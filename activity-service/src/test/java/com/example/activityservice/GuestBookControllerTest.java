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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class GuestBookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("방명록 작성")
    @Transactional
    void 방명록_작성() throws Exception{
        //given
        Long guestId = 2L;
        String guestBook = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"content\": \"방명록3\"\n" +
                "}";
        //when
        mockMvc.perform(post("/guest-book")
                        .param("id", String.valueOf(guestId))
                        .content(guestBook)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("방명록 작성",
                        ResourceSnippetParameters.builder()
                                .tag("방명록 작성")
                                .summary("")
                                .description("방명록 작성")
                                .requestSchema(Schema.schema("Request.GuestBook")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        )));
    }

    @Test
    @DisplayName("방명록 전체 조회")
    @Transactional
    void 방명록_전체_조회() throws Exception{
        //given
        Long userId = 1L;

        FieldDescriptor[] guestBook = new FieldDescriptor[]{
                fieldWithPath("guestBookId").type(JsonFieldType.NUMBER).description("방명록 아이디"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                fieldWithPath("guestId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("read").type(JsonFieldType.BOOLEAN).description("읽음 상태"),
                fieldWithPath("createdTime").type(JsonFieldType.STRING).description("작성 시간")
        };
        //when
        mockMvc.perform(get("/guest-book?page=0&size=5", userId, requestParameters(
                        parameterWithName("page").description("현재 페이지"),
                        parameterWithName("size").description("받아올 객체 개수")))
                        .param("id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("방명록 전체 조회",
                        ResourceSnippetParameters.builder()
                                .tag("방명록 전체 조회")
                                .summary("")
                                .description("유저아이디에 해당하는 방명록 전체 조회")
                                .responseSchema(Schema.schema("Response.List<GuestBook>")),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                            fieldWithPath("[]").description("guestBook")
                        ).andWithPrefix("[].", guestBook)));
    }

    @Test
    @DisplayName("안읽은 방명록 조회")
    @Transactional
    void 안읽은_방명록_조회() throws Exception{
        //given
        Long userId = 1L;

        FieldDescriptor[] guestBook = new FieldDescriptor[]{
                fieldWithPath("guestBookId").type(JsonFieldType.NUMBER).description("방명록 아이디"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                fieldWithPath("guestId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("read").type(JsonFieldType.BOOLEAN).description("읽음 상태"),
                fieldWithPath("createdTime").type(JsonFieldType.STRING).description("작성 시간")
        };
        //when
        mockMvc.perform(get("/guest-book/unread?page=0&size=5", userId, requestParameters(
                        parameterWithName("page").description("현재 페이지"),
                        parameterWithName("size").description("받아올 객체 개수")))
                        .param("id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                //then
                .andExpect(status().isOk())
                .andDo(document("안읽은 방명록 조회",
                        ResourceSnippetParameters.builder()
                                .tag("안읽은 방명록 조회")
                                .summary("")
                                .description("유저아이디에 해당하는 방명록 중 안읽은 방명록 조회"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]").description("guestBook")
                        ).andWithPrefix("[].", guestBook)));
    }
}
