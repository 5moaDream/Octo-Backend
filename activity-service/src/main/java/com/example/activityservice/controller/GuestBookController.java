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
public class GuestBookController {

    final GuestBookService guestBookService;


    /**방명록 작성*/
    @PostMapping("/guest-book")
    public HttpStatus createGuestBook(@RequestParam("id") Long userId, @RequestBody GuestBookEntity guestBook){
        guestBook.setGuestId(userId);
        return guestBookService.createGuestBook(guestBook);
    }

    /**방명록 전체 조회*/
    @GetMapping("/guest-book")
    public ResponseEntity<List<GuestBookEntity>> findAllGuestBook(@RequestParam("id") Long userId,
                                                                  @PageableDefault(page = 0, size = 30, sort = "createdTime") Pageable pageable){

        List<GuestBookEntity> responseGuestBooks = guestBookService.findAllGuestBookById(userId, pageable);
        guestBookService.readAllComment(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBooks);
    }

    /**안읽은 방명록 조회*/
    @GetMapping("/guest-book/unread")
    public ResponseEntity<List<GuestBookEntity>> findAllUnReadComment(@RequestParam("id") Long userId,
                                                                      @PageableDefault(page = 0, size = 30, sort = "createdTime") Pageable pageable){
        List<GuestBookEntity> result = guestBookService.findAllUnReadComment(userId, pageable);
        guestBookService.readAllComment(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    /**방명록 삭제*/
//    @DeleteMapping("/guest-book/{guestBookId}")
//    public ResponseEntity<Void> deleteGuestBook(@PathVariable Long guestBookId){
//        guestBookService.deleteGuestBookById(guestBookId);
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
}
