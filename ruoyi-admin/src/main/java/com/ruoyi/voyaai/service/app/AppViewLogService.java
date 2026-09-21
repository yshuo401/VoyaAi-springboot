package com.ruoyi.voyaai.service.app;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.ViewLogDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiViewLog;
import com.ruoyi.voyaai.domain.vo.ViewLogVO;
import com.ruoyi.voyaai.mapper.VoyaAiViewLogMapper;

@Service
public class AppViewLogService {

    private final VoyaAiViewLogMapper mapper;

    public AppViewLogService(VoyaAiViewLogMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public void record(Long userId, ViewLogDTO dto, String ip, String userAgent) {
        if (mapper.countExistingTarget(dto.getTargetType(), dto.getTargetId()) == 0) {
            throw new ServiceException("目标不存在或已删除", 404);
        }
        VoyaAiViewLog log = new VoyaAiViewLog();
        log.setUserId(userId);
        log.setTargetType(dto.getTargetType());
        log.setTargetId(dto.getTargetId());
        log.setIp(ip);
        log.setUserAgent(userAgent);
        mapper.insert(log);
        adjustTargetViewCount(dto.getTargetType(), dto.getTargetId(), 1);
    }

    public List<ViewLogVO> getHistory(Long userId) {
        return mapper.selectByUser(userId);
    }

    @Transactional
    public void clearHistory(Long userId) {
        mapper.removeByUser(userId);
    }

    private void adjustTargetViewCount(String targetType, Long targetId, int delta) {
        switch (targetType) {
            case "city"       -> mapper.adjustCityViewCount(targetId, delta);
            case "attraction" -> mapper.adjustAttractionViewCount(targetId, delta);
            case "guide"      -> mapper.adjustGuideViewCount(targetId, delta);
        }
    }
}
