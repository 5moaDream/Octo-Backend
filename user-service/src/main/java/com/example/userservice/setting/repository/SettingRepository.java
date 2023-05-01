package com.example.userservice.setting.repository;


import com.example.userservice.setting.domain.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<SettingEntity, Long> {

}
