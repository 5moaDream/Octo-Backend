package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class KakaoFriend {
    @JsonProperty("elements")
    private Friend[] elements;

    @JsonProperty("total_count")
    private Integer total_count;

    @Data
    public class Friend {
        @JsonProperty("id")
        private long id;
        @JsonProperty("uuid")
        private String uuid;
        @JsonProperty("profile_nickname")
        private String profile_nickname;
        @JsonProperty("profile_thumbnail_image")
        private String profile_thumbnail_image;
    }
}








