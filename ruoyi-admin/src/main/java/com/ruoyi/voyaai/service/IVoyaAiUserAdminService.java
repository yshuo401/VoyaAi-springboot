package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.vo.UserVO;

public interface IVoyaAiUserAdminService {

    List<UserVO> list(String nickname, String status, String beginCreateTime, String endCreateTime);

    UserVO detail(Long id);

    int changeStatus(Long id, String status);
}