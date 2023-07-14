package com.zli.user.init;


import com.zli.user.model.Role;
import com.zli.user.repository.BookingRepository;
import com.zli.user.repository.UserRepository;
import com.zli.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


public class ServerInitialisation implements ApplicationRunner {


    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.insertUser("Pascal", "Thuma", "lol@gmail.com", "password", Role.USER);
    }
}
