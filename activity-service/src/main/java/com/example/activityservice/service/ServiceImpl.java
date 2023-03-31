package com.example.activityservice.service;

import com.example.activityservice.dao.DiaryDao;
import com.example.activityservice.dao.GuestBookDao;
import com.example.activityservice.dao.RunningDao;
import com.example.activityservice.dao.SleepDao;
import com.example.activityservice.repository.DiaryRepository;
import com.example.activityservice.repository.GuestBookRepository;
import com.example.activityservice.repository.RunningRepository;
import com.example.activityservice.repository.SleepRepository;
import com.example.activityservice.dto.Experience;
import com.example.activityservice.dto.diary.RequestDiary;
import com.example.activityservice.dto.diary.ResponseDiary;
import com.example.activityservice.dto.guestBook.RequestGuestBook;
import com.example.activityservice.dto.guestBook.ResponseGuestBook;
import com.example.activityservice.dto.running.RequestRunning;
import com.example.activityservice.dto.running.ResponseRunning;
import com.example.activityservice.dto.sleep.RequestSleep;
import com.example.activityservice.dto.sleep.ResponseSleep;
import com.example.activityservice.dto.sleep.ResponseSleepList;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements ActivityService{
    RunningRepository runningRepository;
    SleepRepository sleepRepository;
    GuestBookRepository guestBookRepository;
    ModelMapper mapper = new ModelMapper();

    Experience experience = new Experience();



    @Autowired
    public ServiceImpl( RunningRepository runningRepository, SleepRepository sleepRepository, GuestBookRepository guestBookRepository) {
        this.runningRepository = runningRepository;
        this.sleepRepository = sleepRepository;
        this.guestBookRepository = guestBookRepository;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);   //이걸 해줘야 Id값이 변경됨
    }

    //문어 레벨업 & 문어 경험치 획득 [추가 예정]

    // 수면

    @Override
    public ResponseSleep createSleep(RequestSleep sleep) {
        SleepDao sleepDao = mapper.map(sleep, SleepDao.class);
        sleepDao.setWakeUpTime(LocalDateTime.now());

        sleepDao.setTotalSleepTime(ChronoUnit.MINUTES.between(sleepDao.getSleptTime(), sleepDao.getWakeUpTime()));

        if(sleepDao.getTotalSleepTime() < 60)
            return null;

        sleepRepository.save(sleepDao);

        return mapper.map(sleepDao, ResponseSleep.class);
    }

    @Override
    public ResponseSleepList findAllSleepById(long userId) {
        Specification<SleepDao> spec = Specification.where((root, query, builder) -> builder.equal(root.get("userId"), userId));

        List<SleepDao> sleepDaos = sleepRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "sleptTime"));

        List<ResponseSleep> responseSleeps = sleepDaos.stream().map(
                sleepDao -> {
                    ResponseSleep responseSleep = mapper.map(sleepDao, ResponseSleep.class);
                    return responseSleep;
                }
        ).collect(Collectors.toList());

        long weekAverage = 0;

        if(responseSleeps.size() > 7){
            for(int i = responseSleeps.size()-8; i < responseSleeps.size()-1; i++){
                weekAverage += responseSleeps.get(i).getTotalSleepTime();
            }
            weekAverage = weekAverage/7;
        }
        else{
            for (ResponseSleep r :responseSleeps) {
                weekAverage += r.getTotalSleepTime();
            }
            weekAverage = weekAverage/responseSleeps.size();
        }

        ResponseSleepList result = new ResponseSleepList(responseSleeps, weekAverage);

        return result;
    }

    @Override
    public ResponseRunning createRunning(RequestRunning running) {
        RunningDao runningDao = mapper.map(running, RunningDao.class);

        LocalDateTime start = runningDao.getRunningStartTime();
        LocalDateTime end = runningDao.getRunningEndTime();

        long totalTime = ChronoUnit.MINUTES.between(start, end);

        runningDao.setTotalRunningTime(totalTime);

        runningRepository.save(runningDao);

        return mapper.map(runningDao, ResponseRunning.class);
    }

    @Override
    public List<ResponseRunning> findTodayRunningById(long userId) {
        Specification<RunningDao> spec = Specification.where((root, query, builder) -> {
            LocalDate date = LocalDate.now();
            LocalDate tomorrow = date.plusDays(1); // Get tomorrow's date
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endOfDay = LocalDateTime.of(tomorrow, LocalTime.MIDNIGHT);

            return builder.and(
                    builder.equal(root.get("userId"), userId),
                    builder.between(root.get("runningStartTime"), startOfDay, endOfDay)
            );
        });

        List<RunningDao> runningDaoList =  runningRepository.findAll(spec);

        List<ResponseRunning> result = runningDaoList.stream().map(
                runningDao -> {
                    ResponseRunning running = mapper.map(runningDao, ResponseRunning.class);
                    running.setToday(runningDao.getRunningStartTime().toLocalDate());

                    return running;
                }
        ).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<ResponseRunning> findAllRunningById(long userId) {
        Specification<RunningDao> spec = Specification.where((root, query, builder) -> {
            return builder.equal(root.get("userId"), userId);
        });

        List<RunningDao> runningDaoList = runningRepository.findAll(spec);

        List<ResponseRunning> result = runningDaoList.stream().map(
                runningDao -> {
                    ResponseRunning running = mapper.map(runningDao, ResponseRunning.class);
                    running.setToday(runningDao.getRunningStartTime().toLocalDate());


                    return running;
                }
        ).collect(Collectors.toList());

        return result;
    }


    // 방명록


}
