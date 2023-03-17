package com.example.activityservice.DTO;

import com.example.activityservice.vo.activity.TodayActivity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activities {
    private int page;
    private int getSize;
    private List<TodayActivity> activityList;
}

// Limit (page-1) * getSize, getSize => a 부터 b 만큼
// max size = activityList size

// page, size 없어도 될 듯? 프론트에서 페이지 보내주고, size는 controller에서 지정 = 현재 클래스 필요없음