package com.example.orderservice.payment;

import com.example.orderservice.payment.dao.PaymentDao;
import com.example.orderservice.payment.vo.RequestPayment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    final PaymentRepository paymentRepository;

    @PostMapping("/payment")
    public void payment(@RequestBody RequestPayment requestPayment){
        System.out.println(requestPayment);

        ModelMapper mapper = new ModelMapper();
        PaymentDao paymentDao = mapper.map(requestPayment, PaymentDao.class);
        paymentDao.setPaymentTime(LocalDateTime.now());

        paymentRepository.save(paymentDao);

        System.out.println(paymentDao);
    }
}
