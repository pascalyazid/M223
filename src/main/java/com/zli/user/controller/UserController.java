package com.zli.user.controller;

import com.zli.user.controller.rest.dto.UserDto;
import com.zli.user.controller.rest.dto.UserInputDto;
import com.zli.user.model.AppUser;
import com.zli.user.model.impl.AppUserImpl;
import com.zli.user.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/users")
    String getUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "UserList";
    }

    @GetMapping("/users/{id}")
    String getUSerByID( @PathVariable Long id, Model model) {
        if(userService.getById(id) != null) {
            model.addAttribute("response", "Username:");
            model.addAttribute("user", userService.getById(id));
            model.addAttribute(HttpStatus.OK);
        }
        else {
            model.addAttribute("response", "Couldn't find user");
            model.addAttribute("user", new AppUserImpl(" ", " ", " ", " ", userService.getById(id).getRole()));
            model.addAttribute(HttpStatus.NOT_MODIFIED);
        }
        return "GetUser";

    }

    @GetMapping("/register")
    String addUserForm(Model model) {
        AppUserImpl user = new AppUserImpl();
        model.addAttribute("user", user);
        return "AddUser";
    }

    @PostMapping("/web/users/add")
    String register(@Valid AppUser user, BindingResult result, Model model) {
        System.out.println(result.getModel().isEmpty());
        System.out.println("Username: " + (user.getUserName()) + "\nPassword: " + user.getPassword());
        try {
            if(result.hasErrors()) {
                System.out.println("Invalid Data");
                return "AddUser";
            }
            if(userService.save(new AppUserImpl(user.getFirstName(), user.getSurName(), user.getUserName(), user.getPassword(), user.getRole()))) {
                System.out.println("User saved");
                model.addAttribute(HttpStatus.CREATED);
                return "redirect:/";

            } else {
                System.out.println("Error saving user");
                model.addAttribute(HttpStatus.BAD_REQUEST);
                return "error_UserAdd";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute(HttpStatus.BAD_REQUEST);
            return "error_UserAdd";
        }

    }

    @PostMapping("/api/v1/users")
    String setUserRole() {return null;}
}
