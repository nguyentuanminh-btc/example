package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface UserInfoService {
    public User getUserInfo();
}
