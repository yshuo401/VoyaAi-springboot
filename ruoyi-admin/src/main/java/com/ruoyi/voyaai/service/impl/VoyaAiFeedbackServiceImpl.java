package com.ruoyi.voyaai.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.vo.FeedbackVO;
import com.ruoyi.voyaai.mapper.VoyaAiFeedbackMapper;
import com.ruoyi.voyaai.service.IVoyaAiFeedbackService;

@Service
public class VoyaAiFeedbackServiceImpl implements IVoyaAiFeedbackService {

    private final VoyaAiFeedbackMapper mapper;

    public VoyaAiFeedbackServiceImpl(VoyaAiFeedbackMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<FeedbackVO> list(String status, String keyword, String contact,
            String beginCreateTime, String endCreateTime) {
        return mapper.selectAdminList(trimToNull(status), trimToNull(keyword), trimToNull(contact),
                trimToNull(beginCreateTime), trimToNull(endCreateTime));
    }

    @Override
    public FeedbackVO detail(Long id) {
        if (id == null || id <= 0) throw new ServiceException("ID不合法", 400);
        FeedbackVO vo = mapper.selectById(id);
        if (vo == null) throw new ServiceException("反馈不存在", 404);
        return vo;
    }

    @Override
    @Transactional
    public int handle(Long id, String handleRemark, String username) {
        FeedbackVO vo = mapper.selectById(id);
        if (vo == null) throw new ServiceException("反馈不存在", 404);
        if ("1".equals(vo.getStatus())) throw new ServiceException("该反馈已处理，不能重复处理", 409);
        return mapper.handle(id, handleRemark, username);
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
