package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiTripDay;
import com.ruoyi.voyaai.domain.vo.TripDayVO;

public interface VoyaAiTripDayMapper {

    int insert(VoyaAiTripDay day);

    int update(VoyaAiTripDay day);

    List<TripDayVO> selectByTripId(@Param("tripId") Long tripId);

    VoyaAiTripDay selectById(@Param("id") Long id);

    int softDelete(@Param("id") Long id);

    int countByDayNumber(@Param("tripId") Long tripId, @Param("dayNumber") Integer dayNumber,
            @Param("excludeId") Long excludeId);
}