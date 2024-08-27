package com.javaproject.rasahotel.services.paymentGateway;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.dto.payment.PaymentResponseDto;
import com.javaproject.rasahotel.entities.BookedRoom;
import com.javaproject.rasahotel.repositories.BookingRepository;
import com.javaproject.rasahotel.services.EmailService;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Override
    public PaymentResponseDto payment(List<String> listPay, String confirmationCode) {

        try {

            Midtrans.clientKey = "SB-Mid-client-uNNHC_g2RnVnYJQu";
            Midtrans.serverKey = "SB-Mid-server-Rb-zl0GVpmcfyI9TGal2s3l6";

            String clientKey = Midtrans.getClientKey();

            BookedRoom booked = bookingRepository.findAll().stream()
                    .filter(n -> confirmationCode.equals(n.getBookingConfirmationCode())).findFirst()
                    .orElse(null);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            Map<String, Object> transDetail = new HashMap<>();
            transDetail.put("order_id", "RASA_ID_" + timestamp.getTime());
            transDetail.put("gross_amount", booked.getTotalPayment());

            Map<String, Object> booking = new HashMap<>();
            booking.put("id", booked.getBookingId());
            booking.put("name", booked.getRoom().getName());
            booking.put("price", booked.getRoom().getPrice());
            booking.put("quantity", booked.getCheckOutDate().getDayOfMonth()-booked.getCheckInDate().getDayOfMonth());

            Map<String, Object> custDetail = new HashMap<>();
            custDetail.put("first_name", booked.getCustomer().getName());
            custDetail.put("email", booked.getCustomer().getEmail());
            custDetail.put("phone", booked.getCustomer().getPhoneNumber());
            custDetail.put("address", booked.getCustomer().getAddress());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("transaction_details", transDetail);
            requestBody.put("item_details", booking);
            requestBody.put("customer_details", custDetail);

            List<String> paymentList = new ArrayList<>();
            if (listPay != null) {
                paymentList.addAll(listPay);
            }

            Map<String, String> creditCard = new HashMap<>();
            creditCard.put("secure", "true");

            return PaymentResponseDto.builder()
                    .clientKey(clientKey)
                    .result(requestBody)
                    .transactionToken(SnapApi.createTransactionToken(requestBody)).build();
        } catch (MidtransError e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void paymentSuccess(String bookingId) {

        try {

            BookedRoom bookedRoom = bookingRepository.findById(bookingId).orElse(null);

            if (bookedRoom == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No data booking found");

            bookedRoom.setPaymentRequired(false);
            bookingRepository.save(bookedRoom);

            emailService.sendBookingConfirmationSuccess(bookedRoom);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
