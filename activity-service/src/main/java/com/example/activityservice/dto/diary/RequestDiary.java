package com.example.activityservice.dto.diary;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
