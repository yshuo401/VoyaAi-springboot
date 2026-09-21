package com.ruoyi.voyaai.controller.app;

import java.util.List;
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
import com.ruoyi.voyaai.domain.dto.SearchHistoryDTO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppSearchHistoryService;

@RestController
@RequestMapping("/app/voyaai/me")
public class AppSearchHistoryController {

    private final AppSearchHistoryService service;

    public AppSearchHistoryController(AppSearchHistoryService service) {
        this.service = service;
    }

    @GetMapping("/search-history")
    public AjaxResult list(@AuthenticationPrincipal AppPrincipal user) {
        List<?> data = service.getHistory(user.userId());
        return AjaxResult.success(data);
    }

    @PostMapping("/search-history")
    public AjaxResult save(@AuthenticationPrincipal AppPrincipal user, @Valid @RequestBody SearchHistoryDTO dto) {
        if (dto.getKeyword().isEmpty()) {
            throw new ServiceException("关键词不能为空", 400);
        }
        service.save(user.userId(), dto);
        return AjaxResult.success();
    }

    @DeleteMapping("/search-history")
    public AjaxResult clear(@AuthenticationPrincipal AppPrincipal user) {
        service.clearHistory(user.userId());
        return AjaxResult.success();
    }

    @DeleteMapping("/search-history/{id}")
    public AjaxResult remove(@AuthenticationPrincipal AppPrincipal user, @PathVariable Long id) {
        service.remove(user.userId(), id);
        return AjaxResult.success();
    }
}