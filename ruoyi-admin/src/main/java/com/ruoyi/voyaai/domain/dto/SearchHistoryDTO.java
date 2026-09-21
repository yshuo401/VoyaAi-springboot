package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SearchHistoryDTO {

    @NotBlank(message = "关键词不能为空")
    @Size(max = 100, message = "关键词最多100字")
    private String keyword;

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword != null ? keyword.trim() : null; }
}