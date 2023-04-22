package com.example.userservice.Friends.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class FriendDTO {
    private Long id;
    private String nickName;
    private String characterImage;
}
