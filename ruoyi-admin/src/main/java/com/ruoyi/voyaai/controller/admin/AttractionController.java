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
import com.ruoyi.voyaai.domain.vo.AttractionVO;
import com.ruoyi.voyaai.service.IVoyaAiAttractionService;

@RestController
@RequestMapping("/voyaai/attraction")
public class AttractionController extends BaseController {
    private final IVoyaAiAttractionService service;

    public AttractionController(IVoyaAiAttractionService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:attraction:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Valid AttractionQueryDTO query) {
        try {
            PageHelper.startPage(query.getPageNum(), query.getPageSize());
            return getDataTable(service.list(query));
        } finally {
            PageHelper.clearPage();
        }
    }

    @PreAuthorize("@ss.hasAnyPermi('voyaai:attraction:query,voyaai:attraction:edit')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:attraction:add')")
    @Log(title = "景点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@Valid @RequestBody AttractionCreateDTO dto) {
        return toAjax(service.create(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:attraction:edit')")
    @Log(title = "景点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Valid @RequestBody AttractionUpdateDTO dto) {
        return toAjax(service.update(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:attraction:edit')")
    @Log(title = "景点状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Valid @RequestBody RegionStatusDTO dto) {
        return toAjax(service.changeStatus(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:attraction:remove')")
    @Log(title = "景点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) {
        return toAjax(service.delete(ids, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:attraction:export')")
    @Log(title = "景点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @Valid AttractionQueryDTO query) {
        new ExcelUtil<>(AttractionVO.class).exportExcel(response, service.list(query), "景点数据");
    }

    @PreAuthorize("@ss.hasAnyPermi('voyaai:attraction:list,voyaai:attraction:add,voyaai:attraction:edit')")
    @GetMapping("/cityOptions")
    public AjaxResult cityOptions(@RequestParam Long provinceId) {
        return success(service.cityOptions(provinceId));
    }
}
