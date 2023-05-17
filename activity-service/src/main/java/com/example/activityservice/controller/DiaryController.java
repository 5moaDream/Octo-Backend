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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    final DiaryRepository diaryRepository;
    final RunningRepository runningRepository;
    final SleepRepository sleepRepository;


    /**다이어리 작성*/
    @PutMapping("/diary")
    public HttpStatus writeDiary(@RequestParam("id") Long userId, @RequestBody String content){
        DiaryEntity diary = DiaryEntity.builder()
                                        .userId(userId)
                                        .content(content)
                                        .createdTime(new Date(System.currentTimeMillis()))
                                        .build();

        diaryRepository.save(diary);

        return HttpStatus.CREATED;
    }

    /**모든 기록 조회*/
    @GetMapping("/calender/{month}")
    public ResponseEntity<CalendarDTO> findAllCalender(@RequestParam("id") Long userId, @PathVariable String month) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(simpleDateFormat.parse(month).getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = new Date(calendar.getTimeInMillis());

        calendar.add(Calendar.MONTH, 1);
        Date endDate = new Date(calendar.getTimeInMillis());

        List<Object> diaryListTmp = diaryRepository.findDiaryByDate(startDate, endDate, userId);
        List<CalendarDTO.DiaryDTO> diaryList = new ArrayList<>();

        for (Object o : diaryListTmp) {
            Object[] sublist = (Object[]) o;

            Date today = (Date) sublist[0];
            String content = (String) sublist[1];

            diaryList.add(new CalendarDTO.DiaryDTO(today, content));
        }

        List<Object> runningListTmp = runningRepository.findRunningByDate(startDate, endDate, userId);
        List<CalendarDTO.RunningDTO> runningList = new ArrayList<>();

        for (Object o : runningListTmp) {
            Object[] sublist = (Object[]) o;

            Date today = (Date) sublist[0];
            Double distance = (Double) sublist[1];

            runningList.add(new CalendarDTO.RunningDTO(today, distance));
        }

        List<Object> sleepListTmp = sleepRepository.findSleepByDate(startDate, endDate, userId);
        List<CalendarDTO.SleepDTO> sleepList = new ArrayList<>();

        for (Object o : sleepListTmp) {
            Object[] sublist = (Object[]) o;

            Date today = (Date) sublist[0];
            Integer sleepTime = (Integer) sublist[1];

            sleepList.add(new CalendarDTO.SleepDTO(today, sleepTime));
        }

        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setDiaryList(diaryList);
        calendarDTO.setRunningList(runningList);
        calendarDTO.setSleepList(sleepList);

        return ResponseEntity.status(HttpStatus.OK).body(calendarDTO);
    }
}
