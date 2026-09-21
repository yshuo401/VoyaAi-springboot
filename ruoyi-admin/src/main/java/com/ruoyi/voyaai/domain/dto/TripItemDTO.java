package com.ruoyi.voyaai.domain.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TripItemDTO {

    private Long attractionId;

    @NotBlank(message = "行程项类型不能为空")
    @Pattern(regexp = "[1-6]", message = "类型只能为1-6")
    private String itemType;

    @NotBlank(message = "行程项标题不能为空")
    @Size(max = 200, message = "行程项标题最多200字")
    private String title;

    @Size(max = 10, message = "开始时间格式HH:mm")
    private String startTime;

    @Size(max = 10, message = "结束时间格式HH:mm")
    private String endTime;

    @Size(max = 500, message = "地址最多500字")
    private String address;

    @Size(max = 1000, message = "描述最多1000字")
    private String description;

    @DecimalMin(value = "0", message = "预估花费不能小于0")
    private BigDecimal estimatedCost = BigDecimal.ZERO;

    @Min(value = 0, message = "排序不能小于0")
    private Integer sort = 0;

    public Long getAttractionId() { return attractionId; }
    public void setAttractionId(Long attractionId) { this.attractionId = attractionId; }
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
}