package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.*;

public class ProvinceCreateDTO {

    @NotNull(message = "请选择上级地区")
    @Positive(message = "地区ID必须大于0")
    private Long countryId;

    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称最多100字")
    private String name;

    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序不能小于0")
    private Integer sort = 0;

    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "[01]", message = "状态只能为0或1")
    private String status = "0";

    @Size(max = 500, message = "备注最多500字")
    private String remark;

    @Size(max = 20, message = "编码最多20字")
    private String code;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
