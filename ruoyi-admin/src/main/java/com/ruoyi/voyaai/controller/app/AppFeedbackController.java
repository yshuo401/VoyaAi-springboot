package com.ruoyi.voyaai.controller.app;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.domain.dto.FeedbackDTO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppFeedbackService;

@RestController
@RequestMapping("/app/voyaai/feedback")
public class AppFeedbackController {

    private final AppFeedbackService service;

    public AppFeedbackController(AppFeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public AjaxResult submit(@AuthenticationPrincipal AppPrincipal user, @Valid @RequestBody FeedbackDTO dto) {
        service.submit(user != null ? user.userId() : null, dto);
        return AjaxResult.success();
    }
}