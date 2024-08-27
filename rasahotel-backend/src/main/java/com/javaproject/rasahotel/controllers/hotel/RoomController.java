package com.javaproject.rasahotel.controllers.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.constants.MessageConstant;
import com.javaproject.rasahotel.dto.GeneralResponse;
import com.javaproject.rasahotel.dto.room.RoomRequestDto;
import com.javaproject.rasahotel.services.room.RoomService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/api/room")
@Tag(name = "Room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all-room")
    ResponseEntity<Object> getRooms() {
        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(roomService.getAll(), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get-room")
    ResponseEntity<Object> getRoom(@RequestParam String idRoom) {
        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(roomService.getRoomById(idRoom), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/add-room")
    ResponseEntity<Object> addRoom(@RequestBody RoomRequestDto dto) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(roomService.add(dto), MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/update-room/{idRoom}")
    ResponseEntity<Object> updateRoom(@PathVariable String idRoom, @RequestBody RoomRequestDto dto) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(roomService.update(idRoom, dto), MessageConstant.OK_PUT_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/update-room-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Object> updatePhotoRoom(@RequestParam String idRoom, @RequestParam MultipartFile file) {

        try {

            roomService.uploadPhoto(idRoom, file);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(null, MessageConstant.OK_PUT_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete-room")
    ResponseEntity<Object> deleteRoom(@RequestParam String idRoom) {

        try {
            roomService.delete(idRoom);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(null, MessageConstant.OK_DELETE_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

}
