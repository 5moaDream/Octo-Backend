package com.example.activityservice.service;

import com.example.activityservice.DAO.DiaryDao;
import com.example.activityservice.jpa.DiaryRepository;
import com.example.activityservice.jpa.RunningRepository;
import com.example.activityservice.jpa.SleepRepository;
import com.example.activityservice.vo.diary.RequestDiary;
import com.example.activityservice.vo.diary.ResponseDiary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements ActivityService{
    DiaryRepository diaryRepository;
    RunningRepository runningRepository;
    SleepRepository sleepRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public ServiceImpl(DiaryRepository diaryRepository, RunningRepository runningRepository, SleepRepository sleepRepository) {
        this.diaryRepository = diaryRepository;
        this.runningRepository = runningRepository;
        this.sleepRepository = sleepRepository;
    }


    // 다이어리

    /**다이어리 생성*/
    @Override
    public ResponseDiary createDiary(RequestDiary diary) {
        DiaryDao diaryDao = mapper.map(diary, DiaryDao.class);
        diaryDao.setWriteTime(LocalDateTime.now());

        diaryRepository.save(diaryDao);

        return mapper.map(diaryDao, ResponseDiary.class);
    }

    /**모든 다이어리 조회*/
    @Override
    public List<ResponseDiary> findAllDiaryById(long userId) {
        Specification<DiaryDao> spec = Specification.where((root, query, builder) -> {
            return builder.equal(root.get("userId"), userId);
        });

        List<DiaryDao> diaryList = diaryRepository.findAll(spec);

        List<ResponseDiary> result = diaryList.stream().map(
                diaryDao -> {
                    ResponseDiary responseDiary = mapper.map(diaryDao, ResponseDiary.class);
                    return responseDiary;
                }
        ).collect(Collectors.toList());

        return result;
    }

    /**다이어리 수정*/
    @Override
    public ResponseDiary updateDiary(RequestDiary diary, long diaryId) {
        DiaryDao diaryDao = mapper.map(diary, DiaryDao.class);
        diaryDao.setDiaryId(diaryId);

        diaryRepository.save(diaryDao);

        return mapper.map(diaryDao, ResponseDiary.class);
    }

    /**다이어리 삭제*/
    @Override
    public boolean deleteDiary(long diaryId) {
        return false;
    }

}
