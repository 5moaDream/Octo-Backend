package com.example.activityservice.service;

import com.example.activityservice.dao.GuestBookDao;
import com.example.activityservice.dto.guestBook.RequestGuestBook;
import com.example.activityservice.dto.guestBook.ResponseGuestBook;
import com.example.activityservice.repository.GuestBookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestBookServiceImpl implements GuestBookService{

    GuestBookRepository guestBookRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public GuestBookServiceImpl(GuestBookRepository guestBookRepository) {
        this.guestBookRepository = guestBookRepository;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**방명록 작성*/
    @Override
    public ResponseGuestBook createGuestBook(RequestGuestBook requestGuestBook) {
        GuestBookDao guestBookDao = mapper.map(requestGuestBook, GuestBookDao.class);
        guestBookDao.setWriteTime(LocalDateTime.now());
        guestBookDao.setRead(false);

        guestBookRepository.save(guestBookDao);

        return mapper.map(guestBookDao, ResponseGuestBook.class);
    }

    /**방명록 전체 조회*/
    @Override
    public List<ResponseGuestBook> findAllGuestBookById(String userEmail) {
        Specification<GuestBookDao> spec = Specification.where((root, query, builder) -> {
            return builder.equal(root.get("userEmail"), userEmail);
        });

        List<GuestBookDao> guestBookList = guestBookRepository.findAll(spec);

        List<ResponseGuestBook> result = guestBookList.stream().map(
                guestBookDao -> {
                    ResponseGuestBook responseGuestBook = mapper.map(guestBookDao, ResponseGuestBook.class);
                    return responseGuestBook;
                }
        ).collect(Collectors.toList());

        return result;
    }

    /**안읽은 방명록 조회*/
    @Override
    public List<ResponseGuestBook> findAllUnReadComment(String userEmail) {
        List<GuestBookDao> guestBookDaoList = guestBookRepository.findAllByUserEmailAndReadFalse(userEmail);

        List<ResponseGuestBook> result = guestBookDaoList.stream().map(
                guestBookDao -> {
                    ResponseGuestBook responseGuestBook = mapper.map(guestBookDao, ResponseGuestBook.class);
                    return responseGuestBook;
                }
        ).collect(Collectors.toList());

        return result;
    }

    /**전체 읽음 처리*/
    @Transactional
    @Override
    public void readAllComment(String userEmail) {
        guestBookRepository.updateAllRead(userEmail);
    }

    /**단일 읽음 처리*/
    @Transactional
    @Override
    public void readComment(long commentId) {
        guestBookRepository.updateRead(commentId);
    }

    /**방명록 수정*/
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
