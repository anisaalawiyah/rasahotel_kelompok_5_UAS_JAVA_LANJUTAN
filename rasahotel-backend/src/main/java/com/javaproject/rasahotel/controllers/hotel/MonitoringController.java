package com.javaproject.rasahotel.controllers.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.constants.MessageConstant;
import com.javaproject.rasahotel.dto.GeneralResponse;
import com.javaproject.rasahotel.services.room.MonitoringService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/room")
@Tag(name = "Room")
@Slf4j
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/find-all")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findAll(@RequestParam(required = false) String name,
            @RequestParam(required = false) Long price,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sort) {

        try {
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(monitoringService.findAll(name, price, page, size, sort),
                            MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }
}