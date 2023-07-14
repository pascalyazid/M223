package com.zli.user.controller.rest.dto;


import com.zli.user.model.BookingStatus;
import com.zli.user.model.impl.AppBookingImpl;
import com.zli.user.model.impl.AppUserImpl;
import com.zli.user.security.JwtUtils;
import com.zli.user.service.impl.BookingServiceImpl;


import com.zli.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingDtoRestController {


    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    JwtUtils jwtUtils;

    public BookingDtoRestController() {
    }

    @GetMapping("/list")
    ResponseEntity<?> list(@RequestHeader("Authorization") String header) {
        if (jwtUtils.admin(header)) {

            try {
                return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
            } catch (Exception ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(" ", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/list/user")
    ResponseEntity<?> getUserBookings(@RequestHeader("Authorization") String header) {
        if (jwtUtils.user(header) || jwtUtils.admin(header)) {
            Long id = jwtUtils.getIdFromJwtToken(header);
            try {
                return new ResponseEntity<>(bookingService.getUserBookings(id), HttpStatus.OK);
            } catch (Exception ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(" ", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/add")
    ResponseEntity<?> addBooking(@Valid @RequestBody BookingInputDto bookingDto, @RequestHeader("Authorization") String header) {
        System.out.println(header);
        if (jwtUtils.user(header) || jwtUtils.admin(header)) {
            try {
                Long id = jwtUtils.getIdFromJwtToken(header);
                AppBookingImpl createdBooking = new AppBookingImpl(bookingDto.bookingDate, bookingDto.bookingTime, BookingStatus.PENDING, id);
                createdBooking.setUserId(id);
                if (bookingService.save(createdBooking)) {
                    return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(createdBooking, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(" ", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/edit/{id}")
    void editBooking(@Valid @RequestBody BookingEditDto bookingEditDto, @PathVariable Long id, HttpServletResponse response, @RequestHeader("Authorization") String header) throws IOException {
        if (jwtUtils.admin(header)) {
            if (bookingService.getById(id) != null && bookingService.getById(id).getUser().equals(bookingEditDto.userId)) {
                try {
                    AppBookingImpl editBooking = new AppBookingImpl(bookingEditDto.bookingDate, bookingEditDto.bookingTime, bookingEditDto.bookingStatus, bookingEditDto.userId);
                    editBooking.setId(id);
                    bookingService.save(editBooking);
                    response.setStatus(200);
                    response.setHeader("Updated", "true");
                    response.getWriter().println("Updated Booking with id: " + id + ".");
                } catch (Exception ex) {
                    response.setStatus(400);
                    response.setHeader("Updated", "false");
                    response.getWriter().println("Error while trying to update user with id: " + id + ".");
                }
            } else {
                response.setStatus(404);
                response.setHeader("Updated", "false");
                response.getWriter().println("Couldn't find booking with id: " + id);
            }
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    void deleteBooking(@PathVariable Long id, HttpServletResponse response, @RequestHeader("Authorization") String header) throws IOException {
        if (jwtUtils.admin(header) || jwtUtils.user(header)) {
            try {
                if (bookingService.deleteBooking(id)) {
                    response.setStatus(200);
                    response.setHeader("Deleted", "true");
                    response.getWriter().println("Deleted booking with the id: " + id + ".");
                } else {
                    response.setStatus(404);
                    response.setHeader("Deleted", "false");
                    response.getWriter().println("Couldn't find booking with the id: " + id + ".");
                }
            } catch (IOException e) {
                response.setStatus(400);
                response.setHeader("Deleted", "false");
                response.getWriter().println("Error while trying to delete booking with the id: " + id + ".");
            }
        }
    }
}
