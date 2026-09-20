package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.*;

public class CountryUpdateDTO extends CountryCreateDTO {

    @NotNull(message = "ID不能为空")
    @Positive
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
