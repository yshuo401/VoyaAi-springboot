package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.vo.CityVO;

public interface IVoyaAiCityService {
    List<CityVO> list(CityQueryDTO query);
    CityVO detail(Long id);
    int create(CityCreateDTO dto, String username);
    int update(CityUpdateDTO dto, String username);
    int changeStatus(RegionStatusDTO dto, String username);
    int delete(Long[] ids, String username);
}
