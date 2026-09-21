package com.ruoyi.voyaai.domain.dto;

import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CommentDTO {

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

    @NotNull(message = "父评论ID不能为空")
    @PositiveOrZero(message = "父评论ID不能为负数")
    private Long parentId;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 1000, message = "评论内容不能超过1000字")
    private String content;

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}