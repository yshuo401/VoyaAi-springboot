package com.ruoyi.voyaai.controller.app;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.voyaai.domain.vo.AttractionVO;
import com.ruoyi.voyaai.service.IVoyaAiAttractionService;
import com.ruoyi.voyaai.service.IVoyaAiCityService;

/** Public read-only endpoints for the mini-program. No admin permission is required. */
@RestController
@RequestMapping("/app/voyaai")
@Anonymous
public class AppCityController {
    private final IVoyaAiAttractionService attractionService;
    private final IVoyaAiCityService cityService;

    public AppCityController(IVoyaAiAttractionService attractionService, IVoyaAiCityService cityService) {
        this.attractionService = attractionService;
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public AjaxResult cities(@RequestParam(required = false) String keyword) {
        return AjaxResult.success(cityService.publicList(keyword));
    }

    @GetMapping("/cities/{cityId}/attractions")
    public AjaxResult attractions(@PathVariable Long cityId,
                                  @RequestParam(required = false) String keyword) {
        List<AttractionVO> rows = attractionService.publicList(cityId, keyword);
        return AjaxResult.success(rows);
    }

    @GetMapping("/attractions/{id}")
    public AjaxResult attraction(@PathVariable Long id) {
        return AjaxResult.success(attractionService.publicDetail(id));
    }
}
