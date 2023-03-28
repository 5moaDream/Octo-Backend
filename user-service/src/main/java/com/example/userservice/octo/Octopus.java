package com.example.userservice.octo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CHARACTER_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Octopus {
    @Id
    @GeneratedValue
    private long CHARACTER_Id;
    private long userId;
    private String name;
    private int level;
    private int experience;
    private long octoKindId;

    public Octopus(long userId, String name, int level, int experience, long octoKindId) {
        this.userId = userId;
        this.name = name;
        this.level = level;
        this.experience = experience;
        this.octoKindId = octoKindId;
    }
}
