package com.ruoyi.voyaai.controller.app;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.domain.dto.TripDTO;
import com.ruoyi.voyaai.domain.dto.TripDayDTO;
import com.ruoyi.voyaai.domain.dto.TripItemDTO;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.vo.TripListVO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppTripService;

@RestController
@RequestMapping("/app/voyaai/trips")
public class AppTripController extends BaseController {

    private final AppTripService service;

    public AppTripController(AppTripService service) {
        this.service = service;
    }

    @GetMapping
    public AjaxResult list(@AuthenticationPrincipal AppPrincipal user,
            String keyword, String status,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            PageHelper.startPage(Math.max(pageNum, 1), Math.max(1, Math.min(pageSize, 50)));
            List<TripListVO> rows = service.list(user.userId(), keyword, status);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("rows", rows);
            data.put("total", new PageInfo<>(rows).getTotal());
            return AjaxResult.success(data);
        } finally {
            PageHelper.clearPage();
        }
    }

    @PostMapping
    public AjaxResult create(@AuthenticationPrincipal AppPrincipal user,
            @Validated(CreateGroup.class) @RequestBody TripDTO dto) {
        return success(service.create(user.userId(), dto));
    }

    @GetMapping("/{id}")
    public AjaxResult detail(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long id) {
        return success(service.detail(user.userId(), id));
    }

    @PutMapping("/{id}")
    public AjaxResult update(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long id, @Validated @RequestBody TripDTO dto) {
        return success(service.update(user.userId(), id, dto));
    }

    @DeleteMapping("/{id}")
    public AjaxResult delete(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long id) {
        service.delete(user.userId(), id);
        return success();
    }

    @PostMapping("/{tripId}/days")
    public AjaxResult addDay(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId, @Valid @RequestBody TripDayDTO dto) {
        return success(service.addDay(user.userId(), tripId, dto));
    }

    @PutMapping("/{tripId}/days/{dayId}")
    public AjaxResult updateDay(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId, @PathVariable Long dayId,
            @Valid @RequestBody TripDayDTO dto) {
        return success(service.updateDay(user.userId(), tripId, dayId, dto));
    }

    @DeleteMapping("/{tripId}/days/{dayId}")
    public AjaxResult deleteDay(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId, @PathVariable Long dayId) {
        service.deleteDay(user.userId(), tripId, dayId);
        return success();
    }

    @PostMapping("/{tripId}/days/{dayId}/items")
    public AjaxResult addItem(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId, @PathVariable Long dayId,
            @Valid @RequestBody TripItemDTO dto) {
        return success(service.addItem(user.userId(), tripId, dayId, dto));
    }

    @PutMapping("/{tripId}/days/{dayId}/items/{itemId}")
    public AjaxResult updateItem(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId, @PathVariable Long dayId, @PathVariable Long itemId,
            @Valid @RequestBody TripItemDTO dto) {
        return success(service.updateItem(user.userId(), tripId, dayId, itemId, dto));
    }

    @DeleteMapping("/{tripId}/days/{dayId}/items/{itemId}")
    public AjaxResult deleteItem(@AuthenticationPrincipal AppPrincipal user,
            @PathVariable Long tripId, @PathVariable Long dayId, @PathVariable Long itemId) {
        service.deleteItem(user.userId(), tripId, dayId, itemId);
        return success();
    }
}
