package com.ruoyi.voyaai.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.TagDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiTag;
import com.ruoyi.voyaai.domain.vo.TagVO;
import com.ruoyi.voyaai.mapper.VoyaAiTagMapper;
import com.ruoyi.voyaai.service.IVoyaAiTagService;

@Service
public class VoyaAiTagServiceImpl implements IVoyaAiTagService {

    private final VoyaAiTagMapper mapper;

    public VoyaAiTagServiceImpl(VoyaAiTagMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<TagVO> list(String type, String name, String status, String beginCreateTime, String endCreateTime) {
        return mapper.selectAdminList(trimToNull(type), trimToNull(name),
                trimToNull(status), trimToNull(beginCreateTime), trimToNull(endCreateTime));
    }

    @Override
    public TagVO detail(Long id) {
        if (id == null || id <= 0) throw new ServiceException("ID不合法", 400);
        TagVO vo = mapper.selectById(id);
        if (vo == null) throw new ServiceException("标签不存在或已删除", 404);
        return vo;
    }

    @Override
    @Transactional
    public int create(TagDTO dto, String username) {
        String name = dto.getName().trim();
        String type = dto.getType().trim();
        checkDuplicate(type, name, null);
        VoyaAiTag entity = new VoyaAiTag();
        entity.setName(name);
        entity.setType(type);
        entity.setSort(dto.getSort());
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        entity.setCreateBy(username);
        return mapper.insert(entity);
    }

    @Override
    @Transactional
    public int update(TagDTO dto, String username) {
        VoyaAiTag existing = requireExisting(dto.getId());
        String name = dto.getName().trim();
        String type = dto.getType().trim();
        checkDuplicate(type, name, dto.getId());
        existing.setName(name);
        existing.setType(type);
        existing.setSort(dto.getSort());
        existing.setStatus(dto.getStatus());
        existing.setRemark(dto.getRemark());
        existing.setUpdateBy(username);
        return mapper.update(existing);
    }

    @Override
    @Transactional
    public int changeStatus(Long id, String status, String username) {
        requireExisting(id);
        return mapper.changeStatus(id, status, username);
    }

    @Override
    @Transactional
    public int delete(Long[] ids, String username) {
        if (ids == null || ids.length == 0) throw new ServiceException("请选择要删除的标签", 400);
        List<Long> orderedIds = Arrays.stream(ids).distinct().sorted().toList();
        for (Long id : orderedIds) {
            requireExisting(id);
            long ref = mapper.countReferences(id);
            if (ref > 0) throw new ServiceException("标签ID " + id + " 存在 " + ref + " 条攻略关联，不能删除", 409);
        }
        int affected = 0;
        for (Long id : orderedIds) affected += mapper.softDelete(id, username);
        return affected;
    }

    private VoyaAiTag requireExisting(Long id) {
        TagVO vo = mapper.selectById(id);
        if (vo == null) throw new ServiceException("标签不存在或已删除", 404);
        VoyaAiTag entity = new VoyaAiTag();
        entity.setId(vo.getId());
        entity.setName(vo.getName());
        entity.setType(vo.getType());
        entity.setSort(vo.getSort());
        entity.setStatus(vo.getStatus());
        entity.setRemark(vo.getRemark());
        return entity;
    }

    private void checkDuplicate(String type, String name, Long excludeId) {
        if (mapper.countDuplicate(type, name, excludeId) > 0) {
            throw new ServiceException("该类型下标签名称已存在", 409);
        }
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
