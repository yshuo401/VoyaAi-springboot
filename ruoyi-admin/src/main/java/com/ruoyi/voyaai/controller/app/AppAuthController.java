package com.ruoyi.voyaai.controller.app;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.domain.dto.WechatLoginDTO;
import com.ruoyi.voyaai.domain.vo.AppLoginVO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.*;

@RestController
@RequestMapping("/app/voyaai/auth")
public class AppAuthController {
    private final WechatCodeService wechat;
    private final AppUserService users;
    private final AppSessionService sessions;
    public AppAuthController(WechatCodeService wechat, AppUserService users, AppSessionService sessions) {
        this.wechat = wechat; this.users = users; this.sessions = sessions;
    }
    @PostMapping("/login")
    public AjaxResult login(@Valid @RequestBody WechatLoginDTO dto, HttpServletRequest request) {
        var user = users.login(wechat.exchange(dto.getCode()));
        String token = sessions.issue(user.getId());
        sessions.revoke(AppSessionService.bearer(request.getHeader("Authorization")));
        return AjaxResult.success(new AppLoginVO(token, "Bearer", sessions.expiresIn(), user));
    }
    @PostMapping("/logout")
    public AjaxResult logout(@AuthenticationPrincipal AppPrincipal user, HttpServletRequest request) {
        sessions.revoke(AppSessionService.bearer(request.getHeader("Authorization")));
        return AjaxResult.success();
    }
}
