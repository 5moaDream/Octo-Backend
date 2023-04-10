package com.example.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("사전 검증")
    void prepareTest() throws Exception{

        //given
        int amount = 9900;

        //when
        mockMvc.perform(post("/payment/prepare/{amount}",amount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                // rest docs 문서화
                .andDo(document("prepareVerification",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지").optional(),
                                fieldWithPath("response").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("response.merchant_uid").type(JsonFieldType.STRING).description("결제 아이디"),
                                fieldWithPath("response.amount").type(JsonFieldType.NUMBER).description("금액"),
                                fieldWithPath("response.token").type(JsonFieldType.STRING).description("토큰")
                        )
                ));
    }
}
