package com.ruoyi.voyaai.domain.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public class TripDTO {

    @NotNull(groups = CreateGroup.class, message = "城市ID不能为空")
    private Long cityId;

    @NotBlank(groups = CreateGroup.class, message = "行程标题不能为空")
    @Size(max = 200, message = "行程标题最多200字")
    private String title;

    @Size(max = 500, message = "封面图路径最多500字")
    private String coverImage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Min(value = 1, message = "出行人数至少为1")
    private Integer peopleCount = 1;

    @DecimalMin(value = "0", message = "预算不能小于0")
    private BigDecimal budget = BigDecimal.ZERO;

    @Size(max = 30, message = "出行方式最多30字")
    private String travelType;

    @Size(max = 1000, message = "行程描述最多1000字")
    private String description;

    @Pattern(regexp = "USER|AI|ADMIN", message = "来源只能为USER/AI/ADMIN")
    private String source = "USER";

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public Integer getPeopleCount() { return peopleCount; }
    public void setPeopleCount(Integer peopleCount) { this.peopleCount = peopleCount; }
    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }
    public String getTravelType() { return travelType; }
    public void setTravelType(String travelType) { this.travelType = travelType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
