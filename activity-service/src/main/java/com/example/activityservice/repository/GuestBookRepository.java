package com.example.activityservice.repository;

import com.example.activityservice.dao.GuestBookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBookDao, Long>, JpaSpecificationExecutor<GuestBookDao> {
    // 안읽은 댓글 조회
    List<GuestBookDao> findAllByUserEmailAndReadFalse(String userEmail);
    // 전체 댓글 읽음 처리
    @Modifying
    @Query("UPDATE GuestBookDao g SET g.read = true WHERE g.userEmail = :userEmail")
    int updateAllRead(@Param("userEmail") String userEmail);

    //단일 댓글 읽음 처리
    @Modifying
    @Query("UPDATE GuestBookDao g SET g.read = true WHERE g.commentId = :commentId")
    int updateRead(@Param("commentId") long commentId);
}
