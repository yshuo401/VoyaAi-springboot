package com.ruoyi.voyaai.domain.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public class AttractionQueryDTO {

    @NotNull
    @Min(1)
    private Integer pageNum = 1;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer pageSize = 10;

    @Size(max = 200)
    private String name;

    @Pattern(regexp = "[01]", message = "状态只能为0或1")
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate beginCreateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endCreateTime;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(LocalDate beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public LocalDate getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(LocalDate endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
    @AssertTrue(message = "开始日期不能晚于结束日期")
    public boolean isDateRangeValid() {
        return beginCreateTime == null || endCreateTime == null || !beginCreateTime.isAfter(endCreateTime);
    }

    public LocalDate getEndExclusive() {
        return endCreateTime == null ? null : endCreateTime.plusDays(1);
    }

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
