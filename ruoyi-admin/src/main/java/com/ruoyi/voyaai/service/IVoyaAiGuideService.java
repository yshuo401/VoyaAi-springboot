package com.ruoyi.voyaai.service;

import com.ruoyi.voyaai.domain.dto.AppGuideQueryDTO;
import com.ruoyi.voyaai.domain.dto.GuideDTO;
import com.ruoyi.voyaai.domain.dto.GuideQueryDTO;
import com.ruoyi.voyaai.domain.dto.GuideStatusDTO;
import com.ruoyi.voyaai.domain.vo.GuideVO;

import java.util.List;

public interface IVoyaAiGuideService {
    List<GuideVO> list(GuideQueryDTO query);
    List<GuideVO> publicList(AppGuideQueryDTO query);
    GuideVO detail(Long id);
    GuideVO publicDetail(Long id);
    int create(GuideDTO dto, String username);
    int update(GuideDTO dto, String username);
    int changeStatus(GuideStatusDTO dto, String username);
    int delete(Long[] ids, String username);
}
