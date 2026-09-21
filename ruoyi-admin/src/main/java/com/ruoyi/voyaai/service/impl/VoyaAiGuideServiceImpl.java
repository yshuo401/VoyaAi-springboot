package com.ruoyi.voyaai.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.voyaai.domain.dto.*;
import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;
import com.ruoyi.voyaai.domain.entity.VoyaAiCity;
import com.ruoyi.voyaai.domain.entity.VoyaAiGuide;
import com.ruoyi.voyaai.domain.vo.GuideVO;
import com.ruoyi.voyaai.mapper.VoyaAiCityMapper;
import com.ruoyi.voyaai.mapper.VoyaAiGuideMapper;
import com.ruoyi.voyaai.service.IVoyaAiGuideService;
import com.ruoyi.voyaai.service.RegionValidation;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class VoyaAiGuideServiceImpl implements IVoyaAiGuideService {
    private final VoyaAiGuideMapper mapper;
    private final VoyaAiCityMapper cityMapper;
    private final Validator validator;

    public VoyaAiGuideServiceImpl(VoyaAiGuideMapper mapper, VoyaAiCityMapper cityMapper, Validator validator) {
        this.mapper = mapper;
        this.cityMapper = cityMapper;
        this.validator = validator;
    }

    @Override
    public List<GuideVO> list(GuideQueryDTO query) {
        RegionValidation.validate(validator, query);
        return mapper.selectList(query);
    }

    @Override
    public List<GuideVO> publicList(AppGuideQueryDTO query) {
        RegionValidation.validate(validator, query);
        if (query.getKeyword() != null) query.setKeyword(query.getKeyword().trim());
        if (query.getGuideType() != null) query.setGuideType(query.getGuideType().trim());
        return mapper.selectPublicList(query);
    }

    @Override
    public GuideVO detail(Long id) {
        RegionValidation.id(id);
        GuideVO result = mapper.selectById(id);
        if (result == null) throw new ServiceException("攻略不存在或已删除", 404);
        result.setTagIds(mapper.selectTagIds(id));
        return result;
    }

    @Override
    public GuideVO publicDetail(Long id) {
        RegionValidation.id(id);
        GuideVO result = mapper.selectPublicById(id);
        if (result == null) throw new ServiceException("攻略不存在或暂未发布", 404);
        result.setTagIds(mapper.selectTagIds(id));
        return result;
    }

    @Override
    @Transactional
    public int create(GuideDTO dto, String username) {
        RegionValidation.validate(validator, dto, CreateGroup.class);
        VoyaAiGuide guide = entity(dto);
        validateBusiness(guide, dto.getTagIds());
        guide.setCreateBy(username);
        int affected = mapper.insert(guide);
        saveTags(guide.getId(), dto.getTagIds());
        return affected;
    }

    @Override
    @Transactional
    public int update(GuideDTO dto, String username) {
        RegionValidation.validate(validator, dto, UpdateGroup.class);
        requireExisting(dto.getId());
        VoyaAiGuide guide = entity(dto);
        validateBusiness(guide, dto.getTagIds());
        guide.setUpdateBy(username);
        int affected = mapper.update(guide);
        saveTags(guide.getId(), dto.getTagIds());
        return affected;
    }

    @Override
    @Transactional
    public int changeStatus(GuideStatusDTO dto, String username) {
        RegionValidation.validate(validator, dto);
        VoyaAiGuide guide = requireExisting(dto.getId());
        if ("1".equals(dto.getPublishStatus())) checkCity(guide.getCityId());
        guide.setPublishStatus(dto.getPublishStatus());
        guide.setPublishTime("1".equals(dto.getPublishStatus()) ? new Date() : null);
        guide.setUpdateBy(username);
        return mapper.changeStatus(guide);
    }

    @Override
    @Transactional
    public int delete(Long[] ids, String username) {
        RegionValidation.ids(ids);
        List<Long> orderedIds = Arrays.stream(ids).distinct().sorted().toList();
        for (Long id : orderedIds) {
            requireExisting(id);
            long count = mapper.countReferences(id);
            if (count > 0) throw new ServiceException("攻略ID " + id + " 存在 " + count + " 条收藏、点赞或评论，不能删除", 409);
        }
        int affected = 0;
        for (Long id : orderedIds) affected += mapper.softDelete(id, username);
        return affected;
    }

    private VoyaAiGuide requireExisting(Long id) {
        RegionValidation.id(id);
        VoyaAiGuide guide = mapper.lockById(id);
        if (guide == null) throw new ServiceException("攻略不存在或已删除", 404);
        return guide;
    }

    private void validateBusiness(VoyaAiGuide guide, List<Long> tagIds) {
        checkCity(guide.getCityId());
        if (mapper.countDuplicate(guide) > 0) throw new ServiceException("该城市下的攻略标题已存在", 409);
        if (tagIds != null && !tagIds.isEmpty()) {
            long distinct = tagIds.stream().distinct().count();
            if (distinct != tagIds.size() || mapper.countValidTags(tagIds) != distinct) {
                throw new ServiceException("存在无效或重复的攻略标签", 400);
            }
        }
    }

    private void checkCity(Long cityId) {
        VoyaAiCity city = cityMapper.lockById(cityId);
        if (city == null || !"0".equals(city.getStatus())) throw new ServiceException("请选择存在且启用的城市", 409);
    }

    private VoyaAiGuide entity(GuideDTO dto) {
        VoyaAiGuide entity = new VoyaAiGuide();
        BeanUtils.copyProperties(dto, entity);
        entity.setTitle(dto.getTitle().trim());
        if (dto.getGuideType() != null) entity.setGuideType(dto.getGuideType().trim());
        if ("1".equals(dto.getPublishStatus()) && entity.getPublishTime() == null) entity.setPublishTime(new Date());
        if (!"1".equals(dto.getPublishStatus())) entity.setPublishTime(null);
        return entity;
    }

    private void saveTags(Long guideId, List<Long> tagIds) {
        mapper.deleteTags(guideId);
        if (tagIds != null && !tagIds.isEmpty()) mapper.insertTags(guideId, tagIds.stream().distinct().toList());
    }
}
