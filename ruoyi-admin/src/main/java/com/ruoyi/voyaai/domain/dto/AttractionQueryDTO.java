package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.Positive;

public class AttractionQueryDTO extends RegionQueryDTO {

    @Positive
    private Long countryId;

    @Positive
    private Long provinceId;

    @Positive
    private Long cityId;

    public Long getCountryId() { return countryId; }
    public void setCountryId(Long countryId) { this.countryId = countryId; }
    public Long getProvinceId() { return provinceId; }
    public void setProvinceId(Long provinceId) { this.provinceId = provinceId; }
    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
}