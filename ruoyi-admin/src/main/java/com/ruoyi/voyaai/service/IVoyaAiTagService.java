package com.ruoyi.voyaai.service;

import java.util.List;
import com.ruoyi.voyaai.domain.dto.TagDTO;
import com.ruoyi.voyaai.domain.vo.TagVO;

public interface IVoyaAiTagService {

    List<TagVO> list(String type, String name, String status, String beginCreateTime, String endCreateTime);

    TagVO detail(Long id);

    int create(TagDTO dto, String username);

    int update(TagDTO dto, String username);

    int changeStatus(Long id, String status, String username);

    int delete(Long[] ids, String username);
}