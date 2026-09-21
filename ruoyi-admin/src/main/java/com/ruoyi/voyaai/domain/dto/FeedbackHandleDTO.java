package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FeedbackHandleDTO {

    @NotBlank(message = "处理备注不能为空")
    @Size(max = 500, message = "处理备注最多500字")
    private String handleRemark;

    public String getHandleRemark() { return handleRemark; }
    public void setHandleRemark(String handleRemark) { this.handleRemark = handleRemark; }
}
