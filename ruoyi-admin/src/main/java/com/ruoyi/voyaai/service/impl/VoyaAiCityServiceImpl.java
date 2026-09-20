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
import com.ruoyi.voyaai.domain.vo.CityVO;
import com.ruoyi.voyaai.mapper.*;
import com.ruoyi.voyaai.service.IVoyaAiCityService;
import com.ruoyi.voyaai.service.RegionValidation;

@Service
public class VoyaAiCityServiceImpl implements IVoyaAiCityService {
    private final VoyaAiCityMapper mapper;
    private final Validator validator;
    private final VoyaAiProvinceMapper parentMapper;
    private final VoyaAiCountryMapper countryMapper;

    public VoyaAiCityServiceImpl(VoyaAiCityMapper mapper, Validator validator, VoyaAiProvinceMapper parentMapper, VoyaAiCountryMapper countryMapper) {
        this.mapper = mapper;
        this.validator = validator;
        this.parentMapper = parentMapper;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<CityVO> list(CityQueryDTO query) {
        RegionValidation.validate(validator, query);
        // Return the original PageHelper List, preserving total/page metadata.
        return mapper.selectList(query);
    }

    @Override
    public List<CityVO> publicList(String keyword) {
        if (keyword != null && keyword.length() > 100) {
            throw new ServiceException("搜索关键词最多100字", 400);
        }
        return mapper.selectPublicList(keyword == null ? null : keyword.trim());
    }

    @Override
    public CityVO detail(Long id) {
        RegionValidation.id(id);
        CityVO result = mapper.selectById(id);
        if (result == null) throw new ServiceException("城市不存在或已删除", 404);
        return result;
    }

    @Override
    @Transactional
    public int create(CityDTO dto, String username) {
        RegionValidation.validate(validator, dto, CreateGroup.class);
        VoyaAiCity city = entity(dto);
        checkParent(city);
        checkUnique(city);
        city.setCreateBy(username);
        return mapper.insert(city);
    }

    @Override
    @Transactional
    public int update(CityDTO dto, String username) {
        RegionValidation.validate(validator, dto, UpdateGroup.class);
        requireExisting(dto.getId());
        VoyaAiCity city = entity(dto);
        checkParent(city);
        checkUnique(city);
        city.setUpdateBy(username);
        return mapper.update(city);
    }

    @Override
    @Transactional
    public int changeStatus(RegionStatusDTO dto, String username) {
        RegionValidation.validate(validator, dto);
        VoyaAiCity city = requireExisting(dto.getId());
        if ("0".equals(dto.getStatus())) checkParent(city);
        city.setStatus(dto.getStatus());
        city.setUpdateBy(username);
        return mapper.changeStatus(city);
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
            if (count > 0) throw new ServiceException("城市ID " + id + " 存在 " + count + " 条关联景点、攻略或行程，不能删除", 409);
        }
        int affected = 0;
        for (Long id : orderedIds) affected += mapper.softDelete(id, username);
        return affected;
    }

    private VoyaAiCity requireExisting(Long id) {
        RegionValidation.id(id);
        VoyaAiCity city = mapper.lockById(id);
        if (city == null) throw new ServiceException("城市不存在或已删除", 404);
        return city;
    }

    private void checkUnique(VoyaAiCity city) {
        if (mapper.countDuplicate(city) > 0) {
            throw new ServiceException("城市在该上级地区下的名称已存在（含已删除记录）", 409);
        }
    }

    private void checkParent(VoyaAiCity city) {
        VoyaAiProvince parent = parentMapper.lockById(city.getProvinceId());
        if (parent == null || !"0".equals(parent.getStatus())) {
            throw new ServiceException("请选择存在且启用的省份", 409);
        }
        VoyaAiCountry country = countryMapper.lockById(parent.getCountryId());
        if (country == null || !"0".equals(country.getStatus())) {
            throw new ServiceException("该省份所属国家已停用或删除", 409);
        }
    }

    private VoyaAiCity entity(CityDTO dto) {
        VoyaAiCity entity = new VoyaAiCity();
        // Only allowlisted DTO properties are copied; audit fields never come from the client.
        BeanUtils.copyProperties(dto, entity);
        entity.setName(dto.getName().trim());
        return entity;
    }
}