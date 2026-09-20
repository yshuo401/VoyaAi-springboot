package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.vo.CountryVO;

public interface IVoyaAiCountryService {
    List<CountryVO> list(CountryQueryDTO query);
    CountryVO detail(Long id);
    int create(CountryCreateDTO dto, String username);
    int update(CountryUpdateDTO dto, String username);
    int changeStatus(RegionStatusDTO dto, String username);
    int delete(Long[] ids, String username);
    List<CountryVO> options(Long countryId, boolean enabledOnly);
}
