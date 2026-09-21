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
import com.ruoyi.voyaai.domain.dto.CommentDTO;
import com.ruoyi.voyaai.mapper.VoyaAiCommentMapper;
import com.ruoyi.voyaai.service.app.AppCommentService;

@RestController
@RequestMapping("/voyaai/comment")
public class CommentController extends BaseController {

    private final VoyaAiCommentMapper mapper;
    private final AppCommentService comments;

    public CommentController(VoyaAiCommentMapper mapper, AppCommentService comments) {
        this.mapper = mapper;
        this.comments = comments;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(String targetType, String nickname, String keyword, Long parentId,
            Boolean parentIdNotZero, String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(mapper.selectAdminList(normalizeType(targetType), trimToNull(nickname),
                trimToNull(keyword), parentId, parentIdNotZero,
                trimToNull(beginCreateTime), trimToNull(endCreateTime)));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:comment:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(mapper.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:comment:remove')")
    @Log(title = "评论管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        Arrays.stream(ids).forEach(comments::removeById);
        return success();
    }

    private static String normalizeType(String targetType) {
        String value = trimToNull(targetType);
        return CommentDTO.supports(value) ? value : null;
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
