package com.ruoyi.voyaai.controller.app;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppTripShareService;

@RestController
@RequestMapping("/app/voyaai")
public class AppTripShareController {

    private final AppTripShareService service;

    public AppTripShareController(AppTripShareService service) {
        this.service = service;
    }

    @PostMapping("/trips/{tripId}/share")
    public AjaxResult createOrRefresh(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId) {
        return AjaxResult.success(service.createOrRefresh(user.userId(), tripId));
    }

    @DeleteMapping("/trips/{tripId}/share")
    public AjaxResult invalidate(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId) {
        service.invalidate(user.userId(), tripId);
        return AjaxResult.success();
    }

    @GetMapping("/trip-shares/{shareCode}")
    public AjaxResult viewShared(@PathVariable String shareCode) {
        return AjaxResult.success(service.viewSharedTrip(shareCode));
    }
}