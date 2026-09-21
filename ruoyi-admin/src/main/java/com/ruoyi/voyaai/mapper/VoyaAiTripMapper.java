package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiTrip;
import com.ruoyi.voyaai.domain.vo.TripDetailVO;
import com.ruoyi.voyaai.domain.vo.TripListVO;

public interface VoyaAiTripMapper {

    int insert(VoyaAiTrip trip);

    int update(VoyaAiTrip trip);

    TripDetailVO selectById(@Param("id") Long id, @Param("userId") Long userId);

    VoyaAiTrip selectByIdForUpdate(@Param("id") Long id, @Param("userId") Long userId);

    List<TripListVO> selectList(@Param("userId") Long userId, @Param("keyword") String keyword,
            @Param("status") String status);

    int softDelete(@Param("id") Long id, @Param("userId") Long userId);

    List<TripListVO> selectAdminList(@Param("cityId") Long cityId, @Param("keyword") String keyword,
            @Param("source") String source, @Param("beginCreateTime") String beginCreateTime,
            @Param("endCreateTime") String endCreateTime);

    TripDetailVO selectAdminById(@Param("id") Long id);

    long countEnabledCity(@Param("cityId") Long cityId);
}
