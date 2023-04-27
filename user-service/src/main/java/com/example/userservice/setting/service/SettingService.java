package com.example.userservice.setting.service;

import com.example.userservice.setting.domain.SettingEntity;
import com.example.userservice.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;

    public void updateSettingInfo(SettingEntity setting){
        settingRepository.save(setting);
    }

    public SettingEntity getSettingInfo(Long userId){
        return settingRepository.findByUserId(userId);
    }
}
