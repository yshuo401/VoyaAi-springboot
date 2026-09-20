package com.ruoyi.voyaai.service;

import jakarta.validation.Validator;
import com.ruoyi.common.exception.ServiceException;

/** Shared validation for the country/province/city services. */
public final class RegionValidation {
    private RegionValidation() { }

    public static void validate(Validator validator, Object dto) {
        if (dto == null) throw new ServiceException("请求参数不能为空", 400);
        var errors = validator.validate(dto);
        if (!errors.isEmpty()) throw new ServiceException(errors.iterator().next().getMessage(), 400);
    }

    public static void id(Long id) {
        if (id == null || id <= 0) throw new ServiceException("ID必须大于0", 400);
    }

    public static void ids(Long[] ids) {
        if (ids == null || ids.length == 0 || ids.length > 100) {
            throw new ServiceException("请选择1至100条记录", 400);
        }
        for (Long value : ids) id(value);
    }
}
