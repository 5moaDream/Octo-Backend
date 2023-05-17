package com.example.userservice;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
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
import org.springframework.transaction.annotation.Transactional;


import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("카카오 로그인")
    void 카카오_로그인() throws Exception {
        //given
        String kakaoToken = "Gkv5ltL0dkL1mhw-NPFXRju5mcimFlDE6bQtzcoNCisNHgAAAYgoebFG";

        //when
        mockMvc.perform(get("/unauthorization/kakao-login")
                        .header("Authorization", "Bearer " + kakaoToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated())
                .andDo(document("카카오 로그인 서비스",
                        ResourceSnippetParameters.builder()
                                .tag("카카오 로그인")
                                .summary("")
                                .description("카카오 토큰으로 서버 엑세스 토큰, 리프레시 토큰 발급")
                                .responseSchema(Schema.schema("Response.Tokens")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        responseFields(
                                fieldWithPath("access_token").type(JsonFieldType.STRING).description("서버 엑세스 토큰"),
                                fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("서버 리프레쉬 토큰")
                        )
                ));

    }

//    @Test
//    @DisplayName("친구 정보 불러오기")
//    void 친구_정보_불러오기() throws Exception {
//        //given
//        String kakaoToken = "Gkv5ltL0dkL1mhw-NPFXRju5mcimFlDE6bQtzcoNCisNHgAAAYgoebFG";
//        //when
//        mockMvc.perform(get("/kakao-friends/{kakaoToken}?offset=0",kakaoToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer AccessToken"))
//                //then
//                .andExpect(status().isNotFound())
//                .andDo(document("친구 목록 조회",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("kakaoToken").description("카카오 엑세스 토큰")
//                        ),
//                        responseFields(
//                                fieldWithPath("friendList[].userId").type(JsonFieldType.STRING).description("유저 아이디"),
//                                fieldWithPath("friendList[].nickName").type(JsonFieldType.STRING).description("별명"),
//                                fieldWithPath("friendList[].characterImage").type(JsonFieldType.STRING).description("캐릭터 이미지")
//                        )
//                ));
//    }

    @Test
    @DisplayName("토큰 재발급")
    @Transactional
    void 토큰_재발급() throws Exception {
        String fresh_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNzEzNTgyNDgyIiwiZXhwIjoxNjg2ODk4NDM3fQ.tvIcC2tu2vRz8c0RhHzPrHZ7e8alJsNNW_l0aLA2IiZjEhbs1xgrx_7S8ylDdCo9mG0FZuZrdYpbCghoOY9Z2g";
        //when
        mockMvc.perform(get("/unauthorization/refresh")
                        .header("Authorization", "Bearer " + fresh_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().is2xxSuccessful())
                .andDo(document("서버 토큰 재발급",
                        ResourceSnippetParameters.builder()
                                .tag("서버 토큰 재발급")
                                .summary("")
                                .description("refresh 토큰을 사용해서 access 토큰 재발급"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer RefreshToken")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        )
                ));
    }



    @Test
    @DisplayName("유저 정보 조회")
    void 유저정보_조회() throws Exception {
        //given
        Long userId = 2713582482L;

        //when
        mockMvc.perform(get("/user?id={userId}", userId, requestParameters(
                        parameterWithName("id").description("유저 아이디")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("유저 정보 조회",
                        ResourceSnippetParameters.builder()
                                .tag("유저 정보 조회")
                                .summary("")
                                .description("유저 아이디로 유저 정보 조회")
                                .responseSchema(Schema.schema("Response.User")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("characterName").type(JsonFieldType.STRING).description("캐릭터 이름").optional(),
                                fieldWithPath("characterUrl").type(JsonFieldType.STRING).description("캐릭터 이미지"),
                                fieldWithPath("experienceValue").type(JsonFieldType.NUMBER).description("경험치"),
                                fieldWithPath("stateMsg").type(JsonFieldType.STRING).description("상태 메세지").optional(),
                                fieldWithPath("thumbnailImageUrl").type(JsonFieldType.STRING).description("썸네일 이미지").optional(),
                                fieldWithPath("sleepTime").type(JsonFieldType.NUMBER).description("목표 수면시간").optional(),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("목표 러닝 거리").optional(),
                                fieldWithPath("dday").type(JsonFieldType.STRING).description("목표 기간").optional()
                        )
                ));
    }

    @Test
    @DisplayName("목표 설정")
    @Transactional
    void 목표_설정() throws Exception {
        //given
        Long userId = 2713582482L;
        int sleepTime = 480;
        float distance = 1.5F;

        //when
        mockMvc.perform(put("/target/sleep/{sleepTime}/distance/{distance}?id={userId}", sleepTime, distance, userId
                , requestParameters(
                        parameterWithName("id").description("유저 아이디")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("유저 정보 업데이트",
                        ResourceSnippetParameters.builder()
                                .tag("유저 정보 업데이트")
                                .summary("")
                                .description("유저 정보 업데이트")
                                .requestSchema(Schema.schema("Request.target")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("sleepTime").description("목표 수면시간"),
                                parameterWithName("distance").description("목표 러닝 거리")
                        )));
        //then
    }

    @Test
    @DisplayName("상태 메세지 업데이트")
    @Transactional
    void 상태메세지_변경() throws Exception {
        //given
        Long userId = 2713582482L;
        String stateMSG = "안녕하세요~";

        //when
        mockMvc.perform(put("/msg/{stateMSG}?id={userId}", stateMSG, userId
                        , requestParameters(
                                parameterWithName("id").description("유저 아이디")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("유저 정보 업데이트",
                        ResourceSnippetParameters.builder()
                                .tag("유저 정보 업데이트")
                                .summary("")
                                .description("유저 정보 업데이트")
                                .requestSchema(Schema.schema("Request.stateMSG")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("stateMSG").description("상태 메세지")
                        )));
        //then
    }
    @Test
    @DisplayName("캐릭터 이름 변경")
    @Transactional
    void 캐릭터_이름_변경() throws Exception {
        //given
        Long userId = 2713582482L;
        String characterName = "옥토퍼스";

        //when
        mockMvc.perform(put("/name/{characterName}?id={userId}", characterName, userId
                        , requestParameters(
                                parameterWithName("id").description("유저 아이디")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("캐릭터 이름 변경",
                        ResourceSnippetParameters.builder()
                                .tag("캐릭터 이름 변경")
                                .summary("")
                                .description("캐릭터 이름 변경")
                                .requestSchema(Schema.schema("Request.CharacterName")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("characterName").description("캐릭터 이름 변경")
                        )));
        //then
    }

    @Test
    @DisplayName("컬렉션 조회")
    void 컬렉션_조회() throws Exception {
        //given
        Long userId = 2713582482L;

        //when
        mockMvc.perform(get("/collection?id={userId}", userId, requestParameters(
                        parameterWithName("id").description("유저 아이디")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("컬렉션 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]").description("컬렉션 리스트")

                        )
                ));
    }
}
