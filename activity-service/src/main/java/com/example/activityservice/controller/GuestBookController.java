package com.example.activityservice.controller;

import com.example.activityservice.dto.guestBook.RequestGuestBook;
import com.example.activityservice.dto.guestBook.ResponseGuestBook;
import com.example.activityservice.service.GuestBookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest-book")
public class GuestBookController {

    final GuestBookServiceImpl service;


    /**방명록 작성*/
    @PostMapping("")
    public ResponseEntity<ResponseGuestBook> createGuestBook(@RequestBody RequestGuestBook guestBook){
        ResponseGuestBook responseGuestBook = service.createGuestBook(guestBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseGuestBook);
    }
    /**방명록 전체 조회*/
    @GetMapping("/{userEmail}")
    public ResponseEntity<List<ResponseGuestBook>> findAllGuestBook(@PathVariable String userEmail){
        List<ResponseGuestBook> responseGuestBooks = service.findAllGuestBookById(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBooks);
    }
    /**방명록 업데이트*/
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseGuestBook> modifyGuestBook(@RequestBody RequestGuestBook guestBook, @PathVariable long commentId ){
        ResponseGuestBook responseGuestBook = service.modifyGuestBookById(guestBook,commentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBook);
    }
    /**방명록 삭제*/
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> deleteGuestBook(@PathVariable long commentId){
        Boolean result = service.deleteGuestBookById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**안읽은 댓글 불러오기*/
    @GetMapping("/unread/{userEmail}")
    public ResponseEntity<List<ResponseGuestBook>> findAllUnReadComment(@PathVariable String userEmail){
        List<ResponseGuestBook> result = service.findAllUnReadComment(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**전체 댓글 읽음 처리*/
    @PutMapping("/read-all/{userEmail}")
    public HttpStatus readAllComment(@PathVariable String userEmail){
        service.readAllComment(userEmail);
        return HttpStatus.OK;
    }

    /**댓글 읽음 처리*/
    @PutMapping("/read/{commentId}")
    public HttpStatus readComment(@PathVariable long commentId){
        service.readComment(commentId);
        return HttpStatus.OK;
    }
}
