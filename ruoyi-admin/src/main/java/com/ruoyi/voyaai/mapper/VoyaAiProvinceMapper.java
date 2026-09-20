package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiProvince;
import com.ruoyi.voyaai.domain.dto.ProvinceQueryDTO;
import com.ruoyi.voyaai.domain.vo.ProvinceVO;

public interface VoyaAiProvinceMapper {
    List<ProvinceVO> selectList(ProvinceQueryDTO query);
    ProvinceVO selectById(Long id);
    VoyaAiProvince lockById(Long id);
    long countDuplicate(VoyaAiProvince entity);
    long countReferences(Long id);
    int insert(VoyaAiProvince entity);
    int update(VoyaAiProvince entity);
    int changeStatus(VoyaAiProvince entity);
    int softDelete(@Param("id") Long id, @Param("username") String username);
    List<ProvinceVO> selectOptions(@Param("countryId") Long countryId, @Param("enabledOnly") boolean enabledOnly);
}
