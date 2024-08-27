package com.javaproject.rasahotel.services.paymentGateway;

import java.util.List;

import com.javaproject.rasahotel.dto.payment.PaymentResponseDto;

public interface PaymentGatewayService {

    PaymentResponseDto payment(List<String> listPay, String confirmationCode);

    void paymentSuccess(String bookingId);
}
