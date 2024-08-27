package com.javaproject.rasahotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.rasahotel.entities.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

    // @Query(" SELECT r FROM Room r " +
    //         " WHERE r.name LIKE %:name% " +
    //         " AND r.id NOT IN (" +
    //         "  SELECT br.room_id FROM BookedRoom br " +
    //         "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
    //         ")")

    // @Query("select r from Room r, BookedRoom b where b.checkInDate <= :checkOutDate and b.checkOutDate >= :checkInDate and r.id not in b.room_id")
    // List<Room> findAvailableRoomsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate, String name);
}
