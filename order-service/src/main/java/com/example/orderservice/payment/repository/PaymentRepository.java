package com.example.orderservice.payment.repository;

import com.example.orderservice.payment.dao.PaymentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentDao, String> {

}
