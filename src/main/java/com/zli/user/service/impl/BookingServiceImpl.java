package com.zli.user.service.impl;

import com.zli.user.model.BookingStatus;
import com.zli.user.model.BookingTime;
import com.zli.user.model.impl.AppBookingImpl;
import com.zli.user.model.impl.AppUserImpl;
import com.zli.user.repository.BookingRepository;
import com.zli.user.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<AppBookingImpl> getAllBookings() {
        return new ArrayList<>(bookingRepository.findAll());
    }

    public List<AppBookingImpl> getUserBookings(Long id) {return new ArrayList<>(bookingRepository.getUserBookings(id));}

    @Override
    public boolean save(AppBookingImpl booking) {
        try {
            bookingRepository.save(booking);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public AppBookingImpl getById(Long id) {
        try {
            return bookingRepository.findById(id).get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<AppBookingImpl> getByStatus(BookingStatus status) {

        List<AppBookingImpl> bookings =bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "bookingStatus"));
        return bookings;
    }

    @Override
    public boolean deleteBooking(Long id) {
        if(bookingRepository.existsById(id)) {
            try {
                bookingRepository.deleteById(id);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        else {
            return false;
        }

    }
}
