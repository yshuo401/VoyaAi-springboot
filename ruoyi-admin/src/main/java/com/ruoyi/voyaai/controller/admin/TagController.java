package com.ruoyi.voyaai.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voyaai.domain.dto.RegionStatusDTO;
import com.ruoyi.voyaai.domain.dto.TagDTO;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;
import com.ruoyi.voyaai.service.IVoyaAiTagService;

@RestController
@RequestMapping("/voyaai/tag")
public class TagController extends BaseController {

    private final IVoyaAiTagService service;

    public TagController(IVoyaAiTagService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(String type, String name, String status, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(service.list(type, name, status, beginCreateTime, endCreateTime));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:tag:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:tag:add')")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@Validated(CreateGroup.class) @RequestBody TagDTO dto) {
        return toAjax(service.create(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:tag:edit')")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated(UpdateGroup.class) @RequestBody TagDTO dto) {
        return toAjax(service.update(dto, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:tag:edit')")
    @Log(title = "标签状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated @RequestBody RegionStatusDTO dto) {
        return toAjax(service.changeStatus(dto.getId(), dto.getStatus(), getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:tag:remove')")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) {
        return toAjax(service.delete(ids, getUsername()));
    }
}