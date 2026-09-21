package com.ruoyi.voyaai.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GuideVO {
    @Excel(name = "ID") private Long id;
    private Long cityId;
    @Excel(name = "城市") private String cityName;
    @Excel(name = "省份") private String provinceName;
    @Excel(name = "国家") private String countryName;
    @Excel(name = "标题") private String title;
    private String coverImage;
    private String summary;
    private String content;
    private String guideType;
    private Integer days;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    @Excel(name = "发布状态") private String publishStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date publishTime;
    @Excel(name = "浏览量") private Long viewCount;
    private Long likeCount;
    private Long favoriteCount;
    private Long commentCount;
    @Excel(name = "排序") private Integer sort;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date createTime;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private Date updateTime;
    private String updateBy;
    private String tagNames;
    private List<Long> tagIds;

    public Long getId() { return id; } public void setId(Long v) { id = v; }
    public Long getCityId() { return cityId; } public void setCityId(Long v) { cityId = v; }
    public String getCityName() { return cityName; } public void setCityName(String v) { cityName = v; }
    public String getProvinceName() { return provinceName; } public void setProvinceName(String v) { provinceName = v; }
    public String getCountryName() { return countryName; } public void setCountryName(String v) { countryName = v; }
    public String getTitle() { return title; } public void setTitle(String v) { title = v; }
    public String getCoverImage() { return coverImage; } public void setCoverImage(String v) { coverImage = v; }
    public String getSummary() { return summary; } public void setSummary(String v) { summary = v; }
    public String getContent() { return content; } public void setContent(String v) { content = v; }
    public String getGuideType() { return guideType; } public void setGuideType(String v) { guideType = v; }
    public Integer getDays() { return days; } public void setDays(Integer v) { days = v; }
    public BigDecimal getBudgetMin() { return budgetMin; } public void setBudgetMin(BigDecimal v) { budgetMin = v; }
    public BigDecimal getBudgetMax() { return budgetMax; } public void setBudgetMax(BigDecimal v) { budgetMax = v; }
    public String getPublishStatus() { return publishStatus; } public void setPublishStatus(String v) { publishStatus = v; }
    public Date getPublishTime() { return publishTime; } public void setPublishTime(Date v) { publishTime = v; }
    public Long getViewCount() { return viewCount; } public void setViewCount(Long v) { viewCount = v; }
    public Long getLikeCount() { return likeCount; } public void setLikeCount(Long v) { likeCount = v; }
    public Long getFavoriteCount() { return favoriteCount; } public void setFavoriteCount(Long v) { favoriteCount = v; }
    public Long getCommentCount() { return commentCount; } public void setCommentCount(Long v) { commentCount = v; }
    public Integer getSort() { return sort; } public void setSort(Integer v) { sort = v; }
    public String getRemark() { return remark; } public void setRemark(String v) { remark = v; }
    public Date getCreateTime() { return createTime; } public void setCreateTime(Date v) { createTime = v; }
    public String getCreateBy() { return createBy; } public void setCreateBy(String v) { createBy = v; }
    public Date getUpdateTime() { return updateTime; } public void setUpdateTime(Date v) { updateTime = v; }
    public String getUpdateBy() { return updateBy; } public void setUpdateBy(String v) { updateBy = v; }
    public String getTagNames() { return tagNames; } public void setTagNames(String v) { tagNames = v; }
    public List<Long> getTagIds() { return tagIds; } public void setTagIds(List<Long> v) { tagIds = v; }
}
