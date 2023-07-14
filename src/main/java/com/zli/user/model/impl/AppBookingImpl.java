package com.zli.user.model.impl;

import com.zli.user.model.AppBooking;
import com.zli.user.model.BookingStatus;
import com.zli.user.model.BookingTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class AppBookingImpl implements AppBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id")
    private Long id;

    @Column(name = "booking_date")
    @NotNull
    private LocalDate bookingDate;


    @Column(name = "booking_time")
    @NotNull
    private BookingTime  bookingTime;

    @Column(name = "booking_status")
    @NotNull
    private BookingStatus bookingStatus;


    @Column(name = "user_id")
    @NotNull
    private Long userId;

    public AppBookingImpl() {}

    public AppBookingImpl(LocalDate bookingDate, BookingTime bookingTime, BookingStatus bookingStatus, Long userId) {
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
        this.userId = userId;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public LocalDate getBookingDate() {
        return this.bookingDate;
    }

    @Override
    public BookingTime getBookingTime() {
        return this.bookingTime;
    }

    @Override
    public BookingStatus getBookingStatus() {
        return null;
    }

    @Override
    public Long getUser() {
        return this.userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingTime(BookingTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}

