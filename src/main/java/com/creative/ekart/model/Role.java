package com.creative.ekart.model;

import com.creative.ekart.config.AppRole;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,name="role_name",nullable=false)
    private AppRole roleName;


}
