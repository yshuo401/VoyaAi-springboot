package com.ruoyi.voyaai.domain.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class ViewLogDTO {

    public static final String TARGET_TYPE_PATTERN = "city|attraction|guide";

    private static final Set<String> TARGET_TYPES = Set.of(TARGET_TYPE_PATTERN.split("\\|"));

    public static boolean supports(String targetType) {
        return targetType != null && TARGET_TYPES.contains(targetType);
    }

    @NotBlank(message = "目标类型不能为空")
    @Pattern(regexp = TARGET_TYPE_PATTERN, message = "目标类型只能是city,attraction或guide")
    private String targetType;

    @NotNull(message = "目标ID不能为空")
    @Positive(message = "目标ID必须大于0")
    private Long targetId;

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
}