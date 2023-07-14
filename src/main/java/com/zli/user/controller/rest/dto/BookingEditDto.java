package com.zli.user.controller.rest.dto;

import com.zli.user.model.BookingStatus;
import com.zli.user.model.BookingTime;

import java.time.LocalDate;

public class BookingEditDto {

    public LocalDate bookingDate = LocalDate.of(2022, 12, 12);
    public BookingTime bookingTime;

    public BookingStatus bookingStatus;
    public Long userId;
}
