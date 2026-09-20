package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CityCreateDTO {

    @NotNull(message = "请选择上级地区")
    @Positive(message = "地区ID必须大于0")
    private Long provinceId;

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

    @Size(max = 500, message = "封面地址最多500字")
    private String coverImage;

    @Size(max = 10000, message = "简介最多10000字")
    private String description;

    @DecimalMin(value = "-90", message = "纬度不能小于-90")
    @DecimalMax(value = "90", message = "纬度不能大于90")
    @Digits(integer = 3, fraction = 7, message = "纬度最多7位小数")
    private BigDecimal latitude;

    @DecimalMin(value = "-180", message = "经度不能小于-180")
    @DecimalMax(value = "180", message = "经度不能大于180")
    @Digits(integer = 3, fraction = 7, message = "经度最多7位小数")
    private BigDecimal longitude;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
