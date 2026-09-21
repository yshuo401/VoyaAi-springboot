package com.ruoyi.voyaai.service.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.LikeDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiLike;
import com.ruoyi.voyaai.mapper.VoyaAiLikeMapper;

@Service
public class AppLikeService {

    private final VoyaAiLikeMapper mapper;

    public AppLikeService(VoyaAiLikeMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public void like(Long userId, LikeDTO dto) {
        VoyaAiLike existing = mapper.lockByUserAndTarget(userId, dto.getTargetType(), dto.getTargetId());
        if (existing != null) {
            return;
        }
        if (mapper.countExistingTarget(dto.getTargetType(), dto.getTargetId()) == 0) {
            throw new ServiceException("点赞失败，目标可能不存在或已删除", 404);
        }
        VoyaAiLike entity = new VoyaAiLike();
        entity.setUserId(userId);
        entity.setTargetType(dto.getTargetType());
        entity.setTargetId(dto.getTargetId());
        mapper.insert(entity);
        adjustTargetLikeCount(dto.getTargetType(), dto.getTargetId(), 1);
    }

    @Transactional
    public void unlike(Long userId, String targetType, Long targetId) {
        VoyaAiLike existing = mapper.lockByUserAndTarget(userId, targetType, targetId);
        if (existing == null) {
            throw new ServiceException("未点赞该目标", 404);
        }
        mapper.delete(userId, targetType, targetId);
        adjustTargetLikeCount(targetType, targetId, -1);
    }

    @Transactional
    public void removeById(Long id) {
        VoyaAiLike entity = mapper.selectEntityById(id);
        if (entity == null) {
            return;
        }
        mapper.removeById(id);
        adjustTargetLikeCount(entity.getTargetType(), entity.getTargetId(), -1);
    }

    public boolean exists(Long userId, String targetType, Long targetId) {
        return mapper.countByUserAndTarget(userId, targetType, targetId) > 0;
    }

    public long likeCount(String targetType, Long targetId) {
        return mapper.countByTarget(targetType, targetId);
    }

    private void adjustTargetLikeCount(String targetType, Long targetId, int delta) {
        if ("attraction".equals(targetType)) {
            mapper.adjustAttractionLikeCount(targetId, delta);
        } else if ("guide".equals(targetType)) {
            mapper.adjustGuideLikeCount(targetId, delta);
        }
    }
}
