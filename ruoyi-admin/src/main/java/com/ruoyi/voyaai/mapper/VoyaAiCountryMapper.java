package com.ruoyi.voyaai.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiCountry;
import com.ruoyi.voyaai.domain.dto.CountryQueryDTO;
import com.ruoyi.voyaai.domain.vo.CountryVO;

public interface VoyaAiCountryMapper {
    List<CountryVO> selectList(CountryQueryDTO query);

    CountryVO selectById(Long id);

    VoyaAiCountry lockById(Long id);

    long countDuplicate(VoyaAiCountry entity);

    long countReferences(Long id);

    int insert(VoyaAiCountry entity);

    int update(VoyaAiCountry entity);

    int changeStatus(VoyaAiCountry entity);

    int softDelete(@Param("id") Long id, @Param("username") String username);

    List<CountryVO> selectOptions(@Param("countryId") Long countryId, @Param("enabledOnly") boolean enabledOnly);
}
