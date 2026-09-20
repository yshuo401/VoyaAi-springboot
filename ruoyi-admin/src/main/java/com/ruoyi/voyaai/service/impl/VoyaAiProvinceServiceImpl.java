package com.ruoyi.voyaai.service.impl;

import java.util.Arrays;
import java.util.List;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;
import com.ruoyi.voyaai.domain.entity.*;
import com.ruoyi.voyaai.domain.vo.ProvinceVO;
import com.ruoyi.voyaai.mapper.*;
import com.ruoyi.voyaai.service.IVoyaAiProvinceService;
import com.ruoyi.voyaai.service.RegionValidation;

@Service
public class VoyaAiProvinceServiceImpl implements IVoyaAiProvinceService {
    private final VoyaAiProvinceMapper mapper;
    private final Validator validator;
    private final VoyaAiCountryMapper parentMapper;

    public VoyaAiProvinceServiceImpl(VoyaAiProvinceMapper mapper, Validator validator, VoyaAiCountryMapper parentMapper) {
        this.mapper = mapper;
        this.validator = validator;
        this.parentMapper = parentMapper;
    }

    @Override
    public List<ProvinceVO> list(ProvinceQueryDTO query) {
        RegionValidation.validate(validator, query);
        // Return the original PageHelper List, preserving total/page metadata.
        return mapper.selectList(query);
    }

    @Override
    public ProvinceVO detail(Long id) {
        RegionValidation.id(id);
        ProvinceVO result = mapper.selectById(id);
        if (result == null) throw new ServiceException("省份不存在或已删除", 404);
        return result;
    }

    @Override
    @Transactional
    public int create(ProvinceDTO dto, String username) {
        RegionValidation.validate(validator, dto, CreateGroup.class);
        VoyaAiProvince province = entity(dto);
        checkParent(province);
        checkUnique(province);
        province.setCreateBy(username);
        return mapper.insert(province);
    }

    @Override
    @Transactional
    public int update(ProvinceDTO dto, String username) {
        RegionValidation.validate(validator, dto, UpdateGroup.class);
        requireExisting(dto.getId());
        VoyaAiProvince province = entity(dto);
        checkParent(province);
        checkUnique(province);
        province.setUpdateBy(username);
        return mapper.update(province);
    }

    @Override
    @Transactional
    public int changeStatus(RegionStatusDTO dto, String username) {
        RegionValidation.validate(validator, dto);
        VoyaAiProvince province = requireExisting(dto.getId());
        if ("0".equals(dto.getStatus())) checkParent(province);
        province.setStatus(dto.getStatus());
        province.setUpdateBy(username);
        return mapper.changeStatus(province);
    }

    @Override
    @Transactional
    public int delete(Long[] ids, String username) {
        RegionValidation.ids(ids);
        List<Long> orderedIds = Arrays.stream(ids).distinct().sorted().toList();
        // Check and lock every row before changing any: a batch is all-or-nothing.
        for (Long id : orderedIds) {
            requireExisting(id);
            long count = mapper.countReferences(id);
            if (count > 0) throw new ServiceException("省份ID " + id + " 存在 " + count + " 条关联城市，不能删除", 409);
        }
        int affected = 0;
        for (Long id : orderedIds) affected += mapper.softDelete(id, username);
        return affected;
    }

    private VoyaAiProvince requireExisting(Long id) {
        RegionValidation.id(id);
        VoyaAiProvince province = mapper.lockById(id);
        if (province == null) throw new ServiceException("省份不存在或已删除", 404);
        return province;
    }

    private void checkUnique(VoyaAiProvince province) {
        if (mapper.countDuplicate(province) > 0) {
            throw new ServiceException("省份在该上级地区下的名称已存在（含已删除记录）", 409);
        }
    }

    private void checkParent(VoyaAiProvince province) {
        VoyaAiCountry parent = parentMapper.lockById(province.getCountryId());
        if (parent == null || !"0".equals(parent.getStatus())) {
            throw new ServiceException("请选择存在且启用的国家", 409);
        }
    }

    private VoyaAiProvince entity(ProvinceDTO dto) {
        VoyaAiProvince entity = new VoyaAiProvince();
        // Only allowlisted DTO properties are copied; audit fields never come from the client.
        BeanUtils.copyProperties(dto, entity);
        entity.setName(dto.getName().trim());
        entity.setCode(dto.getCode() == null || dto.getCode().isBlank() ? null : dto.getCode().trim());
        return entity;
    }

    @Override
    public List<ProvinceVO> options(Long countryId, boolean enabledOnly) {
        RegionValidation.id(countryId);
        return mapper.selectOptions(countryId, enabledOnly);
    }
}