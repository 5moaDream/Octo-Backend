package com.example.orderservice.order.repository;

import com.example.orderservice.order.entity.PaymentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    List<PaymentEntity> findAllByUserId(Long userId, Pageable pageable);
}
