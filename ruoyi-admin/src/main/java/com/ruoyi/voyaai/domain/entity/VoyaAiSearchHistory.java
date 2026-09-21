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
public class VoyaAiSearchHistory {
    private Long id;
    private Long userId;
    private String keyword;
    private Date createTime;
}