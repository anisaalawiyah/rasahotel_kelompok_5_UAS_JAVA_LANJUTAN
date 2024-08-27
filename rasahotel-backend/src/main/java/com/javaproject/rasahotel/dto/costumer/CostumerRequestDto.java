package com.javaproject.rasahotel.dto.costumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostumerRequestDto {

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private String password;
}
