package com.ruoyi.voyaai.controller.app;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.ruoyi.common.core.domain.AjaxResult;

@Order(-2)
@RestControllerAdvice(basePackageClasses = AppAuthController.class)
public class AppExceptionHandler {
    @ExceptionHandler(BindException.class)
    public AjaxResult validation(BindException e) { return AjaxResult.error(400, e.getAllErrors().get(0).getDefaultMessage()); }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult json(HttpMessageNotReadableException e) { return AjaxResult.error(400, "请求内容格式不正确"); }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult upload(MaxUploadSizeExceededException e) { return AjaxResult.error(400, "图片超过上传大小限制"); }
}
