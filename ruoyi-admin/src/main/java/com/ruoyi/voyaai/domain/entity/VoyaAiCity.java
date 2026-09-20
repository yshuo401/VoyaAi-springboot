package com.ruoyi.voyaai.domain.entity;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyaAiCity extends BaseEntity {
    private Long id;
    private Long provinceId;
    private String name;
    private Integer sort;
    private String status;
    private String coverImage;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String delFlag;
    private Long viewCount;
}