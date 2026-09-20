package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.Positive;

public class ProvinceQueryDTO extends RegionQueryDTO {

    @Positive
    private Long countryId;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
