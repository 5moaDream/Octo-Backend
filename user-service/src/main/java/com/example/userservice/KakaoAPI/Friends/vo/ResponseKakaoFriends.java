package com.example.userservice.KakaoAPI.Friends.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResponseKakaoFriends {
    private List<KakaoFriendInfo> friends;
    private Integer total_count;
}
