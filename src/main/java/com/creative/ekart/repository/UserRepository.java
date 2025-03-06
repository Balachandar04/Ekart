package com.creative.ekart.repository;

import com.creative.ekart.model.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);

    Optional<User> findByUsername(String username);
}
