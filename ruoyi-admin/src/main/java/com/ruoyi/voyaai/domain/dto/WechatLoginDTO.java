package com.ruoyi.voyaai.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.*;

public class WechatLoginDTO {
    @NotBlank(message = "微信登录凭证不能为空") @Size(max = 256)
    private String code;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
