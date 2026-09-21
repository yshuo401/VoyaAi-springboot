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
import com.ruoyi.voyaai.mapper.VoyaAiFavoriteMapper;

@RestController
@RequestMapping("/voyaai/favorite")
public class FavoriteController extends BaseController {

    private final VoyaAiFavoriteMapper mapper;

    public FavoriteController(VoyaAiFavoriteMapper mapper) {
        this.mapper = mapper;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:favorite:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        return getDataTable(mapper.selectAll());
    }

    @PreAuthorize("@ss.hasPermi('voyaai:favorite:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(mapper.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:favorite:remove')")
    @Log(title = "收藏管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        Arrays.stream(ids).forEach(mapper::removeById);
        return success();
    }
}