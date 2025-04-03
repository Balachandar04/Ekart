package com.creative.ekart.service;

import com.creative.ekart.config.AppRole;
import com.creative.ekart.exception.ApiException;
import com.creative.ekart.model.Role;
import com.creative.ekart.model.User;
import com.creative.ekart.payload.AuthRequest;
import com.creative.ekart.payload.UserInfo;
import com.creative.ekart.repository.RoleRepository;
import com.creative.ekart.repository.UserRepository;
import com.creative.ekart.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "custom")
public class  UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        Role role = userDto.getRole() == null ? new Role(AppRole.ROLE_ADMIN) :
                roleRepository.findByRoleName(AppRole.valueOf(userDto.getRole()))
                .orElseThrow(() -> new ApiException("Role not found"));

        user.getRoles().add(role);
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
