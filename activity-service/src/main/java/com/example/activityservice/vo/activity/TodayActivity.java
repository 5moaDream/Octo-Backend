package com.example.activityservice.vo.activity;

import com.example.activityservice.vo.diary.ResponseDiary;
import com.example.activityservice.vo.running.ResponseRunning;
import com.example.activityservice.vo.sleep.ResponseSleep;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodayActivity {
    private LocalDate date;
    private ResponseDiary diary;
    private ResponseSleep sleep;
    private List<ResponseRunning> running;
}
