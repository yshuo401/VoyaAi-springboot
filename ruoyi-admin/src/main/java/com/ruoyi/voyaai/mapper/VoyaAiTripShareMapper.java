package com.ruoyi.voyaai.mapper;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiTripShare;
import com.ruoyi.voyaai.domain.vo.TripDetailVO;

public interface VoyaAiTripShareMapper {

    int insert(VoyaAiTripShare share);

    VoyaAiTripShare selectByTripId(@Param("tripId") Long tripId);

    VoyaAiTripShare selectByShareCode(@Param("shareCode") String shareCode);

    int invalidate(@Param("tripId") Long tripId);

    TripDetailVO selectTripForShare(@Param("shareCode") String shareCode);
}