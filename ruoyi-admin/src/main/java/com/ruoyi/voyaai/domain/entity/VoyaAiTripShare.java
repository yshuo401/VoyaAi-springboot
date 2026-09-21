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
public class VoyaAiTripShare {
    private Long id;
    private Long tripId;
    private Long userId;
    private String shareCode;
    private String shareToken;
    private Date expireTime;
    private String status;
    private Date createTime;
    private Date updateTime;
}
