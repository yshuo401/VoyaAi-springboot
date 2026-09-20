package com.ruoyi.voyaai.controller.admin;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.voyaai.domain.dto.CityCreateDTO;
import com.ruoyi.voyaai.domain.dto.CityQueryDTO;
import com.ruoyi.voyaai.domain.dto.CityUpdateDTO;
import com.ruoyi.voyaai.domain.vo.CityVO;
import com.ruoyi.voyaai.service.IVoyaAiCityService;

@RestController
@RequestMapping("/voyaai/city")
public class CityController extends BaseController {
    @Autowired
    private IVoyaAiCityService cityService;

    @PreAuthorize("@ss.hasPermi('voyaai:city:list')")
    @GetMapping("/list")
    public TableDataInfo list(CityQueryDTO city) {
        startPage();
        return getDataTable(cityService.selectVoyaAiCityList(city));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:export')")
    @Log(title = "城市", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CityQueryDTO city) {
        new ExcelUtil<>(CityVO.class).exportExcel(response, cityService.selectVoyaAiCityList(city), "城市数据");
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(cityService.selectVoyaAiCityById(id));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:add')")
    @Log(title = "城市", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CityCreateDTO city) {
        return toAjax(cityService.insertVoyaAiCity(city, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:edit')")
    @Log(title = "城市", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CityUpdateDTO city) {
        return toAjax(cityService.updateVoyaAiCity(city, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody CityUpdateDTO city) {
        return toAjax(cityService.updateVoyaAiCityStatus(city.getId(), city.getStatus(), getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('voyaai:city:remove')")
    @Log(title = "城市", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(cityService.deleteVoyaAiCityByIds(ids));
    }
}
