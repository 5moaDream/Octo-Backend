package com.example.activityservice.service;

import com.example.activityservice.entity.SleepEntity;
import com.example.activityservice.repository.SleepRepository;
import com.example.activityservice.dto.sleep.RequestSleep;
import com.example.activityservice.dto.sleep.ResponseSleep;
import com.example.activityservice.dto.sleep.ResponseSleepList;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SleepServiceImpl implements SleepService {
    SleepRepository sleepRepository;
    ModelMapper mapper = new ModelMapper();




    @Autowired
    public SleepServiceImpl(SleepRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);   //이걸 해줘야 Id값이 변경됨
    }

    @Override
    public ResponseSleep createSleep(RequestSleep sleep) {
        SleepEntity sleepEntity = mapper.map(sleep, SleepEntity.class);

        sleepEntity.setTotalSleepTime(ChronoUnit.MINUTES.between(sleepEntity.getSleptTime(), sleepEntity.getWakeUpTime()));

        if(sleepEntity.getTotalSleepTime() < 60)
            return null;

        sleepRepository.save(sleepEntity);

        return mapper.map(sleepEntity, ResponseSleep.class);
    }

    @Override
    public ResponseSleepList findAllSleepById(String userEmail) {
        Specification<SleepEntity> spec = Specification.where((root, query, builder) -> builder.equal(root.get("userEmail"), userEmail));

        List<SleepEntity> sleepEntities = sleepRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "sleptTime"));

        List<ResponseSleep> responseSleeps = sleepEntities.stream().map(
                sleepEntity -> {
                    ResponseSleep responseSleep = mapper.map(sleepEntity, ResponseSleep.class);
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
}
