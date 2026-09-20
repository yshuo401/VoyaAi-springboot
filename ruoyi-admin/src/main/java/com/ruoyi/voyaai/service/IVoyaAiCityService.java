package com.ruoyi.voyaai.service;

import java.util.List;

import com.ruoyi.voyaai.domain.dto.CityCreateDTO;
import com.ruoyi.voyaai.domain.dto.CityQueryDTO;
import com.ruoyi.voyaai.domain.dto.CityUpdateDTO;
import com.ruoyi.voyaai.domain.vo.CityVO;

public interface IVoyaAiCityService {
    CityVO selectVoyaAiCityById(Long id);

    List<CityVO> selectVoyaAiCityList(CityQueryDTO city);

    int insertVoyaAiCity(CityCreateDTO city, String username);

    int updateVoyaAiCity(CityUpdateDTO city, String username);

    int deleteVoyaAiCityByIds(Long[] ids);

    int deleteVoyaAiCityById(Long id);

    int updateVoyaAiCityStatus(Long id, String status, String username);
}
