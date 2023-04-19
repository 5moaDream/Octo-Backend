package com.example.userservice.Login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @NotNull
    //유저 정보
    private int userId;
    private String thumbnail_image_url;
    //캐릭터 정보
    private String characterName;
    private String character_url;
    private int experienceValue;
    //토큰 정보
    private String new_kakao_access_token;
    private String access_token;
    private String refresh_token;
}