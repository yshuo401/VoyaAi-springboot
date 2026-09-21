package com.ruoyi.voyaai.service.app;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.TripDTO;
import com.ruoyi.voyaai.domain.dto.TripDayDTO;
import com.ruoyi.voyaai.domain.dto.TripItemDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiTrip;
import com.ruoyi.voyaai.domain.entity.VoyaAiTripDay;
import com.ruoyi.voyaai.domain.entity.VoyaAiTripItem;
import com.ruoyi.voyaai.domain.vo.TripDayVO;
import com.ruoyi.voyaai.domain.vo.TripDetailVO;
import com.ruoyi.voyaai.domain.vo.TripItemVO;
import com.ruoyi.voyaai.domain.vo.TripListVO;
import com.ruoyi.voyaai.mapper.VoyaAiTripDayMapper;
import com.ruoyi.voyaai.mapper.VoyaAiTripItemMapper;
import com.ruoyi.voyaai.mapper.VoyaAiTripMapper;

@Service
public class AppTripService {

    private final VoyaAiTripMapper tripMapper;
    private final VoyaAiTripDayMapper dayMapper;
    private final VoyaAiTripItemMapper itemMapper;

    public AppTripService(VoyaAiTripMapper tripMapper, VoyaAiTripDayMapper dayMapper,
            VoyaAiTripItemMapper itemMapper) {
        this.tripMapper = tripMapper;
        this.dayMapper = dayMapper;
        this.itemMapper = itemMapper;
    }

    public List<TripListVO> list(Long userId, String keyword, String status) {
        return tripMapper.selectList(userId, trimToNull(keyword), trimToNull(status));
    }

    public List<TripListVO> listAdmin(Long cityId, String keyword, String source,
            String beginCreateTime, String endCreateTime) {
        return tripMapper.selectAdminList(cityId, trimToNull(keyword), trimToNull(source),
                trimToNull(beginCreateTime), trimToNull(endCreateTime));
    }

    public TripDetailVO detailAdmin(Long tripId) {
        TripDetailVO vo = tripMapper.selectAdminById(tripId);
        if (vo == null) throw new ServiceException("行程不存在", 404);

        List<TripDayVO> days = dayMapper.selectByTripId(tripId);
        if (!days.isEmpty()) {
            days.forEach(day -> day.setItems(itemMapper.selectByDayId(day.getId())));
        }
        vo.setDays(days);
        return vo;
    }

    @Transactional
    public TripDetailVO create(Long userId, TripDTO dto) {
        validateCreate(dto);

        VoyaAiTrip trip = new VoyaAiTrip();
        trip.setUserId(userId);
        trip.setCityId(dto.getCityId());
        trip.setTitle(dto.getTitle().trim());
        trip.setCoverImage(dto.getCoverImage() != null ? dto.getCoverImage().trim() : null);
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        trip.setPeopleCount(dto.getPeopleCount());
        trip.setBudget(dto.getBudget());
        trip.setTravelType(dto.getTravelType() != null ? dto.getTravelType().trim() : null);
        trip.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : "");
        trip.setSource(dto.getSource());
        tripMapper.insert(trip);

        TripDetailVO vo = tripMapper.selectById(trip.getId(), userId);
        if (vo == null) throw new ServiceException("创建行程失败", 500);
        return vo;
    }

    @Transactional
    public TripDetailVO update(Long userId, Long tripId, TripDTO dto) {
        VoyaAiTrip existing = tripMapper.selectByIdForUpdate(tripId, userId);
        if (existing == null) throw new ServiceException("行程不存在或无权操作", 404);

        if (dto.getStartDate() != null && dto.getEndDate() != null
                && dto.getEndDate().before(dto.getStartDate())) {
            throw new ServiceException("结束日期不能早于开始日期", 400);
        }

        existing.setTitle(dto.getTitle() != null ? dto.getTitle().trim() : null);
        existing.setCoverImage(dto.getCoverImage() != null ? dto.getCoverImage().trim() : null);
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setPeopleCount(dto.getPeopleCount());
        existing.setBudget(dto.getBudget());
        existing.setTravelType(dto.getTravelType() != null ? dto.getTravelType().trim() : null);
        existing.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : null);
        existing.setSource(dto.getSource());
        tripMapper.update(existing);

        return detail(userId, tripId);
    }

    public TripDetailVO detail(Long userId, Long tripId) {
        TripDetailVO vo = tripMapper.selectById(tripId, userId);
        if (vo == null) throw new ServiceException("行程不存在或无权操作", 404);

        List<TripDayVO> days = dayMapper.selectByTripId(tripId);
        if (!days.isEmpty()) {
            days.forEach(day -> day.setItems(itemMapper.selectByDayId(day.getId())));
        }
        vo.setDays(days);
        return vo;
    }

    @Transactional
    public void delete(Long userId, Long tripId) {
        VoyaAiTrip existing = tripMapper.selectByIdForUpdate(tripId, userId);
        if (existing == null) throw new ServiceException("行程不存在或无权操作", 404);
        tripMapper.softDelete(tripId, userId);
    }

    @Transactional
    public TripDayVO addDay(Long userId, Long tripId, TripDayDTO dto) {
        assertTripOwner(userId, tripId);

        if (dto.getDayNumber() == null) {
            throw new ServiceException("天数不能为空", 400);
        }

        int count = dayMapper.countByDayNumber(tripId, dto.getDayNumber(), null);
        if (count > 0) throw new ServiceException("该行程第" + dto.getDayNumber() + "天已存在", 409);

        VoyaAiTripDay day = new VoyaAiTripDay();
        day.setTripId(tripId);
        day.setDayNumber(dto.getDayNumber());
        day.setDate(dto.getDate());
        day.setTitle(dto.getTitle() != null ? dto.getTitle().trim() : null);
        day.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : "");
        dayMapper.insert(day);

        return dayToVO(day);
    }

    @Transactional
    public TripDayVO updateDay(Long userId, Long tripId, Long dayId, TripDayDTO dto) {
        assertTripOwner(userId, tripId);

        VoyaAiTripDay existing = dayMapper.selectById(dayId);
        if (existing == null || !existing.getTripId().equals(tripId)) {
            throw new ServiceException("行程天不存在", 404);
        }

        if (dto.getDayNumber() != null) {
            int count = dayMapper.countByDayNumber(tripId, dto.getDayNumber(), dayId);
            if (count > 0) throw new ServiceException("该行程第" + dto.getDayNumber() + "天已存在", 409);
        }

        existing.setDayNumber(dto.getDayNumber() != null ? dto.getDayNumber() : existing.getDayNumber());
        existing.setDate(dto.getDate() != null ? dto.getDate() : existing.getDate());
        existing.setTitle(dto.getTitle() != null ? dto.getTitle().trim() : existing.getTitle());
        existing.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : existing.getDescription());
        dayMapper.update(existing);

        return dayToVO(existing);
    }

    @Transactional
    public void deleteDay(Long userId, Long tripId, Long dayId) {
        assertTripOwner(userId, tripId);

        VoyaAiTripDay existing = dayMapper.selectById(dayId);
        if (existing == null || !existing.getTripId().equals(tripId)) {
            throw new ServiceException("行程天不存在", 404);
        }
        dayMapper.softDelete(dayId);
    }

    @Transactional
    public TripItemVO addItem(Long userId, Long tripId, Long dayId, TripItemDTO dto) {
        assertTripOwner(userId, tripId);

        VoyaAiTripDay day = dayMapper.selectById(dayId);
        if (day == null || !day.getTripId().equals(tripId)) {
            throw new ServiceException("行程天不存在", 404);
        }

        int sort = dto.getSort() != null && dto.getSort() > 0 ? dto.getSort()
                : itemMapper.selectMaxSort(dayId) + 1;

        VoyaAiTripItem item = new VoyaAiTripItem();
        item.setDayId(dayId);
        item.setAttractionId(dto.getAttractionId());
        item.setItemType(dto.getItemType());
        item.setTitle(dto.getTitle().trim());
        item.setStartTime(dto.getStartTime() != null ? dto.getStartTime().trim() : null);
        item.setEndTime(dto.getEndTime() != null ? dto.getEndTime().trim() : null);
        item.setAddress(dto.getAddress() != null ? dto.getAddress().trim() : null);
        item.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : "");
        item.setEstimatedCost(dto.getEstimatedCost());
        item.setSort(sort);
        itemMapper.insert(item);

        return itemToVO(item);
    }

    @Transactional
    public TripItemVO updateItem(Long userId, Long tripId, Long dayId, Long itemId, TripItemDTO dto) {
        assertTripOwner(userId, tripId);

        VoyaAiTripItem existing = itemMapper.selectById(itemId);
        if (existing == null || !existing.getDayId().equals(dayId)) {
            throw new ServiceException("行程项不存在", 404);
        }

        existing.setAttractionId(dto.getAttractionId() != null ? dto.getAttractionId() : existing.getAttractionId());
        existing.setItemType(dto.getItemType() != null ? dto.getItemType() : existing.getItemType());
        existing.setTitle(dto.getTitle() != null ? dto.getTitle().trim() : existing.getTitle());
        existing.setStartTime(dto.getStartTime() != null ? dto.getStartTime().trim() : existing.getStartTime());
        existing.setEndTime(dto.getEndTime() != null ? dto.getEndTime().trim() : existing.getEndTime());
        existing.setAddress(dto.getAddress() != null ? dto.getAddress().trim() : existing.getAddress());
        existing.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : existing.getDescription());
        existing.setEstimatedCost(dto.getEstimatedCost() != null ? dto.getEstimatedCost() : existing.getEstimatedCost());
        existing.setSort(dto.getSort() != null ? dto.getSort() : existing.getSort());
        itemMapper.update(existing);

        return itemToVO(existing);
    }

    @Transactional
    public void deleteItem(Long userId, Long tripId, Long dayId, Long itemId) {
        assertTripOwner(userId, tripId);

        VoyaAiTripItem existing = itemMapper.selectById(itemId);
        if (existing == null || !existing.getDayId().equals(dayId)) {
            throw new ServiceException("行程项不存在", 404);
        }
        itemMapper.softDelete(itemId);
    }

    private void assertTripOwner(Long userId, Long tripId) {
        VoyaAiTrip trip = tripMapper.selectByIdForUpdate(tripId, userId);
        if (trip == null) throw new ServiceException("行程不存在或无权操作", 404);
    }

    private void validateCreate(TripDTO dto) {
        if (tripMapper.countEnabledCity(dto.getCityId()) == 0) {
            throw new ServiceException("城市不存在或已停用", 400);
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null
                && dto.getEndDate().before(dto.getStartDate())) {
            throw new ServiceException("结束日期不能早于开始日期", 400);
        }
    }

    private TripDayVO dayToVO(VoyaAiTripDay day) {
        TripDayVO vo = new TripDayVO();
        vo.setId(day.getId());
        vo.setDayNumber(day.getDayNumber());
        vo.setDate(day.getDate());
        vo.setTitle(day.getTitle());
        vo.setDescription(day.getDescription());
        return vo;
    }

    private TripItemVO itemToVO(VoyaAiTripItem item) {
        TripItemVO vo = new TripItemVO();
        vo.setId(item.getId());
        vo.setAttractionId(item.getAttractionId());
        vo.setItemType(item.getItemType());
        vo.setTitle(item.getTitle());
        vo.setStartTime(item.getStartTime());
        vo.setEndTime(item.getEndTime());
        vo.setAddress(item.getAddress());
        vo.setDescription(item.getDescription());
        vo.setEstimatedCost(item.getEstimatedCost());
        vo.setSort(item.getSort());
        return vo;
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
