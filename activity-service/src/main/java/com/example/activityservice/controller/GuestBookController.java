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
@RequestMapping("/activity-service")
public class GuestBookController {

    final GuestBookService guestBookService;


    /**방명록 작성*/
    @PostMapping("/guest-book")
    public HttpStatus createGuestBook(@RequestParam("id") Long userId, @RequestBody GuestBookEntity guestBook){
        guestBook.setUserId(userId);
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

//    @ExceptionHandler(NullPointerException.class) 컨트롤러에서 발생하는 모든 예외를 처리 = 각 메소드에 try 없어도 됨


//    /**방명록 업데이트*/
//    @PutMapping("/{guestBookId}")
//    public ResponseEntity<GuestBookEntity> modifyGuestBook(@RequestBody GuestBookEntity guestBook ){
//        GuestBookEntity responseGuestBook = guestBookService.modifyGuestBookById(guestBook);
//        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBook);
//    }
//    /**방명록 삭제*/
//    @DeleteMapping("/{guestBookId}")
//    public ResponseEntity<Boolean> deleteGuestBook(@PathVariable Long guestBookId){
//        Boolean result = guestBookService.deleteGuestBookById(guestBookId);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }


//    /**댓글 읽음 처리*/
//    @PutMapping("/read/{guestBookId}")
//    public HttpStatus readComment(@PathVariable Long guestBookId){
//        guestBookService.readComment(guestBookId);
//        return HttpStatus.OK;
//    }
}
