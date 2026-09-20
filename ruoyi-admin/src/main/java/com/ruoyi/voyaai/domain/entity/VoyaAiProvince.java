package com.ruoyi.voyaai.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyaAiProvince extends BaseEntity {
    private Long id;
    private Long countryId;
    private String name;
    private Integer sort;
    private String status;
    private String code;
    private String delFlag;
}