package com.example.activityservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CalendarDTO {
    private List<DiaryDTO> diaryList;
    private List<RunningDTO> runningList;
    private List<SleepDTO> sleepList;

    @Data
    public class DiaryDTO{
        private Date today;
        private String content;
    }

    @Data
    public class RunningDTO{
        private Date today;
        private float totalDistance;
    }

    @Data
    public class SleepDTO{
        private Date today;
        private int sleepTime;
    }
}
