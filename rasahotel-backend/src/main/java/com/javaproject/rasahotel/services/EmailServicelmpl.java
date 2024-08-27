package com.javaproject.rasahotel.services;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.entities.BookedRoom;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicelmpl implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    ThymeleafService thymeleafService;

    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    private final String ADMIN_EMAIL = "admin@rasahotel.com";
    private final String ADMIN_PERSONAL = "Admin Rasa Hotel";

    @Override
    public void sendMailMessage(String to, String subject, String text) {

        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(to);
            message.setSubject(subject);

            Map<String, Object> variables = new HashMap<>();

            variables.put("mail", text);

            message.setText(thymeleafService.createContext("inline/send-mail.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendResetPassword(String email, String OTP) {
        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(email);
            message.setSubject("RESET PASSWORD | RASA HOTEL");

            Map<String, Object> variables = new HashMap<>();

            variables.put("otp", OTP);

            message.setText(thymeleafService.createContext("inline/send-reset-mail.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, me.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void sendBookingConfirmation(BookedRoom dto) {

        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(dto.getCustomer().getEmail());
            message.setSubject("Booking Confirmation | RASA HOTEL");

            Map<String, Object> variables = new HashMap<>();

            // Room
            variables.put("roomName", dto.getRoom().getName());
            variables.put("roomCategory", dto.getRoom().getCategory().getName());
            variables.put("roomPrice", formatter.format(dto.getRoom().getPrice()));

            // Booking Info
            variables.put("name", dto.getCustomer().getName());
            variables.put("email", dto.getCustomer().getEmail());
            variables.put("confirmationCode", dto.getBookingConfirmationCode());
            variables.put("checkIn", dto.getCheckInDate());
            variables.put("checkOut", dto.getCheckOutDate());
            variables.put("dayLong", (dto.getCheckOutDate().getDayOfMonth() - dto.getCheckInDate().getDayOfMonth()));
            variables.put("totalPayment", formatter.format(dto.getTotalPayment()));
            variables.put("paymentStatus", dto.isPaymentRequired() ? "Pending" : "Success");

            message.setText(thymeleafService.createContext("inline/send-booking-mail.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, me.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void sendBookingConfirmationSuccess(BookedRoom dto) {
        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo(dto.getCustomer().getEmail());
            message.setSubject("Booking Payment Invoice | RASA HOTEL");

            Map<String, Object> variables = new HashMap<>();

            // Room
            variables.put("roomName", dto.getRoom().getName());
            variables.put("roomCategory", dto.getRoom().getCategory().getName());
            variables.put("roomPrice", formatter.format(dto.getRoom().getPrice()));

            // Booking Info
            variables.put("name", dto.getCustomer().getName());
            variables.put("email", dto.getCustomer().getEmail());
            variables.put("checkIn", dto.getCheckInDate());
            variables.put("checkOut", dto.getCheckOutDate());
            variables.put("dayLong", (dto.getCheckOutDate().getDayOfMonth() - dto.getCheckInDate().getDayOfMonth()));
            variables.put("totalPayment", formatter.format(dto.getTotalPayment()));
            variables.put("paymentStatus", dto.isPaymentRequired() ? "Pending" : "Success");

            message.setText(thymeleafService.createContext("inline/send-booking-mail-success.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, me.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void sendRequestDeleteAccount() {
        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            message.setFrom(ADMIN_EMAIL, ADMIN_PERSONAL);
            message.setTo("hidayatsalim004@gmail.com");
            message.setSubject("Account Deletion Request | Rasa Hotel");

            Map<String, Object> variables = new HashMap<>();

            variables.put("email", "hidayatsalim004@gmail");

            message.setText(thymeleafService.createContext("send-delete-confirmation.html", variables), true);
            emailSender.send(mimeMessage);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSimpleMailMessage(String to, String subject, String text) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@rasahotel.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
