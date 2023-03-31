package com.example.activityservice.service;

import com.example.activityservice.dao.DiaryDao;
import com.example.activityservice.dto.diary.RequestDiary;
import com.example.activityservice.dto.diary.ResponseDiary;
import com.example.activityservice.repository.DiaryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryServiceImpl implements DiaryService{
    DiaryRepository diaryRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

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
    public List<ResponseDiary> findAllDiaryById(String userEmail) {
        Specification<DiaryDao> spec = Specification.where((root, query, builder) -> {
            return builder.equal(root.get("userEmail"), userEmail);
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
        diaryDao.setWriteTime(LocalDateTime.now());

        diaryRepository.save(diaryDao);

        return mapper.map(diaryDao, ResponseDiary.class);
    }

    /**다이어리 삭제*/
    @Override
    public boolean deleteDiary(long diaryId) {
        try{
            diaryRepository.deleteById(diaryId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
