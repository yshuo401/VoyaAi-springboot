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
import com.ruoyi.voyaai.domain.vo.AttractionVO;
import com.ruoyi.voyaai.mapper.*;
import com.ruoyi.voyaai.service.IVoyaAiAttractionService;
import com.ruoyi.voyaai.service.RegionValidation;
import com.ruoyi.voyaai.domain.vo.CityOptionVO;

@Service
public class VoyaAiAttractionServiceImpl implements IVoyaAiAttractionService {
    private final VoyaAiAttractionMapper mapper;
    private final Validator validator;
    private final VoyaAiCityMapper cityMapper;
    private final VoyaAiProvinceMapper parentMapper;
    private final VoyaAiCountryMapper countryMapper;

    public VoyaAiAttractionServiceImpl(VoyaAiAttractionMapper mapper, Validator validator, VoyaAiCityMapper cityMapper, VoyaAiProvinceMapper parentMapper, VoyaAiCountryMapper countryMapper) {
        this.mapper = mapper;
        this.cityMapper = cityMapper;
        this.validator = validator;
        this.parentMapper = parentMapper;
        this.countryMapper = countryMapper;
    }

    @Override
    public List<AttractionVO> list(AttractionQueryDTO query) {
        RegionValidation.validate(validator, query);
        // Return the original PageHelper List, preserving total/page metadata.
        return mapper.selectList(query);
    }

    @Override
    public List<AttractionVO> publicList(Long cityId, String keyword) {
        RegionValidation.id(cityId);
        if (keyword != null && keyword.length() > 100) {
            throw new ServiceException("搜索关键词最多100字", 400);
        }
        return mapper.selectPublicList(cityId, keyword == null ? null : keyword.trim());
    }

    @Override
    public AttractionVO publicDetail(Long id) {
        RegionValidation.id(id);
        AttractionVO result = mapper.selectPublicById(id);
        if (result == null) throw new ServiceException("景点不存在或暂未上架", 404);
        result.setImages(mapper.selectImages(id));
        return result;
    }

    @Override
    public AttractionVO detail(Long id) {
        RegionValidation.id(id);
        AttractionVO result = mapper.selectById(id);
        if (result == null) throw new ServiceException("景点不存在或已删除", 404);
        result.setImages(mapper.selectImages(id));
        return result;
    }

    @Override
    @Transactional
    public int create(AttractionDTO dto, String username) {
        RegionValidation.validate(validator, dto, CreateGroup.class);
        VoyaAiAttraction attraction = entity(dto);
        checkParent(attraction);
        checkUnique(attraction);
        attraction.setCreateBy(username);
        int affected = mapper.insert(attraction);
        saveImages(attraction.getId(), dto.getImages());
        return affected;
    }

    @Override
    @Transactional
    public int update(AttractionDTO dto, String username) {
        RegionValidation.validate(validator, dto, UpdateGroup.class);
        requireExisting(dto.getId());
        VoyaAiAttraction attraction = entity(dto);
        checkParent(attraction);
        checkUnique(attraction);
        attraction.setUpdateBy(username);
        int affected = mapper.update(attraction);
        saveImages(attraction.getId(), dto.getImages());
        return affected;
    }

    @Override
    @Transactional
    public int changeStatus(RegionStatusDTO dto, String username) {
        RegionValidation.validate(validator, dto);
        VoyaAiAttraction attraction = requireExisting(dto.getId());
        if ("0".equals(dto.getStatus())) checkParent(attraction);
        attraction.setStatus(dto.getStatus());
        attraction.setUpdateBy(username);
        return mapper.changeStatus(attraction);
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
            if (count > 0) throw new ServiceException("景点ID " + id + " 存在 " + count + " 条关联行程项、收藏、点赞或评论，不能删除", 409);
        }
        int affected = 0;
        for (Long id : orderedIds) affected += mapper.softDelete(id, username);
        return affected;
    }

    private VoyaAiAttraction requireExisting(Long id) {
        RegionValidation.id(id);
        VoyaAiAttraction attraction = mapper.lockById(id);
        if (attraction == null) throw new ServiceException("景点不存在或已删除", 404);
        return attraction;
    }

    private void checkUnique(VoyaAiAttraction attraction) {
        if (mapper.countDuplicate(attraction) > 0) {
            throw new ServiceException("景点在该上级地区下的名称已存在（含已删除记录）", 409);
        }
    }

    private void checkParent(VoyaAiAttraction attraction) {
        VoyaAiCity city = cityMapper.lockById(attraction.getCityId());
        if (city == null || !"0".equals(city.getStatus())) {
            throw new ServiceException("请选择存在且启用的城市", 409);
        }
        VoyaAiProvince parent = parentMapper.lockById(city.getProvinceId());
        if (parent == null || !"0".equals(parent.getStatus())) {
            throw new ServiceException("请选择存在且启用的省份", 409);
        }
        VoyaAiCountry country = countryMapper.lockById(parent.getCountryId());
        if (country == null || !"0".equals(country.getStatus())) {
            throw new ServiceException("该省份所属国家已停用或删除", 409);
        }
    }

    private VoyaAiAttraction entity(AttractionDTO dto) {
        VoyaAiAttraction entity = new VoyaAiAttraction();
        // Only allowlisted DTO properties are copied; audit fields never come from the client.
        BeanUtils.copyProperties(dto, entity);
        entity.setName(dto.getName().trim());
        return entity;
    }

    private void saveImages(Long id, java.util.List<String> images) {
        mapper.deleteImages(id);
        java.util.List<String> normalized = images.stream().map(String::trim).distinct().toList();
        if (!normalized.isEmpty()) mapper.insertImages(id, normalized);
    }

    @Override
    public List<CityOptionVO> cityOptions(Long provinceId) {
        RegionValidation.id(provinceId);
        return mapper.selectCityOptions(provinceId);
    }
}