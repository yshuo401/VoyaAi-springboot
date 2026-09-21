package com.ruoyi.voyaai.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.vo.UserVO;
import com.ruoyi.voyaai.mapper.VoyaAiUserMapper;
import com.ruoyi.voyaai.service.IVoyaAiUserAdminService;

@Service
public class VoyaAiUserAdminServiceImpl implements IVoyaAiUserAdminService {

    private final VoyaAiUserMapper mapper;

    public VoyaAiUserAdminServiceImpl(VoyaAiUserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<UserVO> list(String nickname, String status, String beginCreateTime, String endCreateTime) {
        return mapper.selectAdminList(trimToNull(nickname), trimToNull(status),
                trimToNull(beginCreateTime), trimToNull(endCreateTime));
    }

    @Override
    public UserVO detail(Long id) {
        if (id == null || id <= 0) throw new ServiceException("ID不合法", 400);
        UserVO vo = mapper.selectAdminById(id);
        if (vo == null) throw new ServiceException("用户不存在或已注销", 404);
        return vo;
    }

    @Override
    @Transactional
    public int changeStatus(Long id, String status) {
        UserVO vo = mapper.selectAdminById(id);
        if (vo == null) throw new ServiceException("用户不存在或已注销", 404);
        return mapper.changeStatus(id, status);
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}