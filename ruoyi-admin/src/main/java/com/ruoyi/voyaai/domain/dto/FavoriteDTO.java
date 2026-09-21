package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class FavoriteDTO {

    @NotBlank(message = "目标类型不能为空")
    @Pattern(regexp = "city|attraction|guide|trip", message = "目标类型只能是city、attraction、guide或trip")
    private String targetType;

    @NotNull(message = "目标ID不能为空")
    @Positive(message = "目标ID必须大于0")
    private Long targetId;

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
}