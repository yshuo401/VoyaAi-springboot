package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiTripItem;
import com.ruoyi.voyaai.domain.vo.TripItemVO;

public interface VoyaAiTripItemMapper {

    int insert(VoyaAiTripItem item);

    int update(VoyaAiTripItem item);

    List<TripItemVO> selectByDayId(@Param("dayId") Long dayId);

    VoyaAiTripItem selectById(@Param("id") Long id);

    int softDelete(@Param("id") Long id);

    Integer selectMaxSort(@Param("dayId") Long dayId);
}