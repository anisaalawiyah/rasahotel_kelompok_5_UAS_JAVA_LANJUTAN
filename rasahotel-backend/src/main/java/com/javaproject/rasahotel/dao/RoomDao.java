package com.javaproject.rasahotel.dao;

import com.javaproject.rasahotel.dto.PageResponse;
import com.javaproject.rasahotel.entities.Room;

public interface RoomDao {
    PageResponse<Room> findAll(String name, Long price, int page, int size, String sort);
}
