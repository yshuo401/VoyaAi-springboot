package com.ruoyi.voyaai.controller.app;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ruoyi.voyaai.domain.entity.VoyaAiComment;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.CommentDTO;
import com.ruoyi.voyaai.domain.dto.LikeDTO;
import com.ruoyi.voyaai.domain.vo.CommentVO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.mapper.VoyaAiCommentMapper;
import com.ruoyi.voyaai.service.app.AppCommentService;
import com.ruoyi.voyaai.service.app.AppLikeService;

@RestController
@RequestMapping("/app/voyaai/comments")
public class AppCommentController {

    private final AppCommentService comments;
    private final AppLikeService likes;
    private final VoyaAiCommentMapper commentMapper;

    public AppCommentController(AppCommentService comments, AppLikeService likes, VoyaAiCommentMapper commentMapper) {
        this.comments = comments;
        this.likes = likes;
        this.commentMapper = commentMapper;
    }

    @Anonymous
    @GetMapping
    public AjaxResult list(@RequestParam String targetType, @RequestParam Long targetId,
            @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        if (!CommentDTO.supports(targetType)) {
            throw new ServiceException("目标类型只能是attraction或guide", 400);
        }
        Long userId = extractUserId().orElse(null);
        try {
            int safePageNum = Math.max(pageNum, 1);
            int safePageSize = Math.max(1, Math.min(pageSize, 50));
            PageHelper.startPage(safePageNum, safePageSize);
            List<CommentVO> rows = comments.listWithReplies(targetType, targetId, userId);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("rows", rows);
            data.put("total", new PageInfo<>(rows).getTotal());
            return AjaxResult.success(data);
        } finally {
            PageHelper.clearPage();
        }
    }

    @PostMapping
    public AjaxResult create(@AuthenticationPrincipal AppPrincipal user, @Valid @RequestBody CommentDTO dto) {
        CommentVO vo = comments.create(user.userId(), dto);
        return AjaxResult.success(vo);
    }

    @DeleteMapping("/{id}")
    public AjaxResult delete(@AuthenticationPrincipal AppPrincipal user, @PathVariable Long id) {
        comments.delete(user.userId(), id);
        return AjaxResult.success();
    }

    @PostMapping("/{id}/like")
    public AjaxResult like(@AuthenticationPrincipal AppPrincipal user, @PathVariable Long id) {
        VoyaAiComment entity = commentMapper.selectEntityById(id);
        if (entity == null || "1".equals(entity.getDelFlag())) {
            throw new ServiceException("评论不存在或已删除", 404);
        }
        LikeDTO dto = new LikeDTO();
        dto.setTargetType("comment");
        dto.setTargetId(id);
        if (likes.exists(user.userId(), "comment", id)) {
            likes.unlike(user.userId(), "comment", id);
        } else {
            likes.like(user.userId(), dto);
        }
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("liked", likes.exists(user.userId(), "comment", id));
        data.put("likeCount", likes.likeCount("comment", id));
        return AjaxResult.success(data);
    }

    private Optional<Long> extractUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(p -> p instanceof AppPrincipal)
                .map(p -> ((AppPrincipal) p).userId());
    }
}
