package com.javaproject.rasahotel.services.booking;

import java.util.List;

import com.javaproject.rasahotel.dto.booking.BookingRequestDto;
import com.javaproject.rasahotel.entities.BookedRoom;

public interface BookingService {

    void cancelBooking(String bookingId);

    BookedRoom saveBooking(String roomId, BookingRequestDto dto);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();

    List<BookedRoom> findBookingByEmail(String email);
}
