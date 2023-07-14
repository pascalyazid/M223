package com.zli.user.repository;

import com.zli.user.model.AppUser;
import com.zli.user.model.Role;
import com.zli.user.model.impl.AppUserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUserImpl, Long> {

    public default AppUser insertUser(String firstName, String surName, String email, String password, Role role) {
        return save(new AppUserImpl(firstName, surName, email, password, role));
    }

    public Optional<AppUserImpl> findByEmail(String email);
}
