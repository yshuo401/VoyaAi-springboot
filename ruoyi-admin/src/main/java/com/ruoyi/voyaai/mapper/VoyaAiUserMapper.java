package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiUser;
import com.ruoyi.voyaai.domain.vo.AppUserVO;
import com.ruoyi.voyaai.domain.vo.UserVO;

public interface VoyaAiUserMapper {
    VoyaAiUser selectByOpenid(String openid);
    VoyaAiUser selectById(Long id);
    int registerIfAbsent(VoyaAiUser user);
    int recordLogin(VoyaAiUser user);
    AppUserVO selectProfile(Long id);
    int updateProfile(@Param("id") Long id, @Param("nickname") String nickname, @Param("avatarUrl") String avatarUrl);

    List<UserVO> selectAdminList(@Param("nickname") String nickname, @Param("status") String status,
            @Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    UserVO selectAdminById(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") String status);
}