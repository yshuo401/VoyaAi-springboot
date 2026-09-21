package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** 管理端攻略分页查询。 */
public class GuideQueryDTO extends RegionQueryDTO {
    @Positive(message = "城市ID必须大于0")
    private Long cityId;

    @Size(max = 50, message = "攻略类型最多50字")
    private String guideType;

    @Pattern(regexp = "[012]", message = "发布状态只能为0、1或2")
    private String publishStatus;

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
    public String getGuideType() { return guideType; }
    public void setGuideType(String guideType) { this.guideType = guideType; }
    public String getPublishStatus() { return publishStatus; }
    public void setPublishStatus(String publishStatus) { this.publishStatus = publishStatus; }
}
