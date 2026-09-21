package com.ruoyi.voyaai.service.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.voyaai.domain.dto.FeedbackDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiFeedback;
import com.ruoyi.voyaai.mapper.VoyaAiFeedbackMapper;

@Service
public class AppFeedbackService {

    private final VoyaAiFeedbackMapper mapper;

    public AppFeedbackService(VoyaAiFeedbackMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public void submit(Long userId, FeedbackDTO dto) {
        VoyaAiFeedback entity = new VoyaAiFeedback();
        entity.setUserId(userId);
        entity.setContent(dto.getContent().trim());
        entity.setContact(dto.getContact() == null ? null : dto.getContact().trim());
        entity.setImages(dto.getImages() == null ? null : dto.getImages().trim());
        mapper.insert(entity);
    }
}