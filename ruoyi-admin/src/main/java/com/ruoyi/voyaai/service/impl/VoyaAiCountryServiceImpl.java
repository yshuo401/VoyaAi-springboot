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
import com.ruoyi.voyaai.domain.vo.CountryVO;
import com.ruoyi.voyaai.mapper.*;
import com.ruoyi.voyaai.service.IVoyaAiCountryService;
import com.ruoyi.voyaai.service.RegionValidation;

@Service
public class VoyaAiCountryServiceImpl implements IVoyaAiCountryService {
    private final VoyaAiCountryMapper mapper;
    private final Validator validator;

    public VoyaAiCountryServiceImpl(VoyaAiCountryMapper mapper, Validator validator) {
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<CountryVO> list(CountryQueryDTO query) {
        RegionValidation.validate(validator, query);
        // Return the original PageHelper List, preserving total/page metadata.
        return mapper.selectList(query);
    }

    @Override
    public CountryVO detail(Long id) {
        RegionValidation.id(id);
        CountryVO result = mapper.selectById(id);
        if (result == null) throw new ServiceException("国家不存在或已删除", 404);
        return result;
    }

    @Override
    @Transactional
    public int create(CountryDTO dto, String username) {
        RegionValidation.validate(validator, dto, CreateGroup.class);
        VoyaAiCountry country = entity(dto);
        checkParent(country);
        checkUnique(country);
        country.setCreateBy(username);
        return mapper.insert(country);
    }

    @Override
    @Transactional
    public int update(CountryDTO dto, String username) {
        RegionValidation.validate(validator, dto, UpdateGroup.class);
        requireExisting(dto.getId());
        VoyaAiCountry country = entity(dto);
        checkParent(country);
        checkUnique(country);
        country.setUpdateBy(username);
        return mapper.update(country);
    }

    @Override
    @Transactional
    public int changeStatus(RegionStatusDTO dto, String username) {
        RegionValidation.validate(validator, dto);
        VoyaAiCountry country = requireExisting(dto.getId());
        if ("0".equals(dto.getStatus())) checkParent(country);
        country.setStatus(dto.getStatus());
        country.setUpdateBy(username);
        return mapper.changeStatus(country);
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
            if (count > 0) throw new ServiceException("国家ID " + id + " 存在 " + count + " 条关联省份，不能删除", 409);
        }
        int affected = 0;
        for (Long id : orderedIds) affected += mapper.softDelete(id, username);
        return affected;
    }

    private VoyaAiCountry requireExisting(Long id) {
        RegionValidation.id(id);
        VoyaAiCountry country = mapper.lockById(id);
        if (country == null) throw new ServiceException("国家不存在或已删除", 404);
        return country;
    }

    private void checkUnique(VoyaAiCountry country) {
        if (mapper.countDuplicate(country) > 0) {
            throw new ServiceException("国家名称或编码已存在（含已删除记录）", 409);
        }
    }

    private void checkParent(VoyaAiCountry country) {
        // 国家没有上级地区。
    }

    private VoyaAiCountry entity(CountryDTO dto) {
        VoyaAiCountry entity = new VoyaAiCountry();
        // Only allowlisted DTO properties are copied; audit fields never come from the client.
        BeanUtils.copyProperties(dto, entity);
        entity.setName(dto.getName().trim());
        entity.setCode(dto.getCode() == null || dto.getCode().isBlank() ? null : dto.getCode().trim());
        return entity;
    }

    @Override
    public List<CountryVO> options(Long countryId, boolean enabledOnly) {
        return mapper.selectOptions(countryId, enabledOnly);
    }
}