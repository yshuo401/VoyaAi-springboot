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
public class VoyaAiTrip {
    private Long id;
    private Long userId;
    private Long cityId;
    private String title;
    private String coverImage;
    private Date startDate;
    private Date endDate;
    private Integer peopleCount;
    private BigDecimal budget;
    private String travelType;
    private String description;
    private String source;
    private String status;
    private String delFlag;
    private Date createTime;
    private Date updateTime;
}