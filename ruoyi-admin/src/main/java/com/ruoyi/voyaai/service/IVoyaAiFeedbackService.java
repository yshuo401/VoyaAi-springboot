package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.vo.FeedbackVO;

public interface IVoyaAiFeedbackService {

    List<FeedbackVO> list(String status, String keyword, String contact,
            String beginCreateTime, String endCreateTime);

    FeedbackVO detail(Long id);

    int handle(Long id, String handleRemark, String username);
}
