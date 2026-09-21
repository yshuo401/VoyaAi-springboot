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
import com.ruoyi.voyaai.domain.dto.LikeDTO;
import com.ruoyi.voyaai.mapper.VoyaAiLikeMapper;
import com.ruoyi.voyaai.service.app.AppLikeService;

@RestController
@RequestMapping("/voyaai/like")
public class LikeController extends BaseController {

    private final VoyaAiLikeMapper mapper;
    private final AppLikeService likes;

    public LikeController(VoyaAiLikeMapper mapper, AppLikeService likes) {
        this.mapper = mapper;
        this.likes = likes;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:like:list')")
    @GetMapping("/list")
    public TableDataInfo list(String targetType, String nickname, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(mapper.selectAdminList(normalizeType(targetType), trimToNull(nickname),
                trimToNull(beginCreateTime), trimToNull(endCreateTime)));
    }

    private static String normalizeType(String targetType) {
        String value = trimToNull(targetType);
        return LikeDTO.supports(value) ? value : null;
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:like:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(mapper.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:like:remove')")
    @Log(title = "点赞管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        Arrays.stream(ids).forEach(likes::removeById);
        return success();
    }
}
