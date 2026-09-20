package com.ruoyi.voyaai.domain.dto;

import java.math.BigDecimal;

public class CityCreateDTO {
    private Long provinceId; private String name; private String coverImage; private String description;
    private BigDecimal latitude; private BigDecimal longitude; private Integer sort; private String status; private String remark;
    public Long getProvinceId(){return provinceId;} public void setProvinceId(Long v){provinceId=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getCoverImage(){return coverImage;} public void setCoverImage(String v){coverImage=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public BigDecimal getLatitude(){return latitude;} public void setLatitude(BigDecimal v){latitude=v;}
    public BigDecimal getLongitude(){return longitude;} public void setLongitude(BigDecimal v){longitude=v;}
    public Integer getSort(){return sort;} public void setSort(Integer v){sort=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public String getRemark(){return remark;} public void setRemark(String v){remark=v;}
}
