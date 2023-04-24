package com.example.orderservice.order.service;
import com.example.orderservice.order.entity.PaymentEntity;
import com.example.orderservice.order.repository.PaymentRepository;
import com.example.orderservice.order.vo.complete.ResponseCompleteVerification;
import com.example.orderservice.order.vo.prepare.ResponsePrepareVerification;
import com.example.orderservice.order.vo.refund.RequestRefund;
import com.example.orderservice.order.vo.token.ResponseTokenData;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    WebClient webClient = WebClient.create("https://api.iamport.kr");

    private final String imp_key = "7583047561780781"; // REST API key

    private final String imp_secret = "fzx6EJZTGGN93envPl2ALSWORh3pCAclTn0RpaElEHMPkMof3UbjggIICBUU75NoPPOPpYBib3YRzIWy"; // REST API secret
    private final String imp_code = "imp46460554";  //가맹점 식별코드

    /**엑세스 토큰 발급*/
    public String getAccessToken() {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("imp_key", imp_key); // merchant order number
        requestData.put("imp_secret", imp_secret); // scheduled payment amount

        String token = webClient.post()
                .uri("/users/getToken")
                .body(BodyInserters.fromValue(requestData))
                .retrieve()
                .bodyToMono(ResponseTokenData.class)
                .block()
                .getResponse()
                .getAccess_token();

        return token;
    }

    /** 사전 검증(결제 정보 포트원에 등록) */
    public ResponsePrepareVerification prepareVerification(int amount) {
        String token = getAccessToken();

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("merchant_uid", imp_code + LocalDateTime.now()); // merchant order number
        requestData.put("amount", amount); // scheduled payment amount

        ResponsePrepareVerification result = webClient.post()
                .uri("/payments/prepare")
                .header("Authorization", "Bearer " + token)
                .body(BodyInserters.fromValue(requestData))
                .retrieve()
                .bodyToMono(ResponsePrepareVerification.class)
                .block();

        result.getResponse().setToken(token);

        return result;
    }

    /**Port One RestAPI로 환불 요청*/
    public void refundRequest(String impUid, int cancelableAmount, RequestRefund requestRefund){
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("reason", requestRefund.getReason());
        requestData.put("imp_uid", impUid);
        requestData.put("amount", requestRefund.getCancel_request_amount());
        requestData.put("checksum", cancelableAmount);

        webClient
                .post()
                .uri("/payments/cancel")
                .header("Authorization", "Bearer " + getAccessToken())
                .body(BodyInserters.fromValue(requestData))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    /**사후 검증(결제내역 단건 조회)*/
    public ResponseCompleteVerification completeVerification(String imp_uid, String token) {
        String url = "/payments/" + imp_uid;

        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ResponseCompleteVerification.class)
                .block();
    }

}
