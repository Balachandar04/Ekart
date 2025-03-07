package com.creative.ekart.service;

import com.creative.ekart.exception.ApiException;
import com.creative.ekart.model.User;
import com.creative.ekart.payload.AuthRequest;
import com.creative.ekart.payload.UserInfo;
import com.creative.ekart.repository.UserRepository;
import com.creative.ekart.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class  UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

    }
    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUserToDb(AuthRequest userDto) {

        Boolean usernamePresent = userRepository.existsUserByUsername(userDto.getUsername());
        Boolean emailPresent = userRepository.existsUserByEmail(userDto.getEmail());
        if(usernamePresent) {
            throw new ApiException("User already exists");
        }
        if(emailPresent) {
            throw new ApiException("Email already exists");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = modelMapper.map(userDto, User.class);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User loadedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserInfo userInfo = UserInfo.buildUserInfo(loadedUser);
        return userInfo;
    }
}
