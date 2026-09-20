package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.vo.AttractionVO;

public interface IVoyaAiAttractionService {
    List<AttractionVO> list(AttractionQueryDTO query);
    AttractionVO detail(Long id);
    List<com.ruoyi.voyaai.domain.vo.CityOptionVO> cityOptions(Long provinceId);
    int create(AttractionCreateDTO dto, String username);
    int update(AttractionUpdateDTO dto, String username);
    int changeStatus(RegionStatusDTO dto, String username);
    int delete(Long[] ids, String username);
}
