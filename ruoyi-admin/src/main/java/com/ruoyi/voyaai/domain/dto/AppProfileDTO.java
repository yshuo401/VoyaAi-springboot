package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AppProfileDTO {
    @NotBlank(message = "请输入昵称") @Size(max = 100, message = "昵称最多100字")
    private String nickname;
    @Size(max = 500)
    private String avatarUrl;
}