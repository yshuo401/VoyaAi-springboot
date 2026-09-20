package com.ruoyi.voyaai.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.voyaai.domain.dto.CityCreateDTO;
import com.ruoyi.voyaai.domain.dto.CityQueryDTO;
import com.ruoyi.voyaai.domain.dto.CityUpdateDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiCity;
import com.ruoyi.voyaai.domain.vo.CityVO;
import com.ruoyi.voyaai.mapper.VoyaAiCityMapper;
import com.ruoyi.voyaai.service.IVoyaAiCityService;

@Service
public class VoyaAiCityServiceImpl implements IVoyaAiCityService {
    @Autowired
    private VoyaAiCityMapper cityMapper;

    @Override
    public CityVO selectVoyaAiCityById(Long id) {
        return toVO(cityMapper.selectVoyaAiCityById(id));
    }

    @Override
    public List<CityVO> selectVoyaAiCityList(CityQueryDTO query) {
        return cityMapper.selectVoyaAiCityList(toEntity(query)).stream().map(this::toVO).toList();
    }

    @Override
    public int insertVoyaAiCity(CityCreateDTO dto, String username) {
        VoyaAiCity city = toEntity(dto);
        city.setCreateBy(username);
        city.setCreateTime(new Date());
        return cityMapper.insertVoyaAiCity(city);
    }

    @Override
    public int updateVoyaAiCity(CityUpdateDTO dto, String username) {
        VoyaAiCity city = toEntity(dto);
        city.setUpdateBy(username);
        city.setUpdateTime(new Date());
        return cityMapper.updateVoyaAiCity(city);
    }

    @Override
    public int deleteVoyaAiCityByIds(Long[] ids) {
        return cityMapper.deleteVoyaAiCityByIds(ids);
    }

    @Override
    public int deleteVoyaAiCityById(Long id) {
        return cityMapper.deleteVoyaAiCityById(id);
    }

    @Override
    public int updateVoyaAiCityStatus(Long id, String status, String username) {
        VoyaAiCity city = new VoyaAiCity();
        city.setId(id);
        city.setStatus(status);
        city.setUpdateBy(username);
        city.setUpdateTime(new Date());
        return cityMapper.updateVoyaAiCityStatus(city);
    }

    private VoyaAiCity toEntity(CityQueryDTO d) {
        VoyaAiCity e = new VoyaAiCity();
        e.setProvinceId(d.getProvinceId());
        e.setName(d.getName());
        e.setStatus(d.getStatus());
        e.setParams(d.getParams());
        return e;
    }

    private VoyaAiCity toEntity(CityCreateDTO d) {
        VoyaAiCity e = new VoyaAiCity();
        e.setProvinceId(d.getProvinceId());
        e.setName(d.getName());
        e.setCoverImage(d.getCoverImage());
        e.setDescription(d.getDescription());
        e.setLatitude(d.getLatitude());
        e.setLongitude(d.getLongitude());
        e.setSort(d.getSort());
        e.setStatus(d.getStatus());
        e.setRemark(d.getRemark());
        if (d instanceof CityUpdateDTO u) e.setId(u.getId());
        return e;
    }

    private CityVO toVO(VoyaAiCity e) {
        if (e == null) return null;
        CityVO v = new CityVO();
        v.setId(e.getId());
        v.setProvinceId(e.getProvinceId());
        v.setName(e.getName());
        v.setCoverImage(e.getCoverImage());
        v.setDescription(e.getDescription());
        v.setLatitude(e.getLatitude());
        v.setLongitude(e.getLongitude());
        v.setSort(e.getSort());
        v.setStatus(e.getStatus());
        v.setViewCount(e.getViewCount());
        v.setCreateBy(e.getCreateBy());
        v.setCreateTime(e.getCreateTime());
        v.setRemark(e.getRemark());
        return v;
    }
}
