package com.example.orderservice.order.Controller;

import com.example.orderservice.order.entity.RefundEntity;
import com.example.orderservice.order.repository.PaymentRepository;
import com.example.orderservice.order.entity.PaymentEntity;
import com.example.orderservice.order.repository.RefundRepository;
import com.example.orderservice.order.service.PaymentService;
import com.example.orderservice.order.vo.complete.RequestPayment;
import com.example.orderservice.order.vo.complete.ResponseCompleteVerification;
import com.example.orderservice.order.vo.complete.ResponsePayment;
import com.example.orderservice.order.vo.prepare.ResponsePrepareVerification;
import com.example.orderservice.order.vo.refund.RequestRefund;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {
    final PaymentRepository paymentRepository;
    final PaymentService paymentService;
    final RefundRepository refundRepository;


    @GetMapping("/health_check")
    public String healthCheck() {
        return "health Check Success!";
    }

    //사전 검증
    @CrossOrigin
    @PostMapping("/prepare/{amount}")
    public ResponsePrepareVerification prepareVerification(@PathVariable int amount){
        return paymentService.prepareVerification(amount);
    }

    //결제 요청 + 사후 검증
    @CrossOrigin
    @PostMapping("/completion")
    public ResponseEntity<ResponsePayment> completePayment(@RequestParam("id") Long userId, @RequestBody RequestPayment requestPayment) {

        try {
            requestPayment.setUserId(userId);
            String imp_uid = requestPayment.getImp_uid();           //포트원 거래번호
            String token = requestPayment.getToken();               //엑세스 토큰

            ModelMapper mapper = new ModelMapper();
            PaymentEntity paymentEntity = mapper.map(requestPayment, PaymentEntity.class);
            paymentEntity.setPaymentTime(new Date());         //결제요청 정보 DAO로 변환


            int amountToBePaid  = requestPayment.getAmount();       // 결제요청 받은 금액

            ResponseCompleteVerification paymentData = paymentService.completeVerification(imp_uid, token);    //결제 정보 조회
            int amount = paymentData.getResponse().getAmount();     // 사전 등록해둔 금액
            String status = paymentData.getResponse().getStatus();  // 결제 진행 상태

            if(amount == amountToBePaid){
                paymentRepository.save(paymentEntity);     //결제 정보 디비 저장

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

    //환불
    @CrossOrigin
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPayment(@RequestBody RequestRefund requestRefund) {
        try {
            /* Check payment information */
            String merchantUid = requestRefund.getMerchant_uid(); // order number received from the client
            Optional<PaymentEntity> paymentOptional = paymentRepository.findById(merchantUid);

            if (!paymentOptional.isPresent()) {
                return ResponseEntity.badRequest().body("Payment not found");
            }

            PaymentEntity payment = paymentOptional.get();

            /* Request a payment refund through the Port One REST API */
            String impUid = payment.getImp_uid();
            int amount = payment.getAmount();
            int cancelAmount = requestRefund.getCancel_request_amount();

            /* Calculate the refundable amount (= payment amount - total amount refunded) */
            int cancelableAmount = amount - cancelAmount;
            if (cancelableAmount <= 0) { // If the full amount has already been refunded
                return ResponseEntity.badRequest().body("This order has already been fully refunded.");
            }

            //환불 요청하기
            paymentService.refundRequest(impUid, cancelableAmount, requestRefund);

            /* Synchronize refund result */
            RefundEntity refund = new RefundEntity(payment, new Date());
            //환불 정보 디비에 저장
            refundRepository.save(refund);

            return ResponseEntity.ok("Refund requested successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //결제내역 조회
    @GetMapping("/payment")
    public ResponseEntity<List<PaymentEntity>> findPaymentList(@RequestParam("userId") Long userId,
                                                               @PageableDefault(page = 0, size = 10, sort = "paymentTime", direction = Sort.Direction.DESC) Pageable pageable){
        List<PaymentEntity> paymentEntityList = paymentRepository.findAllByUserId(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(paymentEntityList);
    }

    //환불내역 조회
    @GetMapping("/refund")
    public ResponseEntity<List<RefundEntity>> findRefundList(@RequestParam("userId") Long userId,
                                                             @PageableDefault(page = 0, size = 10, sort = "refundTime", direction = Sort.Direction.DESC) Pageable pageable){
        List<RefundEntity> refundEntityList = refundRepository.findByPaymentEntityUserId(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(refundEntityList);
    }
}
