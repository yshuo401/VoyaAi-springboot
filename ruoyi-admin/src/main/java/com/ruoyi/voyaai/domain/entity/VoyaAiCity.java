package com.ruoyi.voyaai.domain.entity;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/** 城市数据库实体。只在 Mapper/Service 内部使用。 */
public class VoyaAiCity extends BaseEntity {
    private Long id; private Long provinceId; private String name; private String coverImage;
    private String description; private BigDecimal latitude; private BigDecimal longitude;
    private Integer sort; private String status; private Long viewCount; private String delFlag;
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Long getProvinceId(){return provinceId;} public void setProvinceId(Long v){provinceId=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getCoverImage(){return coverImage;} public void setCoverImage(String v){coverImage=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public BigDecimal getLatitude(){return latitude;} public void setLatitude(BigDecimal v){latitude=v;}
    public BigDecimal getLongitude(){return longitude;} public void setLongitude(BigDecimal v){longitude=v;}
    public Integer getSort(){return sort;} public void setSort(Integer v){sort=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Long getViewCount(){return viewCount;} public void setViewCount(Long v){viewCount=v;}
    public String getDelFlag(){return delFlag;} public void setDelFlag(String v){delFlag=v;}
}
