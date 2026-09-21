package com.ruoyi.voyaai.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyaAiGuide extends BaseEntity {
    private Long id;
    private Long cityId;
    private String title;
    private String coverImage;
    private String summary;
    private String content;
    private String guideType;
    private Integer days;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private String publishStatus;
    private Date publishTime;
    private Long viewCount;
    private Long likeCount;
    private Long favoriteCount;
    private Long commentCount;
    private Integer sort;
    private String delFlag;
}
