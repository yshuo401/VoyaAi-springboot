package com.ruoyi.voyaai.controller.app;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.LikeDTO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppLikeService;

@RestController
@RequestMapping("/app/voyaai/likes")
public class AppLikeController {

    private final AppLikeService likes;

    public AppLikeController(AppLikeService likes) {
        this.likes = likes;
    }

    @PostMapping
    public AjaxResult add(@AuthenticationPrincipal AppPrincipal user, @Valid @RequestBody LikeDTO dto) {
        likes.like(user.userId(), dto);
        return AjaxResult.success(status(user.userId(), dto.getTargetType(), dto.getTargetId()));
    }

    @DeleteMapping("/{targetType}/{targetId}")
    public AjaxResult remove(@AuthenticationPrincipal AppPrincipal user, @PathVariable String targetType, @PathVariable Long targetId) {
        String type = requireSupportedType(targetType);
        likes.unlike(user.userId(), type, targetId);
        return AjaxResult.success(status(user.userId(), type, targetId));
    }

    @GetMapping("/{targetType}/{targetId}")
    public AjaxResult status(@AuthenticationPrincipal AppPrincipal user, @PathVariable String targetType, @PathVariable Long targetId) {
        String type = requireSupportedType(targetType);
        return AjaxResult.success(status(user.userId(), type, targetId));
    }

    /** 点赞、取消点赞和状态查询共用同一份返回结构，小程序直接拿它刷新按钮。 */
    private Map<String, Object> status(Long userId, String targetType, Long targetId) {
        boolean liked = likes.exists(userId, targetType, targetId);
        long likeCount = likes.likeCount(targetType, targetId);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("targetType", targetType);
        data.put("targetId", targetId);
        data.put("liked", liked);
        data.put("likeCount", likeCount);
        return data;
    }

    private static String requireSupportedType(String targetType) {
        if (!LikeDTO.supports(targetType)) {
            throw new ServiceException("目标类型只能是attraction或guide", 400);
        }
        return targetType;
    }
}
