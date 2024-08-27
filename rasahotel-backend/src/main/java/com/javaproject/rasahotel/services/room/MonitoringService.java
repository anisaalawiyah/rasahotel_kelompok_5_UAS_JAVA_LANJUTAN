package com.javaproject.rasahotel.services.room;

import com.javaproject.rasahotel.dto.PageResponse;
import com.javaproject.rasahotel.entities.Room;

public interface MonitoringService {
     PageResponse<Room> findAll(String name, 
               Long price, int page, int size, String sort);
}
