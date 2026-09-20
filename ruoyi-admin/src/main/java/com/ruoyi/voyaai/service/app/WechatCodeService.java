package com.ruoyi.voyaai.service.app;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatCodeService {
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    private final String appId;
    private final String secret;
    public WechatCodeService(@Value("${voyaai.wechat.app-id:}") String appId,
                             @Value("${voyaai.wechat.app-secret:}") String secret) {
        this.appId = appId; this.secret = secret;
    }
    public record Identity(String openid, String unionid) { }
    public Identity exchange(String code) {
        if (appId.isBlank() || secret.isBlank()) throw new ServiceException("微信登录尚未配置，请联系管理员", 503);
        // Never log this URI, the code, the upstream body or session_key.
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + encode(appId)
                + "&secret=" + encode(secret) + "&js_code=" + encode(code) + "&grant_type=authorization_code";
        try {
            var response = client.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(10)).GET().build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) throw new ServiceException("微信登录服务暂不可用，请稍后重试", 503);
            var body = JSON.parseObject(response.body());
            if (body == null) throw new ServiceException("微信登录服务响应异常", 503);
            int err = body.getIntValue("errcode");
            if (err == 40029 || err == 40163) throw new ServiceException("微信登录凭证已失效，请重新点击登录", 400);
            if (err != 0) throw new ServiceException("微信登录失败，请稍后重试或检查后端微信配置", 503);
            String openid = body.getString("openid");
            if (openid == null || openid.isBlank() || openid.length() > 100) throw new ServiceException("微信登录服务响应异常", 503);
            return new Identity(openid, body.getString("unionid"));
        } catch (ServiceException e) { throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); throw new ServiceException("微信登录请求中断，请重试", 503);
        } catch (Exception e) { throw new ServiceException("无法连接微信登录服务，请稍后重试", 503); }
    }
    private String encode(String value) { return URLEncoder.encode(value, StandardCharsets.UTF_8); }
}
