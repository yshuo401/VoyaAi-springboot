package com.ruoyi.voyaai.mapper;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiUser;
import com.ruoyi.voyaai.domain.vo.AppUserVO;

public interface VoyaAiUserMapper {
    VoyaAiUser selectByOpenid(String openid);
    VoyaAiUser selectById(Long id);
    int registerIfAbsent(VoyaAiUser user);
    int recordLogin(VoyaAiUser user);
    AppUserVO selectProfile(Long id);
    int updateProfile(@Param("id") Long id, @Param("nickname") String nickname, @Param("avatarUrl") String avatarUrl);
}
