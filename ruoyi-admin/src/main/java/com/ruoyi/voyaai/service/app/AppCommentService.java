package com.ruoyi.voyaai.service.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.CommentDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiComment;
import com.ruoyi.voyaai.domain.vo.CommentVO;
import com.ruoyi.voyaai.mapper.VoyaAiCommentMapper;

@Service
public class AppCommentService {

    private final VoyaAiCommentMapper mapper;

    public AppCommentService(VoyaAiCommentMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public CommentVO create(Long userId, CommentDTO dto) {
        if (!CommentDTO.supports(dto.getTargetType())) {
            throw new ServiceException("目标类型只能是attraction或guide", 400);
        }
        if (mapper.countExistingTarget(dto.getTargetType(), dto.getTargetId()) == 0) {
            throw new ServiceException("评论失败，目标可能不存在或已删除", 404);
        }
        if (dto.getParentId() != null && dto.getParentId() > 0) {
            if (mapper.countExistingParent(dto.getParentId(), dto.getTargetType(), dto.getTargetId()) == 0) {
                throw new ServiceException("回复失败，父评论不存在或已删除", 404);
            }
        }
        VoyaAiComment entity = new VoyaAiComment();
        entity.setUserId(userId);
        entity.setTargetType(dto.getTargetType());
        entity.setTargetId(dto.getTargetId());
        entity.setParentId(dto.getParentId() != null ? dto.getParentId() : 0L);
        entity.setContent(dto.getContent().trim());
        mapper.insert(entity);
        adjustCommentCount(dto.getTargetType(), dto.getTargetId(), 1);
        return mapper.selectById(entity.getId());
    }

    @Transactional
    public void delete(Long userId, Long commentId) {
        VoyaAiComment entity = mapper.selectEntityById(commentId);
        if (entity == null || "1".equals(entity.getDelFlag())) {
            throw new ServiceException("评论不存在或已删除", 404);
        }
        if (!entity.getUserId().equals(userId)) {
            throw new ServiceException("只能删除自己的评论", 403);
        }
        int removedReplies = 0;
        if (entity.getParentId() != null && entity.getParentId() == 0) {
            removedReplies = mapper.softDeleteReplies(commentId);
        }
        int removed = mapper.softDelete(commentId);
        adjustCommentCount(entity.getTargetType(), entity.getTargetId(), -(removed + removedReplies));
    }

    @Transactional
    public void removeById(Long id) {
        VoyaAiComment entity = mapper.selectEntityById(id);
        if (entity == null || "1".equals(entity.getDelFlag())) {
            return;
        }
        int removedReplies = 0;
        if (entity.getParentId() != null && entity.getParentId() == 0) {
            removedReplies = mapper.softDeleteReplies(id);
        }
        int removed = mapper.softDelete(id);
        adjustCommentCount(entity.getTargetType(), entity.getTargetId(), -(removed + removedReplies));
    }

    public List<CommentVO> listWithReplies(String targetType, Long targetId, Long currentUserId) {
        List<CommentVO> parents = mapper.selectTopLevel(targetType, targetId);
        if (parents.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> parentIds = parents.stream().map(CommentVO::getId).collect(Collectors.toList());
        List<CommentVO> allReplies = mapper.selectRepliesByParentIds(parentIds);
        Map<Long, List<CommentVO>> replyMap = allReplies.stream()
                .collect(Collectors.groupingBy(CommentVO::getParentId));
        for (CommentVO parent : parents) {
            parent.setReplies(replyMap.getOrDefault(parent.getId(), Collections.emptyList()));
        }
        if (currentUserId != null) {
            List<Long> allIds = new ArrayList<>(parentIds);
            for (CommentVO reply : allReplies) {
                allIds.add(reply.getId());
            }
            if (!allIds.isEmpty()) {
                Set<Long> likedIds = mapper.selectLikedCommentIds(currentUserId, allIds)
                        .stream().collect(Collectors.toSet());
                for (CommentVO parent : parents) {
                    parent.setLiked(likedIds.contains(parent.getId()));
                    if (parent.getReplies() != null) {
                        for (CommentVO reply : parent.getReplies()) {
                            reply.setLiked(likedIds.contains(reply.getId()));
                        }
                    }
                }
            }
        }
        return parents;
    }

    /** 景点和攻略表带 comment_count，评论和回复都计入。 */
    private void adjustCommentCount(String targetType, Long targetId, int delta) {
        if ("attraction".equals(targetType)) {
            mapper.adjustAttractionCommentCount(targetId, delta);
        } else if ("guide".equals(targetType)) {
            mapper.adjustGuideCommentCount(targetId, delta);
        }
    }
}
