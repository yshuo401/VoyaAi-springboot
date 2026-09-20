package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiCity;
import com.ruoyi.voyaai.domain.dto.CityQueryDTO;
import com.ruoyi.voyaai.domain.vo.CityVO;

public interface VoyaAiCityMapper {
    List<CityVO> selectList(CityQueryDTO query);
    CityVO selectById(Long id);
    VoyaAiCity lockById(Long id);
    long countDuplicate(VoyaAiCity entity);
    long countReferences(Long id);
    int insert(VoyaAiCity entity);
    int update(VoyaAiCity entity);
    int changeStatus(VoyaAiCity entity);
    int softDelete(@Param("id") Long id, @Param("username") String username);
}
