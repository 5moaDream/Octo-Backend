package com.example.activityservice.service;

import com.example.activityservice.dao.SleepDao;
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

import java.time.LocalDateTime;
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
        SleepDao sleepDao = mapper.map(sleep, SleepDao.class);

        sleepDao.setTotalSleepTime(ChronoUnit.MINUTES.between(sleepDao.getSleptTime(), sleepDao.getWakeUpTime()));

        if(sleepDao.getTotalSleepTime() < 60)
            return null;

        sleepRepository.save(sleepDao);

        return mapper.map(sleepDao, ResponseSleep.class);
    }

    @Override
    public ResponseSleepList findAllSleepById(String userEmail) {
        Specification<SleepDao> spec = Specification.where((root, query, builder) -> builder.equal(root.get("userEmail"), userEmail));

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
}
