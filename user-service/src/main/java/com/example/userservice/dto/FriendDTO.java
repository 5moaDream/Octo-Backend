package com.example.userservice.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO {
    private Long id;
    private String nickName;
    private String characterImage;
}
