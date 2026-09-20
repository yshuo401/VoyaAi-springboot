package com.ruoyi.voyaai.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
import com.ruoyi.voyaai.domain.vo.CityVO;
import com.ruoyi.voyaai.service.IVoyaAiCityService;

@RestController
@RequestMapping("/voyaai/city")
public class CityController extends BaseController {
    private final IVoyaAiCityService service;

    public CityController(IVoyaAiCityService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Validated CityQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            return getDataTable(service.list(query));
        } finally {
            PageHelper.clearPage();
        }
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:add')")
    @Log(title = "城市", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@Validated(CreateGroup.class) @RequestBody CityDTO dto) {
        return toAjax(service.create(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:edit')")
    @Log(title = "城市", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated(UpdateGroup.class) @RequestBody CityDTO dto) {
        return toAjax(service.update(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:edit')")
    @Log(title = "城市状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated @RequestBody RegionStatusDTO dto) {
        return toAjax(service.changeStatus(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:remove')")
    @Log(title = "城市", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) {
        return toAjax(service.delete(ids, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:export')")
    @Log(title = "城市", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @Validated CityQueryDTO query) {
        new ExcelUtil<>(CityVO.class).exportExcel(response, service.list(query), "城市数据");
    }
}