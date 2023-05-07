package com.example.activityservice.controller;

import com.example.activityservice.dto.CalendarDTO;
import com.example.activityservice.entity.DiaryEntity;
import com.example.activityservice.repository.DiaryRepository;
import com.example.activityservice.repository.RunningRepository;
import com.example.activityservice.repository.SleepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity-service")
public class DiaryController {
    final DiaryRepository diaryRepository;
    final RunningRepository runningRepository;
    final SleepRepository sleepRepository;


    /**다이어리 작성*/
    @PutMapping("/diary")
    public HttpStatus createCalender(@RequestParam("id") Long userId, @RequestBody String content){
        DiaryEntity diary = DiaryEntity.builder()
                                        .userId(userId)
                                        .content(content)
                                        .createdTime(new Date())
                                        .build();

        diaryRepository.save(diary);

        return HttpStatus.CREATED;
    }

    /**모든 기록 조회*/
    @GetMapping("/calender/{month}")
    public ResponseEntity<CalendarDTO> findAllCalender(@RequestParam("id") Long userId, @PathVariable("month") Date month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);

        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<CalendarDTO.DiaryDTO> diaryList = diaryRepository.findDiaryByDate(startDate, endDate, userId);
        List<CalendarDTO.RunningDTO> runningList = runningRepository.findRunningByDate(startDate, endDate, userId);
        List<CalendarDTO.SleepDTO> sleepList = sleepRepository.findSleepByDate(startDate, endDate, userId);

        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setDiaryList(diaryList);
        calendarDTO.setRunningList(runningList);
        calendarDTO.setSleepList(sleepList);

        return ResponseEntity.status(HttpStatus.OK).body(calendarDTO);
    }
}
