package com.example.activityservice.service;

import com.example.activityservice.DAO.DiaryDao;
import com.example.activityservice.DAO.GuestBookDao;
import com.example.activityservice.jpa.DiaryRepository;
import com.example.activityservice.jpa.GuestBookRepository;
import com.example.activityservice.jpa.RunningRepository;
import com.example.activityservice.jpa.SleepRepository;
import com.example.activityservice.vo.Experience;
import com.example.activityservice.vo.diary.RequestDiary;
import com.example.activityservice.vo.diary.ResponseDiary;
import com.example.activityservice.vo.guestBook.RequestGuestBook;
import com.example.activityservice.vo.guestBook.ResponseGuestBook;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
    GuestBookRepository guestBookRepository;
    ModelMapper mapper = new ModelMapper();

    Experience experience = new Experience();


    @Autowired
    public ServiceImpl(DiaryRepository diaryRepository, RunningRepository runningRepository, SleepRepository sleepRepository, GuestBookRepository guestBookRepository) {
        this.diaryRepository = diaryRepository;
        this.runningRepository = runningRepository;
        this.sleepRepository = sleepRepository;
        this.guestBookRepository = guestBookRepository;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);   //이걸 해줘야 Id값이 변경됨
    }

    //문어 레벨업 & 문어 경험치 획득 [추가 예정]

    // 다이어리

    /**다이어리 생성*/
    @Override
    public ResponseDiary createDiary(RequestDiary diary) {
        DiaryDao diaryDao = mapper.map(diary, DiaryDao.class);
        diaryDao.setWriteTime(LocalDateTime.now());

        diaryDao.setDiaryId(null);
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


    // 방명록

    /**방명록 작성*/
    @Override
    public ResponseGuestBook createGuestBook(RequestGuestBook requestGuestBook) {
        GuestBookDao guestBookDao = mapper.map(requestGuestBook, GuestBookDao.class);
        guestBookDao.setWriteTime(LocalDateTime.now());

        guestBookRepository.save(guestBookDao);

        return mapper.map(guestBookDao, ResponseGuestBook.class);
    }

    /**방명록 전체 조회*/
    @Override
    public List<ResponseGuestBook> findAllGuestBookById(long userId) {
        Specification<GuestBookDao> spec = Specification.where((root, query, builder) -> {
            return builder.equal(root.get("userId"), userId);
        });

        List<GuestBookDao> GuestBookList = guestBookRepository.findAll(spec);

        List<ResponseGuestBook> result = GuestBookList.stream().map(
                guestBookDao -> {
                    ResponseGuestBook responseGuestBook = mapper.map(guestBookDao, ResponseGuestBook.class);
                    return responseGuestBook;
                }
        ).collect(Collectors.toList());

        return result;
    }

    /**방명록 업데이트*/
    @Override
    public ResponseGuestBook modifyGuestBookById(RequestGuestBook requestGuestBook, long commentId) {
        GuestBookDao guestBookDao = mapper.map(requestGuestBook, GuestBookDao.class);
        guestBookDao.setCommentId(commentId);
        guestBookDao.setWriteTime(LocalDateTime.now());

        guestBookRepository.save(guestBookDao);

        return mapper.map(guestBookDao, ResponseGuestBook.class);
    }

    /**방명록 삭제*/
    @Override
    public boolean deleteGuestBookById(long commentId) {
        try{
            guestBookRepository.deleteById(commentId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
