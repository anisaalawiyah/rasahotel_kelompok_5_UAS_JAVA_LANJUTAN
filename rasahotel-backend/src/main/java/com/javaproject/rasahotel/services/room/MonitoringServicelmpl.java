package com.javaproject.rasahotel.services.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaproject.rasahotel.dao.RoomDao;
import com.javaproject.rasahotel.dto.PageResponse;
import com.javaproject.rasahotel.entities.Room;

@Service
public class MonitoringServicelmpl implements MonitoringService {

    @Autowired
    RoomDao roomDao;

    @Override
    public PageResponse<Room> findAll(String name, Long price, int page, int size, String sort) {
        return roomDao.findAll(name, price, page, size, sort);
    }

}
