package com.javaproject.rasahotel.controllers.hotel;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.constants.MessageConstant;
import com.javaproject.rasahotel.dto.GeneralResponse;
import com.javaproject.rasahotel.dto.booking.BookingRequestDto;
import com.javaproject.rasahotel.services.booking.BookingService;
import com.javaproject.rasahotel.services.paymentGateway.PaymentGatewayService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/api/booking")
@Tag(name = "Booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    PaymentGatewayService paymentService;

    @GetMapping("/all-bookings")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getAllBookings() {
        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(bookingService.getAllBookings(), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/save-booking/{roomId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> saveBooking(@PathVariable String roomId,
            @RequestBody BookingRequestDto dto) {
        try {
            return ResponseEntity.ok().body(GeneralResponse.success(bookingService.saveBooking(roomId, dto),
                    MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/confirmation/{confirmationCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            return ResponseEntity.ok().body(GeneralResponse.success(
                    bookingService.findByBookingConfirmationCode(confirmationCode), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/cancel-booking/{bookingId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> cancelBooking(@PathVariable String bookingId) {
        try {

            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok().body(
                    GeneralResponse.success(null, MessageConstant.OK_DELETE_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/get-by-email")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Object> getByEmail(@RequestParam String email) {

        try {

            return ResponseEntity.ok().body(
                    GeneralResponse.success(bookingService.findBookingByEmail(email), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/payment")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> payment(@RequestParam(value = "enablePay", required = false) List<String> listPay, @RequestParam String confirmationCode) {

        try {

            return ResponseEntity.ok().body(GeneralResponse.success(
                    paymentService.payment(listPay, confirmationCode), MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/paymentSuccess")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> paymentSuccess(@RequestParam String bookingId) {

        try {

            paymentService.paymentSuccess(bookingId);
            return ResponseEntity.ok().body(GeneralResponse.success(
                    null, MessageConstant.OK_POST_DATA));
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