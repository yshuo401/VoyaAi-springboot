package com.ruoyi.voyaai.mapper;

import com.ruoyi.voyaai.domain.dto.AppGuideQueryDTO;
import com.ruoyi.voyaai.domain.dto.GuideQueryDTO;
import com.ruoyi.voyaai.domain.entity.VoyaAiGuide;
import com.ruoyi.voyaai.domain.vo.GuideVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoyaAiGuideMapper {
    List<GuideVO> selectList(GuideQueryDTO query);
    List<GuideVO> selectPublicList(AppGuideQueryDTO query);
    GuideVO selectById(Long id);
    GuideVO selectPublicById(Long id);
    VoyaAiGuide lockById(Long id);
    long countDuplicate(VoyaAiGuide guide);
    long countReferences(Long id);
    long countValidTags(@Param("tagIds") List<Long> tagIds);
    int insert(VoyaAiGuide guide);
    int update(VoyaAiGuide guide);
    int changeStatus(VoyaAiGuide guide);
    int softDelete(@Param("id") Long id, @Param("username") String username);
    String selectTagNames(Long guideId);
    List<Long> selectTagIds(Long guideId);
    int deleteTags(Long guideId);
    int insertTags(@Param("guideId") Long guideId, @Param("tagIds") List<Long> tagIds);
}
