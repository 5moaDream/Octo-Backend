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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;
    private int userId;
    private int itemId;
    private int price;

    private LocalDateTime paymentTime;
}
