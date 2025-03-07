package com.creative.ekart.payload;

import com.creative.ekart.model.Role;
import com.creative.ekart.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInfo implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String password;

    private final String username;
    private final Set<GrantedAuthority> authorities;

    public UserInfo(String username,String password, Set<Role> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
                .collect(Collectors.toSet());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static UserInfo buildUserInfo(User user) {
        return new UserInfo(user.getUsername(),user.getPassword(),user.getRoles());
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
