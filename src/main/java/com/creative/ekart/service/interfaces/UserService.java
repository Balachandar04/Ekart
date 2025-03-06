package com.creative.ekart.service.interfaces;

import com.creative.ekart.model.User;
import com.creative.ekart.payload.AuthRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    public User saveUserToDb(AuthRequest user);
}
