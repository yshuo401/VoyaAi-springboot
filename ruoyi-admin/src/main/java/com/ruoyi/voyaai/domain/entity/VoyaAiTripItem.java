package com.ruoyi.voyaai.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyaAiTripItem {
    private Long id;
    private Long dayId;
    private Long attractionId;
    private String itemType;
    private String title;
    private String startTime;
    private String endTime;
    private String address;
    private String description;
    private BigDecimal estimatedCost;
    private Integer sort;
    private String delFlag;
    private Date createTime;
    private Date updateTime;
}