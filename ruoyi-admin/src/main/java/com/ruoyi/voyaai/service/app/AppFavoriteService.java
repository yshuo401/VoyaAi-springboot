package com.ruoyi.voyaai.service.app;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.FavoriteDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiFavorite;
import com.ruoyi.voyaai.domain.vo.FavoriteVO;
import com.ruoyi.voyaai.mapper.VoyaAiFavoriteMapper;

@Service
public class AppFavoriteService {

    private static final String[] VALID_TYPES = { "city", "attraction", "guide", "trip" };

    private final VoyaAiFavoriteMapper mapper;

    public AppFavoriteService(VoyaAiFavoriteMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public FavoriteVO favorite(Long userId, FavoriteDTO dto) {
        VoyaAiFavorite existing = mapper.lockByUserAndTarget(userId, dto.getTargetType(), dto.getTargetId());
        if (existing != null) {
            FavoriteVO vo = new FavoriteVO();
            vo.setId(existing.getId());
            vo.setTargetType(existing.getTargetType());
            vo.setTargetId(existing.getTargetId());
            vo.setCreateTime(existing.getCreateTime());
            return vo;
        }
        VoyaAiFavorite entity = new VoyaAiFavorite();
        entity.setUserId(userId);
        entity.setTargetType(dto.getTargetType());
        entity.setTargetId(dto.getTargetId());
        mapper.insert(entity);
        FavoriteVO result = mapper.selectByUserAndTarget(userId, dto.getTargetType(), dto.getTargetId());
        if (result == null) {
            throw new ServiceException("收藏失败，目标可能不存在或已删除", 404);
        }
        return result;
    }

    @Transactional
    public void unfavorite(Long userId, String targetType, Long targetId) {
        VoyaAiFavorite existing = mapper.lockByUserAndTarget(userId, targetType, targetId);
        if (existing == null) {
            throw new ServiceException("未收藏该目标", 404);
        }
        mapper.delete(userId, targetType, targetId);
    }

    public List<FavoriteVO> list(Long userId, String targetType) {
        return mapper.selectList(userId, targetType);
    }

    public boolean exists(Long userId, String targetType, Long targetId) {
        return mapper.countByUserAndTarget(userId, targetType, targetId) > 0;
    }
}