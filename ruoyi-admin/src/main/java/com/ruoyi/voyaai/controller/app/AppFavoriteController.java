package com.ruoyi.voyaai.controller.app;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.domain.dto.FavoriteDTO;
import com.ruoyi.voyaai.domain.dto.FavoriteQueryDTO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppFavoriteService;

@RestController
@RequestMapping("/app/voyaai/favorites")
public class AppFavoriteController {

    private final AppFavoriteService favorites;

    public AppFavoriteController(AppFavoriteService favorites) {
        this.favorites = favorites;
    }

    @PostMapping
    public AjaxResult add(@AuthenticationPrincipal AppPrincipal user, @Valid @RequestBody FavoriteDTO dto) {
        return AjaxResult.success(favorites.favorite(user.userId(), dto));
    }

    @DeleteMapping("/{targetType}/{targetId}")
    public AjaxResult remove(@AuthenticationPrincipal AppPrincipal user, @PathVariable String targetType, @PathVariable Long targetId) {
        favorites.unfavorite(user.userId(), targetType, targetId);
        return AjaxResult.success();
    }

    @GetMapping
    public AjaxResult list(@AuthenticationPrincipal AppPrincipal user, @Valid FavoriteQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            List<?> rows = favorites.list(user.userId(), query.getTargetType());
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("rows", rows);
            data.put("total", new PageInfo<>(rows).getTotal());
            return AjaxResult.success(data);
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/{targetType}/{targetId}")
    public AjaxResult exists(@AuthenticationPrincipal AppPrincipal user, @PathVariable String targetType, @PathVariable Long targetId) {
        boolean favorited = favorites.exists(user.userId(), targetType, targetId);
        Map<String, Boolean> data = new LinkedHashMap<>();
        data.put("favorited", favorited);
        return AjaxResult.success(data);
    }
}