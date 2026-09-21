package com.ruoyi.voyaai.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public class TripDayDTO {

    @Min(value = 1, message = "天数必须从1开始")
    private Integer dayNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Size(max = 200, message = "当天标题最多200字")
    private String title;

    @Size(max = 1000, message = "当天描述最多1000字")
    private String description;

    public Integer getDayNumber() { return dayNumber; }
    public void setDayNumber(Integer dayNumber) { this.dayNumber = dayNumber; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
