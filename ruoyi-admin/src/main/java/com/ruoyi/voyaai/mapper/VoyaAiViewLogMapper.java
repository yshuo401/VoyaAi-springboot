package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiViewLog;
import com.ruoyi.voyaai.domain.vo.ViewLogVO;

public interface VoyaAiViewLogMapper {

    int insert(VoyaAiViewLog log);

    List<ViewLogVO> selectByUser(@Param("userId") Long userId);

    int removeByUser(@Param("userId") Long userId);

    List<ViewLogVO> selectAdminList(@Param("targetType") String targetType, @Param("nickname") String nickname,
            @Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    ViewLogVO selectById(@Param("id") Long id);

    long countExistingTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    int adjustCityViewCount(@Param("targetId") Long targetId, @Param("delta") int delta);

    int adjustAttractionViewCount(@Param("targetId") Long targetId, @Param("delta") int delta);

    int adjustGuideViewCount(@Param("targetId") Long targetId, @Param("delta") int delta);
}