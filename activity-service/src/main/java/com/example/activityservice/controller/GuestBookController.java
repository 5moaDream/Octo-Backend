package com.example.activityservice.controller;

import com.example.activityservice.entity.GuestBookEntity;
import com.example.activityservice.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest-book")
public class GuestBookController {

    final GuestBookService service;


    /**방명록 작성*/
    @PostMapping("")
    public ResponseEntity<GuestBookEntity> createGuestBook(@RequestBody GuestBookEntity guestBook){
        GuestBookEntity responseGuestBook = service.createGuestBook(guestBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseGuestBook);
    }

    /**방명록 전체 조회*/
    @GetMapping("/{userId}")
    public ResponseEntity<List<GuestBookEntity>> findAllGuestBook(@PathVariable Long userId,
                                  @PageableDefault(page = 0, size = 10, sort = "guestBookId") Pageable pageable){

        List<GuestBookEntity> responseGuestBooks = service.findAllGuestBookById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBooks);
    }

    /**방명록 업데이트*/
    @PutMapping("/{guestBookId}")
    public ResponseEntity<GuestBookEntity> modifyGuestBook(@RequestBody GuestBookEntity guestBook ){
        GuestBookEntity responseGuestBook = service.modifyGuestBookById(guestBook);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBook);
    }
    /**방명록 삭제*/
    @DeleteMapping("/{guestBookId}")
    public ResponseEntity<Boolean> deleteGuestBook(@PathVariable Long guestBookId){
        Boolean result = service.deleteGuestBookById(guestBookId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**안읽은 댓글 불러오기*/
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<GuestBookEntity>> findAllUnReadComment(@PathVariable Long userId){
        List<GuestBookEntity> result = service.findAllUnReadComment(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**전체 댓글 읽음 처리*/
    @PutMapping("/read-all/{userId}")
    public HttpStatus readAllComment(@PathVariable Long userId){
        service.readAllComment(userId);
        return HttpStatus.OK;
    }

    /**댓글 읽음 처리*/
    @PutMapping("/read/{guestBookId}")
    public HttpStatus readComment(@PathVariable Long guestBookId){
        service.readComment(guestBookId);
        return HttpStatus.OK;
    }
}
