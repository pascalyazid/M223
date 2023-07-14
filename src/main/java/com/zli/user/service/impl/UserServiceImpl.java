package com.zli.user.service.impl;

import com.zli.user.controller.rest.dto.UserLoginDto;
import com.zli.user.model.AppUser;
import com.zli.user.model.impl.AppUserImpl;
import com.zli.user.repository.UserRepository;
import com.zli.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AppUserImpl> getAllUsers() {

        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public boolean save(AppUserImpl user) {

        try {
            userRepository.save(user);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public AppUserImpl getById(Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (Exception ex) {
            return null;
        }
    }

    public AppUserImpl getbyEmail(String email) {
        try {
            return userRepository.findByEmail(email).get();
        } catch (Exception ex) {
            return null;
        }
    }

    public AppUserImpl login(UserLoginDto user) {
        if (userRepository.findByEmail(user.email).isPresent() && userRepository.findByEmail(user.email).get().getPassword().equals(user.password))
            return userRepository.findByEmail(user.email).get();
        else
            return null;

    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            if (!userRepository.findById(id).isEmpty()) {
                userRepository.deleteById(id);
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
