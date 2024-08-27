package com.javaproject.rasahotel.services.booking;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.dto.booking.BookingRequestDto;
import com.javaproject.rasahotel.entities.BookedRoom;
import com.javaproject.rasahotel.entities.Room;
import com.javaproject.rasahotel.entities.Users;
import com.javaproject.rasahotel.repositories.BookingRepository;
import com.javaproject.rasahotel.repositories.CustomerRepository;
import com.javaproject.rasahotel.repositories.RoomRepository;
import com.javaproject.rasahotel.repositories.UsersRepository;
import com.javaproject.rasahotel.services.EmailService;
import com.javaproject.rasahotel.services.room.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<BookedRoom> getAllBookings() {

        try {

            return bookingRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void cancelBooking(String bookingId) {

        try {
            BookedRoom booking = bookingRepository.findById(bookingId).orElse(null);

            if (booking == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Room not found");

            bookingRepository.delete(booking);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public BookedRoom saveBooking(String roomId, BookingRequestDto dto) {

        try {

            Room room = roomService.getRoomById(roomId);
            Users user = usersRepository.findByUsername(dto.getGuestEmail()).orElse(null);
            LocalDate now = LocalDate.now();

            if (user == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "User  not found");

            if (dto.getCheckOutDate().isBefore(dto.getCheckInDate()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Check-in date must come before check-out date");

            if (dto.getCheckInDate().isBefore(now) || dto.getCheckOutDate().isBefore(now))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date must be equals or after " + now);

            if (availableRoom(dto, bookingRepository.findAll(), room))
                return bookingRepository.save(addBooking(user, room, dto));
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "This room is not available for the selected dates");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Validasi Tanggal Booking
    private boolean availableRoom(BookingRequestDto dto, List<BookedRoom> existingBookings, Room room) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        isValidBookingDate(dto.getCheckInDate(), dto.getCheckOutDate()) &&
                        isValidBookingDate(existingBooking.getCheckInDate(), existingBooking.getCheckOutDate()) &&
                        isBookingOverlap(dto, existingBooking) && 
                        room.getName().equals(existingBooking.getRoom().getName()));
    }
    
    private boolean isBookingOverlap(BookingRequestDto dto, BookedRoom existingBooking) {
        LocalDate checkIn = dto.getCheckInDate();
        LocalDate checkOut = dto.getCheckOutDate();
        LocalDate existingCheckIn = existingBooking.getCheckInDate();
        LocalDate existingCheckOut = existingBooking.getCheckOutDate();
    
        return (checkOut.isEqual(existingCheckIn) || checkIn.isEqual(existingCheckOut) ||
                (checkIn.isAfter(existingCheckIn) && checkIn.isBefore(existingCheckOut)) ||
                (checkOut.isAfter(existingCheckIn) && checkOut.isBefore(existingCheckOut)) ||
                (checkIn.isEqual(existingCheckIn) && checkOut.isAfter(existingCheckOut)) ||
                (checkIn.isEqual(existingCheckIn) && checkOut.isBefore(existingCheckOut)) ||
                (checkIn.isBefore(existingCheckIn) && checkOut.isEqual(existingCheckOut)) ||
                (checkIn.isAfter(existingCheckIn) && checkOut.isEqual(existingCheckOut)) ||
                (checkIn.isEqual(existingCheckIn) && checkOut.isEqual(existingCheckOut)) ||
                (checkIn.isBefore(existingCheckIn) && checkOut.isAfter(existingCheckOut)) ||
                (checkIn.isAfter(existingCheckIn) && checkOut.isBefore(existingCheckOut)));
    }
    
    private boolean isValidBookingDate(LocalDate checkIn, LocalDate checkOut) {
        return checkIn != null && checkOut != null && checkIn.isBefore(checkOut);
    }
    
    // Menambah Data Booking
    public BookedRoom addBooking(Users user, Room room, BookingRequestDto dto) {

        BookedRoom booked = new BookedRoom();
        booked.setCheckInDate(dto.getCheckInDate());
        booked.setCheckOutDate(dto.getCheckOutDate());
        booked.setCustomer(customerRepository.findByEmail(dto.getGuestEmail()).orElse(null));
        booked.setRoom(room);
        booked.setUsers(user);
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booked.setBookingConfirmationCode(bookingCode);
        booked.setPaymentRequired(true);
        Long totalPayment = (dto.getCheckOutDate().getDayOfMonth() - dto.getCheckInDate().getDayOfMonth() <= 0 ? 1
                : dto.getCheckOutDate().getDayOfMonth() - dto.getCheckInDate().getDayOfMonth()) * room.getPrice();
        booked.setTotalPayment(totalPayment);

        emailService.sendBookingConfirmation(booked);

        return booked;
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {

        try {

            BookedRoom booked = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                    .orElse(null);

            if (booked == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data booking found");

            return booked;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    
    }

    @Override
    public List<BookedRoom> findBookingByEmail(String email) {

        try {

            List<BookedRoom> booked = bookingRepository.findAll().stream()
                    .filter(data -> data.getCustomer().getEmail().equalsIgnoreCase(email)).toList();

            if (booked == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data booking found");

            return booked;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data booking found");
        }

    }

}
