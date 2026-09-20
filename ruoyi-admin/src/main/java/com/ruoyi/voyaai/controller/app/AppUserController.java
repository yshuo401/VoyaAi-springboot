package com.ruoyi.voyaai.controller.app;

import jakarta.validation.Valid;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.voyaai.domain.dto.AppProfileDTO;
import com.ruoyi.voyaai.security.AppPrincipal;
import com.ruoyi.voyaai.service.app.AppUserService;

@RestController
@RequestMapping("/app/voyaai/me")
public class AppUserController {
    private final AppUserService users;
    public AppUserController(AppUserService users) { this.users = users; }
    @GetMapping
    public AjaxResult me(@AuthenticationPrincipal AppPrincipal user) { return AjaxResult.success(users.profile(user.userId())); }
    @PutMapping
    public AjaxResult update(@AuthenticationPrincipal AppPrincipal user, @Valid @RequestBody AppProfileDTO dto) {
        return AjaxResult.success(users.update(user.userId(), dto));
    }
    @PostMapping("/avatar")
    public AjaxResult avatar(@AuthenticationPrincipal AppPrincipal user, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getSize() > 5 * 1024 * 1024) throw new ServiceException("头像不能为空且不能超过5MB", 400);
        try (ImageInputStream input = ImageIO.createImageInputStream(file.getInputStream())) {
            if (input == null) throw new ServiceException("请选择JPG或PNG图片", 400);
            var readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) throw new ServiceException("请选择有效的图片文件", 400);
            var reader = readers.next();
            try {
                reader.setInput(input);
                String type = reader.getFormatName();
                if (!(type.equalsIgnoreCase("JPEG") || type.equalsIgnoreCase("PNG"))
                    || reader.getWidth(0) > 4096 || reader.getHeight(0) > 4096) {
                    throw new ServiceException("头像仅支持JPG或PNG，宽高均不能超过4096像素", 400);
                }
            } finally { reader.dispose(); }
        }
        try {
            String path = FileUploadUtils.upload(RuoYiConfig.getProfile() + "/app-avatar/" + user.userId(), file, new String[]{"jpg", "jpeg", "png"}, true);
            return AjaxResult.success(users.updateAvatar(user.userId(), path));
        } catch (Exception e) { throw new ServiceException("头像保存失败，请检查文件格式或联系管理员", 400); }
    }
}
