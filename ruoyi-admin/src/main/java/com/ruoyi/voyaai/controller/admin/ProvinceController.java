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
import com.ruoyi.voyaai.domain.vo.ProvinceVO;
import com.ruoyi.voyaai.service.IVoyaAiProvinceService;
@RestController
@RequestMapping("/voyaai/province")
public class ProvinceController extends BaseController {
    private final IVoyaAiProvinceService service;

    public ProvinceController(IVoyaAiProvinceService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:province:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Validated ProvinceQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            return getDataTable(service.list(query));
        } finally {
            PageHelper.clearPage();
        }
    }

    @PreAuthorize("@ss.hasPermi('voyaai:province:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:province:add')")
    @Log(title = "省份", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@Validated(CreateGroup.class) @RequestBody ProvinceDTO dto) {
        return toAjax(service.create(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:province:edit')")
    @Log(title = "省份", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated(UpdateGroup.class) @RequestBody ProvinceDTO dto) {
        return toAjax(service.update(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:province:edit')")
    @Log(title = "省份状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated @RequestBody RegionStatusDTO dto) {
        return toAjax(service.changeStatus(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:province:remove')")
    @Log(title = "省份", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) {
        return toAjax(service.delete(ids, getUsername()));
    }

    @PreAuthorize("@ss.hasAnyPermi('voyaai:country:list,voyaai:country:query,voyaai:country:add,voyaai:country:edit,voyaai:province:list,voyaai:province:query,voyaai:province:add,voyaai:province:edit,voyaai:city:list,voyaai:city:query,voyaai:city:add,voyaai:city:edit,voyaai:attraction:list,voyaai:attraction:add,voyaai:attraction:edit')")
    @GetMapping("/options")
    public AjaxResult options(@RequestParam(required = true) Long countryId,
                              @RequestParam(defaultValue = "true") boolean enabledOnly) {
        return success(service.options(countryId, enabledOnly));
    }
}