package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiAttraction;
import com.ruoyi.voyaai.domain.dto.AttractionQueryDTO;
import com.ruoyi.voyaai.domain.vo.AttractionVO;

public interface VoyaAiAttractionMapper {
    List<AttractionVO> selectList(AttractionQueryDTO query);
    AttractionVO selectById(Long id);
    VoyaAiAttraction lockById(Long id);
    long countDuplicate(VoyaAiAttraction entity);
    long countReferences(Long id);
    int insert(VoyaAiAttraction entity);
    int update(VoyaAiAttraction entity);
    int changeStatus(VoyaAiAttraction entity);
    int softDelete(@Param("id") Long id, @Param("username") String username);
    List<String> selectImages(Long id);
    int deleteImages(Long id);
    int insertImages(@Param("id") Long id, @Param("images") List<String> images);
    List<com.ruoyi.voyaai.domain.vo.CityOptionVO> selectCityOptions(@Param("provinceId") Long provinceId);
}
