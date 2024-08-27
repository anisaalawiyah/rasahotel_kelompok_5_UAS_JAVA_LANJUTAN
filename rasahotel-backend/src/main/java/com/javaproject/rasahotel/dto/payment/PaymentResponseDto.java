package com.javaproject.rasahotel.dto.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDto {

    String clientKey;
    Object result;
    String transactionToken;
}
