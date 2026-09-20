package com.ruoyi.voyaai.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.annotation.Excel;

public class CityVO {
    @Excel(name="城市ID") private Long id; private Long provinceId;
    @Excel(name="城市名称") private String name; @Excel(name="封面地址") private String coverImage;
    private String description; private BigDecimal latitude; private BigDecimal longitude;
    @Excel(name="排序") private Integer sort; @Excel(name="状态") private String status;
    @Excel(name="浏览量") private Long viewCount; private String createBy; private Date createTime; private String remark;
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getProvinceId(){return provinceId;} public void setProvinceId(Long v){provinceId=v;}
    public String getName(){return name;} public void setName(String v){name=v;} public String getCoverImage(){return coverImage;} public void setCoverImage(String v){coverImage=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;} public BigDecimal getLatitude(){return latitude;} public void setLatitude(BigDecimal v){latitude=v;}
    public BigDecimal getLongitude(){return longitude;} public void setLongitude(BigDecimal v){longitude=v;} public Integer getSort(){return sort;} public void setSort(Integer v){sort=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;} public Long getViewCount(){return viewCount;} public void setViewCount(Long v){viewCount=v;}
    public String getCreateBy(){return createBy;} public void setCreateBy(String v){createBy=v;} public Date getCreateTime(){return createTime;} public void setCreateTime(Date v){createTime=v;} public String getRemark(){return remark;} public void setRemark(String v){remark=v;}
}
