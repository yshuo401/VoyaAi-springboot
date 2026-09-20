# 微信小程序登录

## 后端配置

微信 `AppID` 和 `AppSecret` 不写入 Java 代码、数据库或小程序仓库。启动后端前设置环境变量：

```powershell
$env:VOYAAI_WECHAT_APP_ID = "你的小程序AppID"
$env:VOYAAI_WECHAT_APP_SECRET = "微信公众平台的AppSecret"
$env:VOYAAI_APP_SESSION_SECONDS = "604800"
```

然后重启 `RuoYiApplication`。`VOYAAI_APP_SESSION_SECONDS` 可选，范围为60秒到30天，默认7天。

## 接口

- `POST /app/voyaai/auth/login`：提交微信 `wx.login()` 返回的临时 `code`，返回小程序 Token 和用户资料。
- `POST /app/voyaai/auth/logout`：注销当前 Token。
- `GET /app/voyaai/me`：读取当前用户资料，需要 `Authorization: Bearer va_...`。
- `PUT /app/voyaai/me`：修改昵称，需要登录。
- `POST /app/voyaai/me/avatar`：使用 `multipart/form-data` 的 `file` 上传头像，需要登录。

Token 只保存 Redis 摘要，数据库只保存用户 OpenID、昵称和头像路径。微信 `session_key`、AppSecret 不会返回前端或写入日志。

## 小程序开发工具

开发环境暂时使用 `http://localhost:8080`，`project.private.config.json` 已关闭 URL 校验。真机和发布版本必须改成备案的 HTTPS 域名，并在微信公众平台配置 request 合法域名。

首次点击“我的 → 微信登录”前，必须配置后端环境变量；没有配置时接口会明确返回“微信登录尚未配置”。
