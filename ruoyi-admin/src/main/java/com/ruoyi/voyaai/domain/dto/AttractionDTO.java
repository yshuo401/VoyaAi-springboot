package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;

public class AttractionDTO {

    @Null(groups = CreateGroup.class, message = "创建时不能指定ID")
    @NotNull(groups = UpdateGroup.class, message = "ID不能为空")
    @Positive
    private Long id;

    @NotNull(message = "请选择上级地区")
    @Positive(message = "地区ID必须大于0")
    private Long cityId;

    @NotBlank(message = "名称不能为空")
    @Size(max = 200, message = "名称最多200字")
    private String name;

    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序不能小于0")
    private Integer sort = 0;

    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "[01]", message = "状态只能为0或1")
    private String status = "0";

    @Size(max = 500, message = "备注最多500字")
    private String remark;

    @Size(max = 500, message = "封面地址最多500字")
    private String coverImage;

    @Size(max = 10000, message = "简介最多10000字")
    private String description;

    @DecimalMin(value = "-90", message = "纬度不能小于-90")
    @DecimalMax(value = "90", message = "纬度不能大于90")
    @Digits(integer = 3, fraction = 7, message = "纬度最多7位小数")
    private BigDecimal latitude;

    @DecimalMin(value = "-180", message = "经度不能小于-180")
    @DecimalMax(value = "180", message = "经度不能大于180")
    @Digits(integer = 3, fraction = 7, message = "经度最多7位小数")
    private BigDecimal longitude;

    @Size(max = 500, message = "地址最多500字")
    private String address;

    @NotNull @DecimalMin("0") @Digits(integer = 8, fraction = 2)
    private BigDecimal ticketPrice = BigDecimal.ZERO;

    @Size(max = 200, message = "开放时间最多200字")
    private String openingHours;

    @NotNull @Min(0)
    private Integer recommendedDuration = 0;

    @NotNull @DecimalMin("0") @DecimalMax("5") @Digits(integer = 1, fraction = 1)
    private BigDecimal rating = BigDecimal.ZERO;

    @NotNull @Size(max = 9, message = "景点图片最多9张")
    private List<@NotBlank @Size(max = 500) String> images = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; }
    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }
    public Integer getRecommendedDuration() { return recommendedDuration; }
    public void setRecommendedDuration(Integer recommendedDuration) { this.recommendedDuration = recommendedDuration; }
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}