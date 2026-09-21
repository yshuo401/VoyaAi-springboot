package com.ruoyi.voyaai.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.voyaai.domain.dto.ViewLogDTO;
import com.ruoyi.voyaai.mapper.VoyaAiViewLogMapper;

@RestController
@RequestMapping("/voyaai/viewLog")
public class ViewLogController extends BaseController {

    private final VoyaAiViewLogMapper mapper;

    public ViewLogController(VoyaAiViewLogMapper mapper) {
        this.mapper = mapper;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:viewLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(String targetType, String nickname, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(mapper.selectAdminList(normalizeType(targetType), trimToNull(nickname),
                trimToNull(beginCreateTime), trimToNull(endCreateTime)));
    }

    private static String normalizeType(String targetType) {
        String value = trimToNull(targetType);
        return ViewLogDTO.supports(value) ? value : null;
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:viewLog:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(mapper.selectById(id));
    }
}