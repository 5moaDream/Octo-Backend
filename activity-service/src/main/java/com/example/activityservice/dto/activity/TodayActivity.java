package com.example.activityservice.dto.activity;

import com.example.activityservice.dto.diary.ResponseDiary;
import com.example.activityservice.dto.running.ResponseRunning;
import com.example.activityservice.dto.sleep.ResponseSleep;
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
