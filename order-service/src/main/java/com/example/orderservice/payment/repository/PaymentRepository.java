package com.example.orderservice.payment.repository;

import com.example.orderservice.payment.dao.PaymentDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDao, String> {
}
