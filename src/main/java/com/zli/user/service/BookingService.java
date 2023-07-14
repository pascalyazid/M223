package com.zli.user.service;

import com.zli.user.model.BookingStatus;
import com.zli.user.model.impl.AppBookingImpl;
import com.zli.user.model.impl.AppUserImpl;

import java.util.List;

public interface BookingService {

    List<AppBookingImpl> getAllBookings();

    boolean save(AppBookingImpl booking);

    AppBookingImpl getById(Long id);

    List<AppBookingImpl> getByStatus(BookingStatus status);

    boolean deleteBooking(Long id);
}
