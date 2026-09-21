package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

/** 攻略发布状态：0草稿、1已发布、2已下架。 */
public class GuideStatusDTO {
    @NotNull(message = "ID不能为空")
    @Positive(message = "ID必须大于0")
    private Long id;

    @NotNull(message = "发布状态不能为空")
    @Pattern(regexp = "[012]", message = "发布状态只能为0、1或2")
    private String publishStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPublishStatus() { return publishStatus; }
    public void setPublishStatus(String publishStatus) { this.publishStatus = publishStatus; }
}
