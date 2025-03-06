package com.creative.ekart.model;

import com.creative.ekart.config.AppRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,name="role_name",nullable=false)
    private AppRole roleName;

    public Role() {
    }

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }




}
