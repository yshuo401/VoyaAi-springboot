package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiTag;
import com.ruoyi.voyaai.domain.vo.TagVO;

public interface VoyaAiTagMapper {

    List<TagVO> selectPublicList(@Param("type") String type);

    List<TagVO> selectAdminList(@Param("type") String type, @Param("name") String name,
            @Param("status") String status, @Param("beginCreateTime") String beginCreateTime,
            @Param("endCreateTime") String endCreateTime);

    TagVO selectById(@Param("id") Long id);

    int insert(VoyaAiTag entity);

    int update(VoyaAiTag entity);

    int changeStatus(@Param("id") Long id, @Param("status") String status,
            @Param("updateBy") String updateBy);

    int softDelete(@Param("id") Long id, @Param("updateBy") String updateBy);

    long countDuplicate(@Param("type") String type, @Param("name") String name,
            @Param("excludeId") Long excludeId);

    long countReferences(@Param("id") Long id);
}