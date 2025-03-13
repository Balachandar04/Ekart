package com.creative.ekart.controller;

import com.creative.ekart.payload.ApiResponse;
import com.creative.ekart.payload.AuthRequest;
import com.creative.ekart.payload.AuthResponse;
import com.creative.ekart.jwt.JwtUtils;
import com.creative.ekart.service.interfaces.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody AuthRequest authRequest) {

        userService.saveUserToDb(authRequest);
        ApiResponse response = new ApiResponse("User Successfull Created",true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest, HttpServletResponse response) {

        //Authenticate it !!!
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));
        // Getting UserDetails from it
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //Generating JWT
        //String token = jwtUtils.generateToken(userDetails);
        Cookie jwtCookie = jwtUtils.generateTokenCookie(userDetails);
        response.addCookie(jwtCookie);
        AuthResponse authResponse = new AuthResponse(userDetails.getUsername(),jwtCookie.getValue());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        Cookie jwtCookie = jwtUtils.generateEmptyCookie();
        response.addCookie(jwtCookie);
        return new ResponseEntity<>(new ApiResponse("Logout Successful",true), HttpStatus.OK);
    }

}
