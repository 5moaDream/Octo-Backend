package com.example.activityservice;

import com.example.activityservice.controller.DiaryController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiaryController.class)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class ActivityRestControllerTest {
    @MockBean
    DiaryServiceImpl diaryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    RequestDiary requestDiary = new RequestDiary("abc@email.com", "다이어리 내용 작성");

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.restDocs = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(this.restDocs)
                .build();
    }


    @Nested
    @DisplayName("다이어리 작성")
    class WriteDiary{
        @Test
        @DisplayName("다이어리 작성완료")
        void write_success() throws Exception {
            mockMvc.perform(post("/diary")
                            .content(objectMapper.writeValueAsBytes(requestDiary))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andDo(
                        restDocs.document(
                            requestFields(
                                fieldWithPath("userEmail").description("유저 이메일"),
                                fieldWithPath("content").description("내용")
                            ),
                            responseFields(
                                fieldWithPath("content").description("내용"),
                                fieldWithPath("writeTime").description("작성 시간")
                            )
                        )
                    );
        }
    }


}
