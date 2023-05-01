package com.example.userservice;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.example.userservice.setting.domain.SettingEntity;
import com.example.userservice.setting.dto.FirstSettingDTO;
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


import java.util.Date;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
        String kakaoToken = "goBlg-gLxA6FFyhLg-HMlWthP6J7vu580r8QtllXCj1y6wAAAYfVPAfv";

        //when
        mockMvc.perform(get("/unauthorization/kakao-login")
                        .header("Authorization", "Bearer " + kakaoToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
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

    @Test
    @DisplayName("토큰 재발급")
    void 토큰_재발급() throws Exception {
        String fresh_token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNzEzNTgyNDgyIiwiZXhwIjoxNjg1NTA0MTg3fQ.tb6w5VsSWRklGsPzW2THFFnj1uc-wBd3uoZ04FSl2p3l1OVDHDzLu_jnTDvOFKhmIXudTGVdCuQ6tiJ6zpJgOA";
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
        mockMvc.perform(get("/user/{userId}", userId)
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
                                fieldWithPath("thumbnail_image_url").type(JsonFieldType.STRING).description("썸네일 이미지"),
                                fieldWithPath("characterName").type(JsonFieldType.STRING).description("캐릭터 이름").optional(),
                                fieldWithPath("experienceValue").type(JsonFieldType.NUMBER).description("경험치"),
                                fieldWithPath("character_url").type(JsonFieldType.STRING).description("캐릭터 이미지")
                        )
                ));
    }

    @Test
    @DisplayName("첫 로그인 시 유저정보 세팅")
    void 첫_로그인_유저정보_세팅() throws Exception {
        //given
        FirstSettingDTO firstSettingDTO = new FirstSettingDTO();

        firstSettingDTO.setUserId(2713582482L);
        firstSettingDTO.setCharacterName("나의 문어");
        firstSettingDTO.setStateMSG("열심히 하자!");
        firstSettingDTO.setSleepTime(7);
        firstSettingDTO.setDistance(2.5F);

        //when
        mockMvc.perform(put("/first")
                        .content(objectMapper.writeValueAsString(firstSettingDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("첫 로그인 세팅",
                        ResourceSnippetParameters.builder()
                                .tag("첫 로그인 세팅")
                                .summary("")
                                .description("첫 로그인 후 세팅 정보 및 캐릭터 명 등록")
                                .responseSchema(Schema.schema("Request.FirstLogin_setting")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("characterName").type(JsonFieldType.STRING).description("캐릭터 이름"),
                                fieldWithPath("stateMSG").type(JsonFieldType.STRING).description("상태메세지").optional(),
                                fieldWithPath("sleepTime").type(JsonFieldType.NUMBER).description("수면 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("러닝 거리")
                        )));
    }

    @Test
    @DisplayName("유저 세팅정보 업데이트")
    void 유저_세팅정보_업데이트() throws Exception {
        //given
        SettingEntity settingEntity = new SettingEntity();
        settingEntity.setUserId(2713582482L);
        settingEntity.setSleepTime(7);
        settingEntity.setDistance(2);
        settingEntity.setDday(new Date());

        //when
        mockMvc.perform(put("/setting")
                        .content(objectMapper.writeValueAsString(settingEntity))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("세팅정보 업데이트",
                        ResourceSnippetParameters.builder()
                                .tag("세팅 정보 업데이트")
                                .summary("")
                                .description("목표 기간 달성 후 새로운 수면 목표, 러닝 목표 설정")
                                .requestSchema(Schema.schema("Request.Setting"))
                        ,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("sleepTime").type(JsonFieldType.NUMBER).description("수면 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("러닝 거리"),
                                fieldWithPath("dday").type(JsonFieldType.STRING).description("목표 기간")
                        )));
        //then
    }

    @Test
    @DisplayName("세팅 정보 조회")
    void 세팅정보_조회() throws Exception {
        //given
        Long userId = 2713582482L;

        //when
        mockMvc.perform(get("/setting/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("세팅정보 조회",
                        ResourceSnippetParameters.builder()
                                .tag("세팅정보 조회")
                                .summary("")
                                .description("세팅 창에 들어갔을 때 넘겨줄 세팅정보 조회")
                                .responseSchema(Schema.schema("Response.Setting"))
                        ,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("sleepTime").type(JsonFieldType.NUMBER).description("목표 수면 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("목표 러닝 시간"),
                                fieldWithPath("dday").type(JsonFieldType.STRING).description("목표 기간")
                        )
                ));
    }

//    @Test
//    @DisplayName("친구 정보 불러오기")
//    void 친구_정보_불러오기() throws Exception {
//        //given
//        RequestKakaoToken kakaoToken = new RequestKakaoToken(
//                "cyhM_aN6Hiu-5NPs6-NdANVcKS2yWy-cl1nW9hH5CiolTgAAAYfM4IA8",
//                "_RSYSU1zdl4mh3K2PTmRu9upMLluH8TAtHn_Ig6lCiolTgAAAYfM4IA6"
//        );
//        //when
//        mockMvc.perform(get("/kakao-friends?offset=0")
//                        .content(objectMapper.writeValueAsString(kakaoToken))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer AccessToken"))
//                //then
//                .andExpect(status().isOk())
//                .andDo(document("친구 목록 조회",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("access_token").type(JsonFieldType.STRING).description("카카오 엑세스 토큰"),
//                                fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("카카오 리프레쉬 토큰")
//                        ),
//                        responseFields(
//                                fieldWithPath("friendList[].userId").type(JsonFieldType.STRING).description("유저 아이디"),
//                                fieldWithPath("friendList[].nickName").type(JsonFieldType.STRING).description("별명"),
//                                fieldWithPath("friendList[].characterImage").type(JsonFieldType.STRING).description("캐릭터 이미지")
//                        )
//                ));
//    }
}
