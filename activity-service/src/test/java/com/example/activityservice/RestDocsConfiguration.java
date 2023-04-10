package com.example.activityservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

import javax.management.Attribute;

@TestConfiguration
public class RestDocsConfiguration {

    @Bean
    public RestDocumentationResultHandler write(){
        return MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",		// (1)
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),	// (2)
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())	// (3)
        );
    }

    public static final Attribute field(    // (4)
            final String key,
            final String value){
        return new Attribute(key,value);
    }
}