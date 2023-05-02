package com.example.activityservice.service;

import com.example.activityservice.entity.GuestBookEntity;
import com.example.activityservice.repository.GuestBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GuestBookService {

    GuestBookRepository guestBookRepository;

    @Autowired
    public GuestBookService(GuestBookRepository guestBookRepository) {
        this.guestBookRepository = guestBookRepository;
    }

    /**방명록 작성*/
    public HttpStatus createGuestBook(GuestBookEntity guestBook) {
        guestBook.setCreatedTime(new Date());
        guestBook.setRead(false);
        guestBookRepository.save(guestBook);
        return HttpStatus.CREATED;
    }

    /**방명록 전체 조회*/
    public List<GuestBookEntity> findAllGuestBookById(Long userId, Pageable pageable) {
        List<GuestBookEntity> guestBookList = guestBookRepository.findAllByUserId(userId, pageable);
        return guestBookList;
    }

    /**안읽은 방명록 조회*/
    public List<GuestBookEntity> findAllUnReadComment(Long userId, Pageable pageable) {
        List<GuestBookEntity> guestBookEntityList = guestBookRepository.findAllByUserIdAndReadFalse(userId, pageable);
        return guestBookEntityList;
    }

    /**전체 읽음 처리*/
    @Transactional
    public void readAllComment(Long userId) {
        guestBookRepository.updateAllRead(userId);
    }

    /**단일 읽음 처리*/
    @Transactional
    public void readComment(Long guestBookId) {
        guestBookRepository.updateRead(guestBookId);
    }

    /**방명록 수정*/
    public GuestBookEntity modifyGuestBookById(GuestBookEntity guestBookEntity) {
        guestBookEntity.setCreatedTime(new Date());
        return guestBookRepository.save(guestBookEntity);

    }

    /**방명록 삭제*/
    public boolean deleteGuestBookById(Long guestBookId) {
        try{
            guestBookRepository.deleteById(guestBookId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
