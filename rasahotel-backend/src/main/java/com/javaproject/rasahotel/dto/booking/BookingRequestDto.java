package com.javaproject.rasahotel.dto.booking;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingRequestDto {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String guestName;

    private String guestEmail;

}
