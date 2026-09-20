package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.vo.ProvinceVO;

public interface IVoyaAiProvinceService {
    List<ProvinceVO> list(ProvinceQueryDTO query);
    ProvinceVO detail(Long id);
    int create(ProvinceCreateDTO dto, String username);
    int update(ProvinceUpdateDTO dto, String username);
    int changeStatus(RegionStatusDTO dto, String username);
    int delete(Long[] ids, String username);
    List<ProvinceVO> options(Long countryId, boolean enabledOnly);
}
