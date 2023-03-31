package com.example.userservice.Login.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity(name="USER_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDAO {
    @Id
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "OCTO_ID")
    private Long octoId;
}
