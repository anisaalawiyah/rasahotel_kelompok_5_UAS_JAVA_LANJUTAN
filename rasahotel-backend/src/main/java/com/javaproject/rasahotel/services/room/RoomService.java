package com.javaproject.rasahotel.services.room;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.javaproject.rasahotel.dto.room.RoomRequestDto;
import com.javaproject.rasahotel.entities.Room;

public interface RoomService {

    List<Room> getAll();

    Room add(RoomRequestDto dto);

    Room update(String idRoom, RoomRequestDto dto);

    void delete(String idRoom);

    Room getRoomById(String string);

    Room uploadPhoto(String idRoom, MultipartFile file);
}
