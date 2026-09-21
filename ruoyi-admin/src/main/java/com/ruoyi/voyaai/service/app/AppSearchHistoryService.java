package com.ruoyi.voyaai.service.app;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.voyaai.domain.dto.SearchHistoryDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiSearchHistory;
import com.ruoyi.voyaai.domain.vo.SearchHistoryVO;
import com.ruoyi.voyaai.mapper.VoyaAiSearchHistoryMapper;

@Service
public class AppSearchHistoryService {

    private final VoyaAiSearchHistoryMapper mapper;
    private static final int MAX_HISTORY = 20;

    public AppSearchHistoryService(VoyaAiSearchHistoryMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public void save(Long userId, SearchHistoryDTO dto) {
        mapper.deleteByUserAndKeyword(userId, dto.getKeyword());
        VoyaAiSearchHistory entity = new VoyaAiSearchHistory();
        entity.setUserId(userId);
        entity.setKeyword(dto.getKeyword());
        mapper.insert(entity);
        mapper.deleteOldestBeyond(userId, MAX_HISTORY);
    }

    public List<SearchHistoryVO> getHistory(Long userId) {
        return mapper.selectByUser(userId);
    }

    @Transactional
    public void clearHistory(Long userId) {
        mapper.deleteByUser(userId);
    }

    @Transactional
    public void remove(Long userId, Long id) {
        mapper.deleteByIdAndUser(id, userId);
    }
}