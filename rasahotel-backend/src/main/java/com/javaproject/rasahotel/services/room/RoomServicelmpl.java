package com.javaproject.rasahotel.services.room;

import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.dto.room.RoomRequestDto;
import com.javaproject.rasahotel.entities.Category;
import com.javaproject.rasahotel.entities.Room;
import com.javaproject.rasahotel.repositories.CategoryRepository;
import com.javaproject.rasahotel.repositories.RoomRepository;

@Service
public class RoomServicelmpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Room> getAll() {

        try {

            return roomRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Room add(RoomRequestDto dto) {

        try {

            Room newRoom = new Room();
            Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            if (category == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Not Found");
            newRoom.setName(dto.getName());
            newRoom.setDesc(dto.getDescription());
            newRoom.setPrice(dto.getPrice());
            newRoom.setCategory(category);
            return roomRepository.save(newRoom);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Room update(String idRoom, RoomRequestDto dto) {
        try {

            Room oldRoom = roomRepository.findById(idRoom).orElse(null);
            Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            if (oldRoom == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Not Found");
            if (category == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Not Found");
            oldRoom.setName(dto.getName());
            oldRoom.setPrice(dto.getPrice());
            oldRoom.setDesc(dto.getDescription());
            oldRoom.setCategory(category);
            return roomRepository.save(oldRoom);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    @Override
    public void delete(String idRoom) {
        try {
            Room room = roomRepository.findById(idRoom).orElse(null);
            if (room == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Not Found");
            roomRepository.delete(room);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Room getRoomById(String roomId) {
        try {

            Room room = roomRepository.findById(roomId).orElse(null);
            if (room == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Not Found");
            return room;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Room uploadPhoto(String idRoom, MultipartFile file) {
        try {
            int index = 0;
            String extension = file.getOriginalFilename();
            Room oldRoom = roomRepository.findById(idRoom).orElse(null);
            if (extension == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File Not Support");

            index = extension.lastIndexOf(".");

            if (!extension.substring(index + 1).equalsIgnoreCase("jpg")
                    && !extension.substring(index + 1).equalsIgnoreCase("jpeg")
                    && !extension.substring(index + 1).equalsIgnoreCase("png"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "File Not Supported! (Only jpg, jpeg and png acceptable)");
            if (oldRoom == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Not Found");

            oldRoom.setPhoto(new SerialBlob(file.getBytes()));
            return roomRepository.save(oldRoom);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
