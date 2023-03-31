package com.example.activityservice.service;

import com.example.activityservice.dto.guestBook.RequestGuestBook;
import com.example.activityservice.dto.guestBook.ResponseGuestBook;

import java.util.List;

public interface GuestBookService {
    //방명록 서비스
    ResponseGuestBook createGuestBook(RequestGuestBook requestGuestBook);
    List<ResponseGuestBook> findAllGuestBookById(String userId);
    ResponseGuestBook modifyGuestBookById(RequestGuestBook requestGuestBook, long commentId);
    boolean deleteGuestBookById(long commentId);

    // 안읽은 댓글 조회
    List<ResponseGuestBook> findAllUnReadComment(String userEmail);
    // 전체 댓글 읽음 처리
    void readAllComment(String userEmail);
    // 단일 댓글 읽음 처리
    void readComment(long commentId);
}
