package com.ruoyi.voyaai.controller.admin;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;
import com.ruoyi.voyaai.domain.vo.GuideVO;
import com.ruoyi.voyaai.service.IVoyaAiGuideService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voyaai/guide")
public class GuideController extends BaseController {
    private final IVoyaAiGuideService service;

    public GuideController(IVoyaAiGuideService service) { this.service = service; }

    @PreAuthorize("@ss.hasPermi('voyaai:guide:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Validated GuideQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            return getDataTable(service.list(query));
        } finally { PageHelper.clearPage(); }
    }

    @PreAuthorize("@ss.hasAnyPermi('voyaai:guide:query,voyaai:guide:edit')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) { return success(service.detail(id)); }

    @PreAuthorize("@ss.hasPermi('voyaai:guide:add')")
    @Log(title = "旅游攻略", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@Validated(CreateGroup.class) @RequestBody GuideDTO dto) {
        return toAjax(service.create(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:guide:edit')")
    @Log(title = "旅游攻略", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated(UpdateGroup.class) @RequestBody GuideDTO dto) {
        return toAjax(service.update(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:guide:edit')")
    @Log(title = "攻略发布状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated @RequestBody GuideStatusDTO dto) {
        return toAjax(service.changeStatus(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:guide:remove')")
    @Log(title = "旅游攻略", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) { return toAjax(service.delete(ids, getUsername())); }

    @PreAuthorize("@ss.hasPermi('voyaai:guide:export')")
    @Log(title = "旅游攻略", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @Validated GuideQueryDTO query) {
        new ExcelUtil<>(GuideVO.class).exportExcel(response, service.list(query), "旅游攻略数据");
    }
}
