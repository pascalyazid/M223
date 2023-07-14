package com.zli.user.controller.rest.dto;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zli.user.model.AppUser;
import com.zli.user.model.Role;
import com.zli.user.model.impl.AppUserImpl;
import com.zli.user.security.JwtRes;
import com.zli.user.security.JwtUtils;
import com.zli.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserDtoRestController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Get a specific user with an id.
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    ResponseEntity getUser(@PathVariable Long id) {
        try {
            if(userService.getById(id) != null) {
                return new ResponseEntity<>(new JSONPObject("user", userService.getById(id)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Couldn't find user: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Couldn't find user with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id")
    ResponseEntity getId(@RequestHeader("Authorization") String header) {
            Long id = jwtUtils.getIdFromJwtToken(header);
            try {
                return new ResponseEntity(id.toString(), HttpStatus.OK);
            }
            catch (Exception ex) {
                return new ResponseEntity(null, HttpStatus.NOT_FOUND);
            }
    }

    /**
     * Returns a list of all users.
     * @return
     */
    @GetMapping("/list")
    ResponseEntity<List<AppUserImpl>> listUsers() {
        //String username = jwtUtils.getUsernameFromJwtToken();

        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto user, HttpServletResponse response) {
        AppUserImpl appUser = userService.login(user);
        if(appUser != null) {
            String token = jwtUtils.generateJwtToken(appUser.getUserName(), appUser.getRole().name(), appUser.getId());
            response.setHeader("Auhtorization", token);
            return ResponseEntity.ok(new JwtRes(token));
        }
            return ResponseEntity.notFound().build();


    }

    /**
     * Register a new user.
     * @param userDto
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/auth/register")
    void registerUser(@Valid @RequestBody UserInputDto userDto, HttpServletResponse response) throws IOException {
        System.out.println(userDto);
        try {
            AppUserImpl createdUser = new AppUserImpl(userDto.firstName, userDto.surname, userDto.email, userDto.password, userDto.role);
            createdUser.setRole(Role.USER);
            if(userService.save(createdUser)) {
                //return new ResponseEntity<>(createdUser.toString(), HttpStatus.CREATED);
                response.setStatus(200);
                response.setHeader("Created", "true");
                response.getWriter().println("User " + userDto.email + " created.");
            }

                else {
                //return new ResponseEntity<>(new HttpStatus.CONFLICT);
                response.setStatus(409);
                response.setHeader("Created", "false");
                response.getWriter().println("Couldn't create user " + userDto.email);
            }

        } catch (Exception ex) {
            //ex.printStackTrace();
            //return new ResponseEntity<>("Couldn't create user", HttpStatus.BAD_REQUEST);
            response.setStatus(400);
            response.setHeader("Created", "false");
            response.getWriter().println("Error creating user " + userDto.email);
        }

    }

    @PutMapping(value = "/edit")
    void editUser(@RequestBody UserInputDto userDto, HttpServletResponse response, @RequestHeader("Authorization") String header) throws IOException {
        if (jwtUtils.admin(header) || jwtUtils.user(header)) {
            Long id = jwtUtils.getIdFromJwtToken(header);
            if (userService.getById(id) != null) {
                try {
                    AppUserImpl editUser = new AppUserImpl(userDto.firstName, userDto.surname, userDto.email, userService.getById(id).getPassword(), userDto.role);
                    editUser.setId(id);
                    userService.save(editUser);
                    response.setStatus(200);
                    response.setHeader("Updated", "true");
                    response.getWriter().println("User updated");
                } catch (Exception ex) {
                    response.setStatus(400);
                    response.setHeader("Updated", "true");
                    response.getWriter().println("Error while updating user");
                }
            } else {
                response.setStatus(404);
                response.setHeader("Updated", "false");
                response.getWriter().println("Couldn't find user with id: " + id);
            }
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteUser(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            if(userService.deleteUser(id)) {
                response.setHeader("deleted", "true");
                response.setStatus(200);
                response.getWriter().println("User with id: " + id + " deleted.");
            }
            else {
                response.setStatus(404);
                response.setHeader("deleted", "false");
                response.getWriter().println("Couldn't find user with id: " + id + ".");
            }
        } catch (IOException e) {
            response.setHeader("deleted", "false");
            response.setStatus(400);
            response.getWriter().println("Error trying to delete user with id: " + id + ".");
        }
    }
}
