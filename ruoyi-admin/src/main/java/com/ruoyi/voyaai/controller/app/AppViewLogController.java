package com.ruoyi.voyaai.controller.app;

import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.ViewLogDTO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppViewLogService;

@RestController
@RequestMapping("/app/voyaai")
public class AppViewLogController {

    private final AppViewLogService viewLogs;

    public AppViewLogController(AppViewLogService viewLogs) {
        this.viewLogs = viewLogs;
    }

    @PostMapping("/view-logs")
    public AjaxResult record(HttpServletRequest req, @Valid @RequestBody ViewLogDTO dto) {
        String type = requireSupportedType(dto.getTargetType());
        dto.setTargetType(type);
        viewLogs.record(extractUserId().orElse(null), dto, req.getRemoteAddr(), req.getHeader("User-Agent"));
        return AjaxResult.success();
    }

    @GetMapping("/me/view-history")
    public AjaxResult history(@AuthenticationPrincipal AppPrincipal user) {
        List<?> data = viewLogs.getHistory(user.userId());
        return AjaxResult.success(data);
    }

    @DeleteMapping("/me/view-history")
    public AjaxResult clearHistory(@AuthenticationPrincipal AppPrincipal user) {
        viewLogs.clearHistory(user.userId());
        return AjaxResult.success();
    }

    private static Optional<Long> extractUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AppPrincipal principal) {
            return Optional.of(principal.userId());
        }
        return Optional.empty();
    }

    private static String requireSupportedType(String targetType) {
        if (!ViewLogDTO.supports(targetType)) {
            throw new ServiceException("目标类型只能是city,attraction或guide", 400);
        }
        return targetType;
    }
}