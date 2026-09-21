package com.ruoyi.voyaai.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ruoyi.voyaai.service.IVoyaAiUserAdminService;

@RestController
@RequestMapping("/voyaai/user")
public class UserController extends BaseController {

    private final IVoyaAiUserAdminService service;

    public UserController(IVoyaAiUserAdminService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(String nickname, String status, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(service.list(nickname, status, beginCreateTime, endCreateTime));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:user:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:user:edit')")
    @Log(title = "用户状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated @RequestBody RegionStatusDTO dto) {
        return toAjax(service.changeStatus(dto.getId(), dto.getStatus()));
    }
}
