package com.ruoyi.voyaai.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.voyaai.service.app.AppTripService;

@RestController
@RequestMapping("/voyaai/trip")
public class TripController extends BaseController {

    private final AppTripService service;

    public TripController(AppTripService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:trip:list')")
    @GetMapping("/list")
    public TableDataInfo list(Long cityId, String keyword, String source,
            String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(service.listAdmin(cityId, keyword, source, beginCreateTime, endCreateTime));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:trip:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detailAdmin(id));
    }
}
