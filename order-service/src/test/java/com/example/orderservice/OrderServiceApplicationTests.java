package com.example.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


//    @Test
//    @DisplayName("사전 검증")
//    void prepareVerificationTest() throws Exception{
//
//        //given
//        int amount = 9900;
//
//        //when
//        mockMvc.perform(post("/payment/prepare/{amount}",amount)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                //then
//                .andExpect(status().isOk())
//                // rest docs 문서화
//                .andDo(document("prepareVerification",
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
//                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지").optional(),
//                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("응답 데이터"),
//                                fieldWithPath("response.merchant_uid").type(JsonFieldType.STRING).description("결제 아이디"),
//                                fieldWithPath("response.amount").type(JsonFieldType.NUMBER).description("금액"),
//                                fieldWithPath("response.token").type(JsonFieldType.STRING).description("토큰")
//                        )
//                ));
//    }

//    @Test
//    @DisplayName("사후 검증")
//    void completeVerificationTest() throws Exception{
//        //given
//        RequestPayment request = new RequestPayment(
//                "imp_442601173622",
//                "imp464605542023-04-05T16:43:08.675766",
//                "5b5825a581c67c7c345811f74b2b27be216b91a0",
//                "ABC@Email.com",
//                1,
//                9900
//                );
//
//        //when
//        mockMvc.perform(post("/payment/completion")
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//        //then
//                .andExpect(status().isOk())
//                .andDo(document("completionVerification",
//                        requestFields(
//                                fieldWithPath("imp_uid").type(JsonFieldType.STRING).description("포트원 고유번호"),
//                                fieldWithPath("merchant_uid").type(JsonFieldType.STRING).description("결제 고유번호"),
//                                fieldWithPath("token").type(JsonFieldType.STRING).description("엑세스 토큰"),
//                                fieldWithPath("userEmail").type(JsonFieldType.STRING).description("유저 이메일"),
//                                fieldWithPath("itemId").type(JsonFieldType.NUMBER).description("아이템 아이디"),
//                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("총액")
//                        ),
//                        responseFields(
//                                fieldWithPath("status").type(JsonFieldType.STRING).description("결제 상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지").optional()
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("환불")
//    void refund() throws Exception{
//        //given
//        RequestRefund request = new RequestRefund(
//                "ABC@Email.com",
//                "imp464605542023-04-05T16:43:08.675766",
//                9900,
//                "단순 변심"
//        );
//        //when
//        mockMvc.perform(post("/payment/cancel")
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                //then
//                .andExpect(status().isBadRequest())
//                .andDo(document("refund",
//                        requestFields(
//                                fieldWithPath("userEmail").type(JsonFieldType.STRING).description("유저 이메일"),
//                                fieldWithPath("merchant_uid").type(JsonFieldType.STRING).description("결제 고유번호"),
//                                fieldWithPath("cancel_request_amount").type(JsonFieldType.NUMBER).description("환불 금액"),
//                                fieldWithPath("reason").type(JsonFieldType.STRING).description("환불 사유")
//                        ),
//                        responseFields(
//                                fieldWithPath("message").type(JsonFieldType.STRING).description("환불 결과 메세지")
//                        )
//                ));
//    }
}
