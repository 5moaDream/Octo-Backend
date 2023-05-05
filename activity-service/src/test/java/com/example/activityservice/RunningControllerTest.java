package com.example.activityservice;

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
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class RunningControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("러닝 기록")
    @Transactional
    void 러닝_기록() throws Exception{
        //given
        String running = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"runningTime\": \"2023-05-02T23:50:01.531079\",\n" +
                "    \"totalRunningTime\": 18,\n" +
                "    \"distance\" : 2.2\n" +
                "}";
        //when
        mockMvc.perform(post("/running")
                        .content(running)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("러닝 기록",
                        ResourceSnippetParameters.builder()
                                .tag("러닝 기록")
                                .summary("")
                                .description("러닝 정보 기록")
                                .requestSchema(Schema.schema("Request.Running")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("runningTime").type(JsonFieldType.STRING).description("러닝 시작 시간"),
                                fieldWithPath("totalRunningTime").type(JsonFieldType.NUMBER).description("총 러닝 시간"),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("달린 거리")
                        )));
    }

    @Test
    @DisplayName("일주일 러닝 조회")
    @Transactional
    void 일주일_러닝_조회() throws Exception{
        //given
        Long userId = 1L;

        FieldDescriptor[] running = new FieldDescriptor[]{
                fieldWithPath("runningId").type(JsonFieldType.NUMBER).description("러닝 아이디"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                fieldWithPath("runningTime").type(JsonFieldType.STRING).description("러닝 시작 시간"),
                fieldWithPath("totalRunningTime").type(JsonFieldType.NUMBER).description("총 러닝 시간"),
                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("달린 거리")
        };
        //when
        mockMvc.perform(get("/running/week/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("이번 주 러닝 기록 조회",
                        ResourceSnippetParameters.builder()
                                .tag("이번 주 러닝 기록 조회")
                                .summary("")
                                .description("유저의 일주일 러닝 기록 조회")
                                .responseSchema(Schema.schema("Response.List<Running>")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                            fieldWithPath("[]").description("running")
                        ).andWithPrefix("[].", running)));
    }

    @Test
    @DisplayName("오늘 러닝 기록 조회")
    @Transactional
    void 오늘_러닝_기록_조회() throws Exception{
        //given
        Long userId = 1L;

        FieldDescriptor[] running = new FieldDescriptor[]{
                fieldWithPath("runningId").type(JsonFieldType.NUMBER).description("러닝 아이디"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                fieldWithPath("runningTime").type(JsonFieldType.STRING).description("러닝 시작 시간"),
                fieldWithPath("totalRunningTime").type(JsonFieldType.NUMBER).description("총 러닝 시간"),
                fieldWithPath("distance").type(JsonFieldType.NUMBER).description("달린 거리")
        };
        //when
        mockMvc.perform(get("/running/today/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("오늘 러닝 기록 조회",
                        ResourceSnippetParameters.builder()
                                .tag("오늘 러닝 기록 조회")
                                .summary("")
                                .description("유저의 오늘 러닝 기록 조회")
                                .responseSchema(Schema.schema("Response.List<Running>")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]").description("running")
                        ).andWithPrefix("[].", running)));
    }
}
