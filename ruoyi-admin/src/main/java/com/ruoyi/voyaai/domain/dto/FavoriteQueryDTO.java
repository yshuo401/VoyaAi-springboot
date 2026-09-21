package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class FavoriteQueryDTO {

    @Min(1)
    private Integer pageNum = 1;

    @Min(1)
    @Max(50)
    private Integer pageSize = 10;

    @Pattern(regexp = "city|attraction|guide|trip", message = "目标类型只能是city、attraction、guide或trip")
    private String targetType;

    public Integer getPageNum() { return pageNum; }
    public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }
    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
}