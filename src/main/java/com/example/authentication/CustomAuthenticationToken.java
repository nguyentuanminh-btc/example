package com.example.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private static final Long serialVersionUID = 1L;

    private final Integer id;
    public CustomAuthenticationToken(Object principal, Object credentials, Integer id) {
        super(principal, credentials);
        this.id = id;
    }

    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Integer id) {
        super(principal, credentials, authorities);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
