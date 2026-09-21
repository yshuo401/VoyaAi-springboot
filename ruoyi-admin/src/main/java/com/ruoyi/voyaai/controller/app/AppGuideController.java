package com.ruoyi.voyaai.controller.app;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.domain.dto.AppGuideQueryDTO;
import com.ruoyi.voyaai.service.IVoyaAiGuideService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 小程序公开攻略接口，只返回已发布且所属地区有效的攻略。 */
@RestController
@RequestMapping("/app/voyaai/guides")
@Anonymous
public class AppGuideController {
    private final IVoyaAiGuideService service;

    public AppGuideController(IVoyaAiGuideService service) { this.service = service; }

    @GetMapping
    public AjaxResult list(@Valid AppGuideQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            List<?> rows = service.publicList(query);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("rows", rows);
            data.put("total", new PageInfo<>(rows).getTotal());
            return AjaxResult.success(data);
        } finally { PageHelper.clearPage(); }
    }

    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return AjaxResult.success(service.publicDetail(id)); }
}
