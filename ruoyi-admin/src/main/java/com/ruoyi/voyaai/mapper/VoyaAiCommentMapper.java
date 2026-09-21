package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiComment;
import com.ruoyi.voyaai.domain.vo.CommentVO;

public interface VoyaAiCommentMapper {

    int insert(VoyaAiComment entity);

    VoyaAiComment selectEntityById(@Param("id") Long id);

    CommentVO selectById(@Param("id") Long id);

    List<CommentVO> selectAdminList(@Param("targetType") String targetType, @Param("nickname") String nickname,
            @Param("keyword") String keyword, @Param("parentId") Long parentId,
            @Param("parentIdNotZero") Boolean parentIdNotZero,
            @Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    List<CommentVO> selectTopLevel(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    List<CommentVO> selectRepliesByParentIds(@Param("parentIds") List<Long> parentIds);

    List<CommentVO> selectRepliesByParentId(@Param("parentId") Long parentId);

    int softDelete(@Param("id") Long id);

    int softDeleteReplies(@Param("parentId") Long parentId);

    int removeById(@Param("id") Long id);

    long countExistingTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    long countExistingParent(@Param("parentId") Long parentId, @Param("targetType") String targetType,
            @Param("targetId") Long targetId);

    List<Long> selectLikedCommentIds(@Param("userId") Long userId, @Param("commentIds") List<Long> commentIds);

    int adjustAttractionCommentCount(@Param("targetId") Long targetId, @Param("delta") int delta);

    int adjustGuideCommentCount(@Param("targetId") Long targetId, @Param("delta") int delta);
}
