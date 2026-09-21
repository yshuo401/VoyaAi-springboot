package com.ruoyi.voyaai.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoyaAiFeedback {
    private Long id;
    private Long userId;
    private String content;
    private String contact;
    private String images;
    private String status;
    private String handleResult;
    private String handleBy;
}