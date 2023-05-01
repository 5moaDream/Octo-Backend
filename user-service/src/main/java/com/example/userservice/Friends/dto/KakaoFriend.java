package com.example.userservice.Friends.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KakaoFriend {
    private Integer total_count;
    private Friend[] friends;

    @Setter
    @Getter
    public class Friend{
        private Long id;
        private String uuid;
        private String profile_nickname;
    }
}
