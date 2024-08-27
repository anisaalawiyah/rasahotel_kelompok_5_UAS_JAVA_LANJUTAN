package com.javaproject.rasahotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.rasahotel.entities.BookedRoom;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookedRoom, String> {

    List<BookedRoom> findByRoomId(String roomId);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

}
