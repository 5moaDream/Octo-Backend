package com.example.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CalendarDTO {
    private List<DiaryDTO> diaryList;
    private List<RunningDTO> runningList;
    private List<SleepDTO> sleepList;

    @Data
    @AllArgsConstructor
    public static class DiaryDTO{
        private Date today;
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class RunningDTO{
        private Date today;
        private Double totalDistance;
    }

    @Data
    @AllArgsConstructor
    public static class SleepDTO{
        private Date today;
        private int sleepTime;
    }
}