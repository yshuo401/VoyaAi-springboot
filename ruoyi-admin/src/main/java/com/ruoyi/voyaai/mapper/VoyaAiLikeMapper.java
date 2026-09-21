package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiLike;
import com.ruoyi.voyaai.domain.vo.LikeVO;

public interface VoyaAiLikeMapper {

    VoyaAiLike lockByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    long countByUserAndTarget(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    long countByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    long countExistingTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    int insert(VoyaAiLike like);

    int delete(@Param("userId") Long userId, @Param("targetType") String targetType, @Param("targetId") Long targetId);

    List<LikeVO> selectAdminList(@Param("targetType") String targetType, @Param("nickname") String nickname,
            @Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    LikeVO selectById(@Param("id") Long id);

    VoyaAiLike selectEntityById(@Param("id") Long id);

    int removeById(@Param("id") Long id);

    int adjustAttractionLikeCount(@Param("targetId") Long targetId, @Param("delta") int delta);

    int adjustGuideLikeCount(@Param("targetId") Long targetId, @Param("delta") int delta);
}
