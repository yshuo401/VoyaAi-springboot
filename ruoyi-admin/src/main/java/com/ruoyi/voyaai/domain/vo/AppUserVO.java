package com.ruoyi.voyaai.domain.vo;

import java.util.Date;
import jakarta.validation.constraints.*;

public class AppUserVO {
    
    private Long id;
    
    private String nickname;
    
    private String avatarUrl;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
