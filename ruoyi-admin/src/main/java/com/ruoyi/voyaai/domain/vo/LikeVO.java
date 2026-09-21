package com.ruoyi.voyaai.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class LikeVO {

    private Long id;
    private Long userId;
    private String nickname;
    private String avatarUrl;
    private String targetType;
    private Long targetId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String targetTitle;
    private String targetCoverImage;
    private String targetSummary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getTargetTitle() { return targetTitle; }
    public void setTargetTitle(String targetTitle) { this.targetTitle = targetTitle; }
    public String getTargetCoverImage() { return targetCoverImage; }
    public void setTargetCoverImage(String targetCoverImage) { this.targetCoverImage = targetCoverImage; }
    public String getTargetSummary() { return targetSummary; }
    public void setTargetSummary(String targetSummary) { this.targetSummary = targetSummary; }
}