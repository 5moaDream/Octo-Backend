package com.example.orderservice.order.repository;

import com.example.orderservice.order.entity.RefundEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<RefundEntity, Long> {
    List<RefundEntity> findByPaymentEntityUserId(Long userId, Pageable pageable);
}
