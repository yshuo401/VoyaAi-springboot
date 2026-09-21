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
public class VoyaAiComment {
    private Long id;
    private Long userId;
    private String targetType;
    private Long targetId;
    private Long parentId;
    private String content;
    private Integer likeCount;
    private String delFlag;
    private Date createTime;
}