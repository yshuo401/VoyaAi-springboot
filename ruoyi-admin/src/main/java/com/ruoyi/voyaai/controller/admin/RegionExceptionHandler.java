package com.ruoyi.voyaai.controller.admin;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ruoyi.common.core.domain.AjaxResult;

/** Keep RuoYi's code/msg response contract; never expose SQL in conflict errors. */
@Order(-1)
@RestControllerAdvice(basePackageClasses = CityController.class)
public class RegionExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public AjaxResult duplicate(DuplicateKeyException e) {
        return AjaxResult.error(409, "名称或编码已存在（含已删除记录），请修改后重试");
    }

    @ExceptionHandler(CannotAcquireLockException.class)
    public AjaxResult locked(CannotAcquireLockException e) {
        return AjaxResult.error(409, "数据正在被其他请求修改，请重试");
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public AjaxResult invalid(BindException e) {
        return AjaxResult.error(400, e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult unreadable(HttpMessageNotReadableException e) {
        return AjaxResult.error(400, "请求格式不正确，请检查数字、经纬度和JSON格式");
    }
}
