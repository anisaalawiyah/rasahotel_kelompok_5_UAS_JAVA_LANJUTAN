package com.javaproject.rasahotel.services;

import com.javaproject.rasahotel.entities.BookedRoom;

public interface EmailService {

    void sendSimpleMailMessage(String to, String subject, String text);

    void sendMailMessage(String to, String subject, String text);

    void sendResetPassword(String email, String oTP);

    void sendBookingConfirmation(BookedRoom dto);

    void sendBookingConfirmationSuccess(BookedRoom dto);

    void sendRequestDeleteAccount();

}
