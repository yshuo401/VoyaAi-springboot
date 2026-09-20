package com.ruoyi.voyaai.mapper;

import java.util.List;

import com.ruoyi.voyaai.domain.entity.VoyaAiCity;

public interface VoyaAiCityMapper {
    VoyaAiCity selectVoyaAiCityById(Long id);

    List<VoyaAiCity> selectVoyaAiCityList(VoyaAiCity city);

    int insertVoyaAiCity(VoyaAiCity city);

    int updateVoyaAiCity(VoyaAiCity city);

    int deleteVoyaAiCityById(Long id);

    int deleteVoyaAiCityByIds(Long[] ids);

    int updateVoyaAiCityStatus(VoyaAiCity city);
}
