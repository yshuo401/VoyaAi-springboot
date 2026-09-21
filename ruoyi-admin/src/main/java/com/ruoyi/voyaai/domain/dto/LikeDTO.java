package com.ruoyi.voyaai.domain.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class LikeDTO {

    /** 小程序端只给景点和攻略提供点赞入口，也只有这两张表带 like_count 字段。 */
    public static final String TARGET_TYPE_PATTERN = "attraction|guide";

    private static final Set<String> TARGET_TYPES = Set.of(TARGET_TYPE_PATTERN.split("\\|"));

    public static boolean supports(String targetType) {
        return targetType != null && TARGET_TYPES.contains(targetType);
    }

    @NotBlank(message = "目标类型不能为空")
    @Pattern(regexp = TARGET_TYPE_PATTERN, message = "目标类型只能是attraction或guide")
    private String targetType;

    @NotNull(message = "目标ID不能为空")
    @Positive(message = "目标ID必须大于0")
    private Long targetId;

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
}
