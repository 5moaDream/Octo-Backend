package com.example.userservice.setting.dto;

import lombok.*;

import javax.validation.constraints.Max;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirstSettingDTO {
    private Long userId;
    @Max(15)
    private String characterName;
    @Max(45)
    private String stateMSG;
    private float sleepTime;
    private float distance;
}

