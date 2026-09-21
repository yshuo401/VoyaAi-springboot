package com.ruoyi.voyaai.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FavoriteVO {

    private Long id;
    private Long userId;
    private String targetType;
    private Long targetId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String targetName;
    private String targetCoverImage;
    private String targetSummary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }
    public String getTargetCoverImage() { return targetCoverImage; }
    public void setTargetCoverImage(String targetCoverImage) { this.targetCoverImage = targetCoverImage; }
    public String getTargetSummary() { return targetSummary; }
    public void setTargetSummary(String targetSummary) { this.targetSummary = targetSummary; }
}