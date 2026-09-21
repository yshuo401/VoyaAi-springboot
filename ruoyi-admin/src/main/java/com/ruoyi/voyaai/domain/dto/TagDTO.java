package com.ruoyi.voyaai.domain.dto;

import com.ruoyi.voyaai.domain.dto.group.CreateGroup;
import com.ruoyi.voyaai.domain.dto.group.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TagDTO {

    @Null(groups = CreateGroup.class, message = "创建时不能指定ID")
    @NotNull(groups = UpdateGroup.class, message = "ID不能为空")
    @Positive
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称最多50字")
    private String name;

    @NotBlank(message = "标签类型不能为空")
    @Size(max = 30, message = "标签类型最多30字")
    private String type;

    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序不能小于0")
    private Integer sort = 0;

    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "[01]", message = "状态只能为0或1")
    private String status = "0";

    @Size(max = 500, message = "备注最多500字")
    private String remark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
