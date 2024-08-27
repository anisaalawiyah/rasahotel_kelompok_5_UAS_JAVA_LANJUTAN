package com.javaproject.rasahotel.dto.booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequestDto {

    String confirmationCode;
    String paymentCode;
    String email;
}
