package com.example.orderservice.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "REFUND_TB")
@Data
@NoArgsConstructor
public class RefundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFUND_PK")
    private Long refundId;
    @OneToOne
    @JoinColumn(name = "MERCHANT_UID")
    private PaymentEntity paymentEntity;
    @Column(name = "REFUND_TIME")
    private Date refundTime;

    public RefundEntity(PaymentEntity payment, Date refundTime){
        this.paymentEntity = payment;
        this.refundTime = refundTime;
    }
}
