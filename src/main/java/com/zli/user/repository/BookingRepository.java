package com.zli.user.repository;

import com.zli.user.model.AppBooking;
import com.zli.user.model.AppUser;
import com.zli.user.model.BookingStatus;
import com.zli.user.model.BookingTime;
import com.zli.user.model.impl.AppBookingImpl;
import com.zli.user.model.impl.AppUserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<AppBookingImpl, Long> {

    public default AppBooking insertBooking(LocalDate bookingDate, BookingTime bookingTime, BookingStatus bookingStatus, Long userId) {
        return save(new AppBookingImpl(bookingDate, bookingTime, bookingStatus, userId));
    }

    public Optional<AppBooking> findByUserId(Long userId);

    @Query(value = "select booking.booking_id, booking.booking_date, booking.booking_time from user left join booking on user.user_id = booking.user_id where user.user_id = :#{#id}",
    countQuery = "select count(*) from user",
    nativeQuery = true)
    List getUserBookings(@Param("id") Long userId);
}
