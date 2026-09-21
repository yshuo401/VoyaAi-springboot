package com.ruoyi.voyaai.controller.admin;

import java.util.Arrays;
import java.util.Set;
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

    private static final Set<String> TARGET_TYPES = Set.of("city", "attraction", "guide", "trip");

    public FavoriteController(VoyaAiFavoriteMapper mapper) {
        this.mapper = mapper;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:favorite:list')")
    @GetMapping("/list")
    public TableDataInfo list(String targetType, String nickname, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(mapper.selectAdminList(normalizeType(targetType), trimToNull(nickname),
                trimToNull(beginCreateTime), trimToNull(endCreateTime)));
    }

    /** 未知类型直接忽略，避免拼出无效的筛选条件。 */
    private static String normalizeType(String targetType) {
        String value = trimToNull(targetType);
        return value != null && TARGET_TYPES.contains(value) ? value : null;
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
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
