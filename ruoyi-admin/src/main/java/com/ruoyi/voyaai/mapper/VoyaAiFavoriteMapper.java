package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiFavorite;
import com.ruoyi.voyaai.domain.vo.FavoriteVO;

public interface VoyaAiFavoriteMapper {

    List<FavoriteVO> selectList(@Param("userId") Long userId, @Param("targetType") String targetType);

    FavoriteVO selectByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    VoyaAiFavorite lockByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    long countByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    int insert(VoyaAiFavorite favorite);

    int delete(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    List<FavoriteVO> selectAll();

    FavoriteVO selectById(@Param("id") Long id);

    int removeById(@Param("id") Long id);
}