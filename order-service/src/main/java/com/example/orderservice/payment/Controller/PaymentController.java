package com.example.orderservice.payment.Controller;

import com.example.orderservice.payment.repository.PaymentRepository;
import com.example.orderservice.payment.dao.PaymentDao;
import com.example.orderservice.payment.service.PaymentService;
import com.example.orderservice.payment.vo.complete.RequestPayment;
import com.example.orderservice.payment.vo.complete.ResponseCompleteVerification;
import com.example.orderservice.payment.vo.complete.ResponsePayment;
import com.example.orderservice.payment.vo.prepare.ResponsePrepareVerification;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
//@RequestMapping("/payment")
public class PaymentController {
    PaymentRepository paymentRepository;
    PaymentService service;

    public PaymentController(PaymentRepository paymentRepository, PaymentService service) {
        this.paymentRepository = paymentRepository;
        this.service = service;
    }



    //사전 검증
    @CrossOrigin
    @PostMapping("/prepare/{amount}")
    public ResponsePrepareVerification responsePrepare(@PathVariable double amount){
        return service.prepareVerification(amount);
    }

    //결제 요청 + 사후 검증
    @CrossOrigin
    @PostMapping("/completion")
    public ResponseEntity<ResponsePayment> completePayment(@RequestBody RequestPayment requestPayment) {
        try {
            String imp_uid = requestPayment.getImp_uid();           //포트원 거래번호
            String token = requestPayment.getToken();               //엑세스 토큰

            ModelMapper mapper = new ModelMapper();
            PaymentDao paymentDao = mapper.map(requestPayment, PaymentDao.class);
            paymentDao.setPaymentTime(LocalDateTime.now());         //결제요청 정보 DAO로 변환


            int amountToBePaid  = requestPayment.getAmount();       // 결제요청 받은 금액

            ResponseCompleteVerification paymentData = service.completeVerification(imp_uid, token);    //결제 정보 조회
            int amount = paymentData.getResponse().getAmount();     // 사전 등록해둔 금액
            String status = paymentData.getResponse().getStatus();  // 결제 진행 상태

            if(amount == amountToBePaid){
                paymentRepository.save(paymentDao);     //결제 정보 디비 저장

                switch (status) {
                    //case "ready": // 가상계좌 계설
                        // Store virtual account issuance information in DB
                        ///...

                        // 가상계좌 생성 안내 문자 발송
                        // ...

                    case "paid": // Payment completed
                        return ResponseEntity.ok().body(new ResponsePayment("success", "General payment successful"));
                    default:
                        throw new RuntimeException("Invalid payment status");
                }
            } else { // Payment amount mismatch. Counterfeit/falsified payment
                throw new RuntimeException("Attempted forgery");
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponsePayment("fail", e.getMessage()));
        }
    }
}
