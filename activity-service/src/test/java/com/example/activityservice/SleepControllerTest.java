//package com.example.activityservice;
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
//import org.springframework.restdocs.payload.FieldDescriptor;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//
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
//public class SleepControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//
//    @Test
//    @DisplayName("수면 기록")
//    void 수면_기록() throws Exception {
//        // Given
//        Long userId = 2713582482L;
//
//        String sleep = "{" +
//                "\"sleptTime\":\"2023-05-16T23:20:22\",\n" +
//                "\"wakeUpTime\": \"2023-05-17T07:20:22\",\n" +
//                "\"totalSleepTime\": 480" +
//                "}";
//
//        // When
//        MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
//                .post("/sleep")
//                .param("id", String.valueOf(userId))
//                .content(sleep)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer AccessToken");
//
//        mockMvc.perform(requestBuilder)
//                // Then
//                .andExpect(status().isOk())
//                .andDo(document("수면 기록",
//                        ResourceSnippetParameters.builder()
//                                .tag("수면 기록")
//                                .summary("")
//                                .description("수면 정보 기록")
//                                .requestSchema(Schema.schema("Request.Sleep")),
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("sleptTime").type(JsonFieldType.STRING).description("수면 시작 시간"),
//                                fieldWithPath("wakeUpTime").type(JsonFieldType.STRING).description("기상 시간"),
//                                fieldWithPath("totalSleepTime").type(JsonFieldType.NUMBER).description("총 수면 시간")
//                        )));
//    }
//
//    @Test
//    @DisplayName("당일 수면 기록 조회")
//    void 당일_수면_기록_조회() throws Exception {
//        // Given
//        Long userId = 2713582482L;
//
//        // When
//        MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
//                .get("/sleep", userId)
//                .param("id", String.valueOf(userId))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer AccessToken");
//
//        mockMvc.perform(requestBuilder)
//                // Then
//                .andExpect(status().isOk())
//                .andDo(document("당일 수면 기록 조회",
//                        ResourceSnippetParameters.builder()
//                                .tag("당일 수면 기록 조회")
//                                .summary("")
//                                .description("당일 수면 기록 조회")
//                                .responseSchema(Schema.schema("Response.Sleep")),
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("sleepId").type(JsonFieldType.NUMBER).description("수면 아이디"),
//                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
//                                fieldWithPath("sleptTime").type(JsonFieldType.STRING).description("수면 시작 시간"),
//                                fieldWithPath("wakeUpTime").type(JsonFieldType.STRING).description("기상 시간"),
//                                fieldWithPath("totalSleepTime").type(JsonFieldType.NUMBER).description("총 수면 시간")
//                        )));
//    }
//
//    @Test
//    @DisplayName("모든 수면 기록 조회")
//    void 모든_수면_기록_조회() throws Exception {
//        // Given
//        Long userId = 2713582482L;
//
//        FieldDescriptor[] sleep = new FieldDescriptor[]{
//                fieldWithPath("sleepId").type(JsonFieldType.NUMBER).description("수면 아이디"),
//                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
//                fieldWithPath("sleptTime").type(JsonFieldType.STRING).description("수면 시작 시간"),
//                fieldWithPath("wakeUpTime").type(JsonFieldType.STRING).description("기상 시간"),
//                fieldWithPath("totalSleepTime").type(JsonFieldType.NUMBER).description("총 수면 시간")
//        };
//
//        // When
//        MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
//                .get("/sleep/all", userId)
//                .param("id", String.valueOf(userId))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer AccessToken");
//
//        mockMvc.perform(requestBuilder)
//                // Then
//                .andExpect(status().isOk())
//                .andDo(document("모든 수면 기록 조회",
//                        ResourceSnippetParameters.builder()
//                                .tag("모든 수면 기록 조회")
//                                .summary("")
//                                .description("모든 수면 기록 조회")
//                                .responseSchema(Schema.schema("Response.Sleep")),
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[]").description("sleep")
//
//                        ).andWithPrefix("[].", sleep)));
//    }
//}
