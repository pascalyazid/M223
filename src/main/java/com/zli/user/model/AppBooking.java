package com.zli.user.model;

import java.time.LocalDate;

public interface AppBooking {
    Long getId();
    LocalDate getBookingDate();
    BookingTime getBookingTime();

    BookingStatus getBookingStatus();
    Long getUser();
}
