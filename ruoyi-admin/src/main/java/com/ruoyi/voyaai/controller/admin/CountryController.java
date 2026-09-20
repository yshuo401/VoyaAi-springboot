package com.ruoyi.voyaai.controller.admin;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.vo.CountryVO;
import com.ruoyi.voyaai.service.IVoyaAiCountryService;

@RestController
@RequestMapping("/voyaai/country")
public class CountryController extends BaseController {
    private final IVoyaAiCountryService service;

    public CountryController(IVoyaAiCountryService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:country:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Valid CountryQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            return getDataTable(service.list(query));
        } finally {
            PageHelper.clearPage();
        }
    }

    @PreAuthorize("@ss.hasPermi('voyaai:country:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:country:add')")
    @Log(title = "国家", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@Valid @RequestBody CountryCreateDTO dto) {
        return toAjax(service.create(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:country:edit')")
    @Log(title = "国家", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Valid @RequestBody CountryUpdateDTO dto) {
        return toAjax(service.update(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:country:edit')")
    @Log(title = "国家状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Valid @RequestBody RegionStatusDTO dto) {
        return toAjax(service.changeStatus(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:country:remove')")
    @Log(title = "国家", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) {
        return toAjax(service.delete(ids, getUsername()));
    }

    @PreAuthorize("@ss.hasAnyPermi('voyaai:country:list,voyaai:country:query,voyaai:country:add,voyaai:country:edit,voyaai:province:list,voyaai:province:query,voyaai:province:add,voyaai:province:edit,voyaai:city:list,voyaai:city:query,voyaai:city:add,voyaai:city:edit')")
    @GetMapping("/options")
    public AjaxResult options(@RequestParam(required = false) Long countryId,
                              @RequestParam(defaultValue = "true") boolean enabledOnly) {
        return success(service.options(countryId, enabledOnly));
    }
}
