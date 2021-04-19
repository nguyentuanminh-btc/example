package com.example.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetail extends User {
    private Integer id;
    public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id) {
        super(username, password, authorities);
        this.id = id;
    }

    public CustomUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
