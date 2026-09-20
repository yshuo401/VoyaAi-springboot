package com.ruoyi.voyaai.domain.vo;

/** The opaque token is returned only at login; no OpenID or session_key is exposed. */
public record AppLoginVO(String token, String tokenType, long expiresIn, AppUserVO user) { }
