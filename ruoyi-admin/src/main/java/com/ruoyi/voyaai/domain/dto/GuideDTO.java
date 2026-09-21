package com.ruoyi.voyaai.domain.dto;

import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 攻略新增和修改共用请求对象。 */
public class GuideDTO {
    @Null(groups = CreateGroup.class, message = "创建时不能指定ID")
    @NotNull(groups = UpdateGroup.class, message = "ID不能为空")
    @Positive(message = "ID必须大于0")
    private Long id;

    @NotNull(message = "请选择城市")
    @Positive(message = "城市ID必须大于0")
    private Long cityId;

    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题最多200字")
    private String title;

    @Size(max = 500, message = "封面地址最多500字")
    private String coverImage;

    @Size(max = 500, message = "摘要最多500字")
    private String summary;

    @NotBlank(message = "正文不能为空")
    @Size(max = 1000000, message = "正文最多1000000字")
    private String content;

    @Size(max = 50, message = "攻略类型最多50字")
    private String guideType;

    @NotNull(message = "建议天数不能为空")
    @Min(value = 1, message = "建议天数至少为1天")
    @Max(value = 365, message = "建议天数不能超过365天")
    private Integer days = 1;

    @NotNull(message = "最低预算不能为空")
    @DecimalMin(value = "0", message = "最低预算不能小于0")
    @Digits(integer = 8, fraction = 2, message = "最低预算最多8位整数和2位小数")
    private BigDecimal budgetMin = BigDecimal.ZERO;

    @NotNull(message = "最高预算不能为空")
    @DecimalMin(value = "0", message = "最高预算不能小于0")
    @Digits(integer = 8, fraction = 2, message = "最高预算最多8位整数和2位小数")
    private BigDecimal budgetMax = BigDecimal.ZERO;

    @NotNull(message = "发布状态不能为空")
    @Pattern(regexp = "[012]", message = "发布状态只能为0草稿、1已发布或2已下架")
    private String publishStatus = "0";

    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序不能小于0")
    private Integer sort = 0;

    @Size(max = 500, message = "备注最多500字")
    private String remark;

    @Size(max = 20, message = "标签最多20个")
    private List<@NotNull @Positive(message = "标签ID必须大于0") Long> tagIds = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getGuideType() { return guideType; }
    public void setGuideType(String guideType) { this.guideType = guideType; }
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    public BigDecimal getBudgetMin() { return budgetMin; }
    public void setBudgetMin(BigDecimal budgetMin) { this.budgetMin = budgetMin; }
    public BigDecimal getBudgetMax() { return budgetMax; }
    public void setBudgetMax(BigDecimal budgetMax) { this.budgetMax = budgetMax; }
    public String getPublishStatus() { return publishStatus; }
    public void setPublishStatus(String publishStatus) { this.publishStatus = publishStatus; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public List<Long> getTagIds() { return tagIds; }
    public void setTagIds(List<Long> tagIds) { this.tagIds = tagIds == null ? new ArrayList<>() : tagIds; }

    @AssertTrue(message = "最低预算不能高于最高预算")
    public boolean isBudgetRangeValid() {
        return budgetMin == null || budgetMax == null || budgetMin.compareTo(budgetMax) <= 0;
    }
}
