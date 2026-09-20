package com.ruoyi.voyaai.service.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.mapper.VoyaAiUserMapper;
import com.ruoyi.voyaai.domain.entity.VoyaAiUser;
import com.ruoyi.voyaai.domain.vo.AppUserVO;
import com.ruoyi.voyaai.domain.dto.AppProfileDTO;

@Service
public class AppUserService {
    private final VoyaAiUserMapper mapper;
    public AppUserService(VoyaAiUserMapper mapper) { this.mapper = mapper; }

    @Transactional
    public AppUserVO login(WechatCodeService.Identity identity) {
        VoyaAiUser newUser = new VoyaAiUser();
        newUser.setOpenid(identity.openid()); newUser.setUnionid(identity.unionid());
        mapper.registerIfAbsent(newUser);
        VoyaAiUser user = mapper.selectByOpenid(identity.openid());
        if (!active(user)) throw new ServiceException("账号已停用或注销", 403);
        user.setUnionid(identity.unionid());
        mapper.recordLogin(user);
        return profile(user.getId());
    }
    public boolean isActive(Long id) { return active(mapper.selectById(id)); }
    private boolean active(VoyaAiUser user) { return user != null && "0".equals(user.getStatus()) && "0".equals(user.getDelFlag()); }
    public AppUserVO profile(Long id) {
        AppUserVO user = mapper.selectProfile(id);
        if (user == null) throw new ServiceException("登录已失效，请重新登录", 401);
        return user;
    }
    @Transactional
    public AppUserVO update(Long id, AppProfileDTO dto) {
        String avatar = dto.getAvatarUrl();
        if (avatar != null && !avatar.isBlank()
            && !avatar.matches("/profile/app-avatar/" + id + "/[0-9]{4}/[0-9]{2}/[0-9]{2}/[a-f0-9]{32}\\.(jpg|jpeg|png)")) {
            throw new ServiceException("请通过头像选择按钮上传本人头像", 400);
        }
        mapper.updateProfile(id, dto.getNickname().trim(), avatar == null || avatar.isBlank() ? null : avatar);
        return profile(id);
    }
    @Transactional
    public AppUserVO updateAvatar(Long id, String avatarUrl) {
        AppUserVO current = profile(id);
        mapper.updateProfile(id, current.getNickname(), avatarUrl);
        return profile(id);
    }
}
