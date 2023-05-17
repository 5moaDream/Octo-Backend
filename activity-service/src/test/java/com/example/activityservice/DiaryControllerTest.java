//package com.example.activityservice;
//
//
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.epages.restdocs.apispec.Schema;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//public class DiaryControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("다이어리 작성")
//    @Transactional
//    void 다이어리_작성() throws Exception {
//        // Given
//        Long userId = 2713582482L;
//        String content = "{" +
//                "\"content\":\"오늘의 일기\"" +
//                "}";
//
//        // When
//        MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
//                .put("/diary")
//                .param("id", String.valueOf(userId))
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer AccessToken");
//
//        mockMvc.perform(requestBuilder)
//                // Then
//                .andExpect(status().isOk())
//                .andDo(document("다이어리 작성",
//                        ResourceSnippetParameters.builder()
//                                .tag("다이어리 작성")
//                                .summary("")
//                                .description("다이어리 작성")
//                                .requestSchema(Schema.schema("Request.Content")),
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("content").type(JsonFieldType.STRING).description("다이어리 내용")
//                        )));
//    }
//
//    @Test
//    @DisplayName("캘린더 조회")
//    void 캘린더_조회() throws Exception {
//        // Given
//        Long userId = 2713582482L;
//
//        // When
//        MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
//                .get("/calender/{month}","2023-05-01")
//                .param("id", String.valueOf(userId))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer AccessToken");
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andDo(document("캘린더 기록 조회",
//                        ResourceSnippetParameters.builder()
//                                .tag("캘린더 기록 조회")
//                                .summary("")
//                                .description("캘린더 조회 시 보여줄 러닝, 수면, 다이어리의 날짜 별 데이터")
//                                .requestSchema(Schema.schema("Request.CalenderDTO")),
//                        responseFields(
//                                fieldWithPath("diaryList[]").description("다이어리 기록"),
//                                fieldWithPath("runningList[]").description("러닝 기록"),
//                                fieldWithPath("sleepList[]").description("수면 기록")
//                        ).andWithPrefix("diaryList[].",
//                                fieldWithPath("today").type(JsonFieldType.STRING).description("다이어리 날짜"),
//                                fieldWithPath("content").type(JsonFieldType.STRING).description("다이어리 내용")
//                        ).andWithPrefix("runningList[].",
//                                fieldWithPath("today").type(JsonFieldType.STRING).description("러닝 날짜"),
//                                fieldWithPath("totalDistance").type(JsonFieldType.NUMBER).description("총 러닝 거리")
//                        ).andWithPrefix("sleepList[].",
//                                fieldWithPath("today").type(JsonFieldType.STRING).description("수면 날짜"),
//                                fieldWithPath("sleepTime").type(JsonFieldType.NUMBER).description("수면 시간")
//                        ))
//                );
//    }
//}
