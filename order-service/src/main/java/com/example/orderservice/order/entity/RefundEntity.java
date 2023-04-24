package com.example.orderservice.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REFUND_TB")
@Data
@NoArgsConstructor
public class RefundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFUND_PK")
    @OneToOne
    @PrimaryKeyJoinColumn
    private PaymentEntity paymentEntity;
//    @Column(name = "PAYMENT_FK")
//    private String payment_fk;
    @Column(name = "REFUND_TIME")
    private Date refundTime;

    public RefundEntity(PaymentEntity payment, Date refundTime){
        this.paymentEntity = payment;
        this.refundTime = refundTime;
    }
}
