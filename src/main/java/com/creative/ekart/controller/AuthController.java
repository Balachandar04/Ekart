package com.creative.ekart.controller;

import com.creative.ekart.model.User;
import com.creative.ekart.payload.AuthRequest;
import com.creative.ekart.payload.AuthResponse;
import com.creative.ekart.service.JwtUtils;
import com.creative.ekart.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UserService userService;


    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest authRequest) {

        User user = userService.saveUserToDb(authRequest);
        return new ResponseEntity<>(user.toString(), HttpStatus.CREATED);

    }

    @PostMapping("signin")
    public ResponseEntity<AuthResponse> signin(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
