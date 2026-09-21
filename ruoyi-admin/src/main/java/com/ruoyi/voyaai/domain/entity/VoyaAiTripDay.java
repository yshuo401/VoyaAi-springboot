package com.ruoyi.voyaai.domain.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyaAiTripDay {
    private Long id;
    private Long tripId;
    private Integer dayNumber;
    private Date date;
    private String title;
    private String description;
    private String delFlag;
    private Date createTime;
    private Date updateTime;
}
