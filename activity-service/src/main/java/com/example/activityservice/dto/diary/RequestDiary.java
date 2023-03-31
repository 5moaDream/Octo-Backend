package com.example.activityservice.dto.diary;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RequestDiary {
    @NotNull
    @Email
    private String userEmail;
    @NotNull
    private String content;
}
