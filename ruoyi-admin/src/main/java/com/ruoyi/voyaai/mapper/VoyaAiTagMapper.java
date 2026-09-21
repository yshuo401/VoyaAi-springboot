package com.ruoyi.voyaai.mapper;

import com.ruoyi.voyaai.domain.vo.TagVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoyaAiTagMapper {
    List<TagVO> selectPublicList(@Param("type") String type);
}
