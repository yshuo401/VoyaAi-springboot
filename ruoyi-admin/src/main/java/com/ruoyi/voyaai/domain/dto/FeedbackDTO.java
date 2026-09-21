package com.ruoyi.voyaai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FeedbackDTO {

    @NotBlank(message = "反馈内容不能为空")
    @Size(max = 1000, message = "反馈内容最多1000字")
    private String content;

    @Size(max = 100, message = "联系方式最多100字")
    private String contact;

    @Size(max = 2000, message = "图片路径最多2000字")
    private String images;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
}