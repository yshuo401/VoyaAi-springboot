package com.ruoyi.voyaai.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;

/** 城市列表查询条件。 */
public class CityQueryDTO extends BaseEntity {
    private Long provinceId; private String name; private String status;
    public Long getProvinceId(){return provinceId;} public void setProvinceId(Long v){provinceId=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
