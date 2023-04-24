package com.example.orderservice.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PAYMENT_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @Column(name = "MERCHANT_UID")
    private String merchant_uid;    //결제 고유번호

    @Column(name = "IMP_UID")
    private String imp_uid;         //포트원 결제번호
    @Column(name = "USER_FK")
    private Long userId;
    @Column(name = "ITEM_FK")
    private int itemId;
    @Column(name = "AMOUNT")
    private int amount;
    @Column(name = "PAYMENT_TIME")
    private Date paymentTime;
}