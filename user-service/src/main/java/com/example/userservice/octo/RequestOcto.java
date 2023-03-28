package com.example.userservice.octo;

import lombok.Data;

@Data
public class RequestOcto {
    private long userId;
    private String name;
    private long octoKindId;
}
