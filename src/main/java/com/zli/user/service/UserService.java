package com.zli.user.service;

import com.zli.user.controller.rest.dto.UserLoginDto;
import com.zli.user.model.AppUser;
import com.zli.user.model.impl.AppUserImpl;

import java.util.List;

public interface UserService {

    List<AppUserImpl> getAllUsers();

    boolean save(AppUserImpl users);

    AppUserImpl getById(Long id);

    boolean deleteUser(Long id);

    AppUser login(UserLoginDto user);
}
