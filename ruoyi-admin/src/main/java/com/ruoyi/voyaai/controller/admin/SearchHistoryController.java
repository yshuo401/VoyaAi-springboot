package com.ruoyi.voyaai.controller.admin;

import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.voyaai.mapper.VoyaAiSearchHistoryMapper;

@RestController
@RequestMapping("/voyaai/searchHistory")
public class SearchHistoryController extends BaseController {

    private final VoyaAiSearchHistoryMapper mapper;

    public SearchHistoryController(VoyaAiSearchHistoryMapper mapper) {
        this.mapper = mapper;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:searchHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(String keyword, String nickname, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(mapper.selectAdminList(trimToNull(keyword), trimToNull(nickname),
                trimToNull(beginCreateTime), trimToNull(endCreateTime)));
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:searchHistory:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(mapper.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:searchHistory:remove')")
    @Log(title = "搜索历史管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        Arrays.stream(ids).forEach(mapper::removeById);
        return success();
    }
}
