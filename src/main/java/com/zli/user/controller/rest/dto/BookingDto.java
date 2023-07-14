package com.zli.user.controller.rest.dto;

import com.zli.user.model.AppBooking;
import com.zli.user.model.BookingTime;

import java.time.LocalDate;

public class BookingDto {

    public LocalDate bookingDate;
    public BookingTime bookingTime;
    public Long userId;

    public BookingDto(AppBooking booking) {
        this.bookingDate = booking.getBookingDate();
        this.bookingTime = booking.getBookingTime();
    }
}
