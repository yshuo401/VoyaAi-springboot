package com.ruoyi.voyaai.controller.admin;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.voyaai.domain.dto.FeedbackHandleDTO;
import com.ruoyi.voyaai.domain.vo.FeedbackVO;
import com.ruoyi.voyaai.service.IVoyaAiFeedbackService;

@RestController
@RequestMapping("/voyaai/feedback")
public class FeedbackController extends BaseController {

    private final IVoyaAiFeedbackService service;

    public FeedbackController(IVoyaAiFeedbackService service) {
        this.service = service;
    }

    @PreAuthorize("@ss.hasPermi('voyaai:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(String status, String keyword, String contact,
            String beginCreateTime, String endCreateTime) {
        startPage();
        return getDataTable(service.list(status, keyword, contact, beginCreateTime, endCreateTime));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:feedback:query')")
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(service.detail(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:feedback:handle')")
    @Log(title = "意见反馈", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/handle")
    public AjaxResult handle(@PathVariable Long id, @Valid @RequestBody FeedbackHandleDTO dto) {
        return toAjax(service.handle(id, dto.getHandleRemark(), getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:feedback:export')")
    @Log(title = "意见反馈", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, String status, String keyword, String contact,
            String beginCreateTime, String endCreateTime) {
        List<FeedbackVO> list = service.list(status, keyword, contact, beginCreateTime, endCreateTime);
        new ExcelUtil<>(FeedbackVO.class).exportExcel(response, list, "意见反馈数据");
    }
}
