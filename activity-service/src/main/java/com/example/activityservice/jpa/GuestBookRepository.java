package com.example.activityservice.jpa;

import com.example.activityservice.DAO.GuestBookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuestBookRepository extends JpaRepository<GuestBookDao, Long>, JpaSpecificationExecutor<GuestBookDao> {
}
