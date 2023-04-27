package com.example.userservice.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstSettingDTO {
    private Long userId;
    @Size(max = 15)
    private String characterName;
    @Size(max = 45)
    private String stateMSG;
    @Size(min = 6, max = 8)
    private float sleepTime;
    @Size(min = 1)
    private float distance;
}
