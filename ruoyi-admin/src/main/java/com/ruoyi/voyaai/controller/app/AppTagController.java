package com.ruoyi.voyaai.controller.app;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.voyaai.mapper.VoyaAiTagMapper;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/voyaai/tags")
@Anonymous
@Validated
public class AppTagController {
    private final VoyaAiTagMapper mapper;

    public AppTagController(VoyaAiTagMapper mapper) { this.mapper = mapper; }

    @GetMapping
    public AjaxResult list(@RequestParam(required = false) @Size(max = 30, message = "标签类型最多30字") String type) {
        return AjaxResult.success(mapper.selectPublicList(type == null ? null : type.trim()));
    }
}
