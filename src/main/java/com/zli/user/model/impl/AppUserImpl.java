package com.zli.user.model.impl;



import com.zli.user.model.AppUser;
import com.zli.user.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


@Entity
@Table(name = "user")
public class AppUserImpl implements AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "sur_name")
    private String surName;
    @Column(name = "role")
    private Role role;




    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password may not be empty")
    @Length(min = 5, max = 250, message = "Invalid Password length, min 5, max 250")
    private String password;

    public AppUserImpl(String firstName, String surName, String email, String password, Role role) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public AppUserImpl() {}


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getUserName() {
        return this.email;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getSurName() {
        return this.surName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
