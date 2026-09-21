package com.ruoyi.voyaai.domain.vo;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentVO {

    private Long id;
    private Long userId;
    private String nickname;
    private String avatarUrl;
    private String targetType;
    private Long targetId;
    private Long parentId;
    private String content;
    private Integer likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String targetTitle;
    private String parentNickname;
    private String parentContent;
    private Boolean liked;
    private List<CommentVO> replies;

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
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getLikeCount() { return likeCount; }
    public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public String getTargetTitle() { return targetTitle; }
    public void setTargetTitle(String targetTitle) { this.targetTitle = targetTitle; }
    public String getParentNickname() { return parentNickname; }
    public void setParentNickname(String parentNickname) { this.parentNickname = parentNickname; }
    public String getParentContent() { return parentContent; }
    public void setParentContent(String parentContent) { this.parentContent = parentContent; }
    public Boolean getLiked() { return liked; }
    public void setLiked(Boolean liked) { this.liked = liked; }
    public List<CommentVO> getReplies() { return replies; }
    public void setReplies(List<CommentVO> replies) { this.replies = replies; }
}
