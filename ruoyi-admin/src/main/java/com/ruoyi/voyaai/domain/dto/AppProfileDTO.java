package com.ruoyi.voyaai.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.*;

public class AppProfileDTO {
    @NotBlank(message = "请输入昵称") @Size(max = 100, message = "昵称最多100字")
    private String nickname;
    @Size(max = 500)
    private String avatarUrl;
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
