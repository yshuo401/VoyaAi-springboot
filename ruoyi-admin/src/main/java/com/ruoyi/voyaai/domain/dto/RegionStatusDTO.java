package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.*;

public class RegionStatusDTO {

    @NotNull(message = "ID不能为空")
    @Positive
    private Long id;

    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "[01]", message = "状态只能为0或1")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
