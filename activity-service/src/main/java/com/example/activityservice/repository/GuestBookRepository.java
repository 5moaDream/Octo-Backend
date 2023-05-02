package com.example.activityservice.repository;

import com.example.activityservice.entity.GuestBookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBookEntity, Long>, JpaSpecificationExecutor<GuestBookEntity> {
    // 안읽은 댓글 조회
    List<GuestBookEntity> findAllByUserIdAndReadFalse(Long userId,  Pageable pageable);
    List<GuestBookEntity> findAllByUserId(Long userId, Pageable pageable);

    // 전체 댓글 읽음 처리
    @Modifying
    @Query("UPDATE GuestBookEntity g SET g.read = true WHERE g.userId = :userId")
    int updateAllRead(@Param("userId") Long userId);

    //단일 댓글 읽음 처리
    @Modifying
    @Query("UPDATE GuestBookEntity g SET g.read = true WHERE g.guestBookId = :guestBookId")
    int updateRead(@Param("guestBookId") Long guestBookId);


}
