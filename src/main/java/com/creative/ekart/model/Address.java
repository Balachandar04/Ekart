package com.creative.ekart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String doorNo;
    private String street;
    private String city;
    private String state;

    @Length(min = 6, max = 6)
    @Column(nullable = false)
    private String zipCode;

    private String country;

    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "address")
    private Set<User> users = new HashSet<>();


}
