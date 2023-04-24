package com.example.activityservice.service;

import com.example.activityservice.entity.RunningEntity;
import com.example.activityservice.dto.running.RequestRunning;
import com.example.activityservice.dto.running.ResponseRunning;
import com.example.activityservice.repository.RunningRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RunningServiceImpl implements RunningService{

    RunningRepository runningRepository;
    ModelMapper mapper = new ModelMapper();

    public RunningServiceImpl(RunningRepository runningRepository) {
        this.runningRepository = runningRepository;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public ResponseRunning createRunning(RequestRunning running) {
        RunningEntity runningEntity = mapper.map(running, RunningEntity.class);

        LocalDateTime start = runningEntity.getRunningStartTime();
        LocalDateTime end = runningEntity.getRunningEndTime();

        long totalTime = ChronoUnit.MINUTES.between(start, end);

        runningEntity.setTotalRunningTime(totalTime);

        runningRepository.save(runningEntity);

        return mapper.map(runningEntity, ResponseRunning.class);
    }

    @Override
    public List<ResponseRunning> findTodayRunningById(String userEmail) {
        Specification<RunningEntity> spec = Specification.where((root, query, builder) -> {
            LocalDate date = LocalDate.now();
            LocalDate tomorrow = date.plusDays(1); // Get tomorrow's date
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endOfDay = LocalDateTime.of(tomorrow, LocalTime.MIDNIGHT);

            return builder.and(
                    builder.equal(root.get("userEmail"), userEmail),
                    builder.between(root.get("runningStartTime"), startOfDay, endOfDay)
            );
        });

        List<RunningEntity> runningEntityList =  runningRepository.findAll(spec);

        List<ResponseRunning> result = runningEntityList.stream().map(
                runningEntity -> {
                    ResponseRunning running = mapper.map(runningEntity, ResponseRunning.class);
                    running.setToday(runningEntity.getRunningStartTime().toLocalDate());

                    return running;
                }
        ).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<ResponseRunning> findAllRunningById(String userEmail) {
        Specification<RunningEntity> spec = Specification.where((root, query, builder) -> {
            return builder.equal(root.get("userEmail"), userEmail);
        });

        List<RunningEntity> runningEntityList = runningRepository.findAll(spec);

        List<ResponseRunning> result = runningEntityList.stream().map(
                runningEntity -> {
                    ResponseRunning running = mapper.map(runningEntity, ResponseRunning.class);
                    running.setToday(runningEntity.getRunningStartTime().toLocalDate());


                    return running;
                }
        ).collect(Collectors.toList());

        return result;
    }
}
