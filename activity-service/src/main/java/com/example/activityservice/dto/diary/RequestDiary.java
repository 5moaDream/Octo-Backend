package com.example.activityservice.dto.diary;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDiary {
    @NotNull
    @Email
    private String userEmail;
    @NotNull
    private String content;
}
