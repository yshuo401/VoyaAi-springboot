package com.ruoyi.voyaai.service.app;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

/** Independent opaque mini-program sessions. Redis stores a digest, never a bearer token. */
@Service
public class AppSessionService {
    private final StringRedisTemplate redis;
    private final AppUserService users;
    private final SecureRandom random = new SecureRandom();
    private final int ttl;
    public AppSessionService(StringRedisTemplate redis, AppUserService users,
                             @Value("${voyaai.wechat.session-seconds:604800}") int ttl) {
        if (ttl < 60 || ttl > 2592000) throw new IllegalArgumentException("小程序会话有效期必须在60秒至30天之间");
        this.redis = redis; this.users = users; this.ttl = ttl;
    }
    public long expiresIn() { return ttl; }
    public String issue(Long id) {
        byte[] bytes = new byte[32]; random.nextBytes(bytes);
        String token = "va_" + Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        redis.opsForValue().set(key(token), id.toString(), ttl, TimeUnit.SECONDS);
        return token;
    }
    public Long resolve(String token) {
        if (!valid(token)) return null;
        String id = redis.opsForValue().get(key(token));
        if (id == null) return null;
        Long userId = Long.valueOf(id);
        if (!users.isActive(userId)) { revoke(token); return null; }
        return userId;
    }
    public void revoke(String token) { if (valid(token)) redis.delete(key(token)); }
    public static String bearer(String header) {
        return header != null && header.startsWith("Bearer ") ? header.substring(7) : null;
    }
    private boolean valid(String token) { return token != null && token.matches("va_[A-Za-z0-9_-]{43}"); }
    private String key(String token) {
        try { return "voyaai:app:session:" + HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8))); }
        catch (java.security.NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
    }
}
