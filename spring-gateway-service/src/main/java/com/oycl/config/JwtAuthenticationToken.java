package com.oycl.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken implements Authentication {
    private List<GrantedAuthority> authorities;
    private String name;
    private boolean authenticated;
    public JwtAuthenticationToken(List<GrantedAuthority> authorities, String id){
        this.authorities = authorities;
        this.name = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticate) throws IllegalArgumentException {
        authenticated = isAuthenticate;
    }

    @Override
    public String getName() {
        return name;
    }
}
