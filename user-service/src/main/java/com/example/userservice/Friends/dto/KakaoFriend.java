package com.example.userservice.Friends.dto;

import lombok.Data;

@Data
public class KakaoFriend {
    private Integer total_count;
    private Friend[] friends;

    @Data
    public class Friend{
        private Long id;
        private String uuid;
        private String profile_nickname;
    }
}
