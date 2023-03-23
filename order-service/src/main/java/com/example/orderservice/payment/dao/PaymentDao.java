package com.example.orderservice.payment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDao {
    @Id
    private String merchant_uid;
    private int userId;
    private int itemId;
    private int amount;

    private LocalDateTime paymentTime;
}
