package com.example.service;

import com.example.authentication.CustomAuthenticationToken;
import com.example.domain.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
           Integer id = ((CustomAuthenticationToken) authentication).getId();
        }
        return null;
    }
}
