package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** 小程序公开攻略查询。 */
public class AppGuideQueryDTO {
    @Min(1) private Integer pageNum = 1;
    @Min(1) @Max(50) private Integer pageSize = 10;
    @Positive(message = "城市ID必须大于0") private Long cityId;
    @Size(max = 100, message = "搜索关键词最多100字") private String keyword;
    @Size(max = 50, message = "攻略类型最多50字") private String guideType;
    /** recommend、latest、hot */
    @Pattern(regexp = "recommend|latest|hot", message = "排序方式只能是recommend、latest或hot")
    private String sort = "recommend";

    public Integer getPageNum() { return pageNum; }
    public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }
    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getGuideType() { return guideType; }
    public void setGuideType(String guideType) { this.guideType = guideType; }
    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
}
