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
public class VoyaAiTag extends BaseEntity {
    private Long id;
    private String name;
    private String type;
    private Integer sort;
    private String status;
    private String delFlag;
}