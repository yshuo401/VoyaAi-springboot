package com.ruoyi.voyaai.service.app;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.entity.VoyaAiTripShare;
import com.ruoyi.voyaai.domain.vo.TripDayVO;
import com.ruoyi.voyaai.domain.vo.TripDetailVO;
import com.ruoyi.voyaai.domain.vo.TripShareVO;
import com.ruoyi.voyaai.mapper.VoyaAiTripDayMapper;
import com.ruoyi.voyaai.mapper.VoyaAiTripItemMapper;
import com.ruoyi.voyaai.mapper.VoyaAiTripMapper;
import com.ruoyi.voyaai.mapper.VoyaAiTripShareMapper;

@Service
public class AppTripShareService {

    private static final long EXPIRE_DAYS = 7;

    private final VoyaAiTripMapper tripMapper;
    private final VoyaAiTripDayMapper dayMapper;
    private final VoyaAiTripItemMapper itemMapper;
    private final VoyaAiTripShareMapper shareMapper;

    public AppTripShareService(VoyaAiTripMapper tripMapper, VoyaAiTripDayMapper dayMapper,
            VoyaAiTripItemMapper itemMapper, VoyaAiTripShareMapper shareMapper) {
        this.tripMapper = tripMapper;
        this.dayMapper = dayMapper;
        this.itemMapper = itemMapper;
        this.shareMapper = shareMapper;
    }

    @Transactional
    public TripShareVO createOrRefresh(Long userId, Long tripId) {
        if (tripMapper.selectByIdForUpdate(tripId, userId) == null) {
            throw new ServiceException("行程不存在或无权操作", 404);
        }

        VoyaAiTripShare existing = shareMapper.selectByTripId(tripId);
        if (existing != null) {
            return buildVO(existing);
        }

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + EXPIRE_DAYS * 24 * 60 * 60 * 1000L);

        VoyaAiTripShare share = new VoyaAiTripShare();
        share.setTripId(tripId);
        share.setUserId(userId);
        share.setShareCode(UUID.randomUUID().toString().replace("-", ""));
        share.setShareToken(UUID.randomUUID().toString().replace("-", ""));
        share.setExpireTime(expireTime);
        shareMapper.insert(share);

        return buildVO(share);
    }

    @Transactional
    public void invalidate(Long userId, Long tripId) {
        if (tripMapper.selectByIdForUpdate(tripId, userId) == null) {
            throw new ServiceException("行程不存在或无权操作", 404);
        }
        shareMapper.invalidate(tripId);
    }

    public TripDetailVO viewSharedTrip(String shareCode) {
        TripDetailVO vo = shareMapper.selectTripForShare(shareCode);
        if (vo == null) throw new ServiceException("分享不存在或已过期", 404);

        List<TripDayVO> days = dayMapper.selectByTripId(vo.getId());
        if (!days.isEmpty()) {
            days.forEach(day -> day.setItems(itemMapper.selectByDayId(day.getId())));
        }
        vo.setDays(days);
        return vo;
    }

    private TripShareVO buildVO(VoyaAiTripShare share) {
        TripShareVO vo = new TripShareVO();
        vo.setShareCode(share.getShareCode());
        vo.setShareToken(share.getShareToken());
        vo.setExpireTime(share.getExpireTime());
        vo.setUrl("/pages/trip-share?code=" + share.getShareCode());
        return vo;
    }
}
