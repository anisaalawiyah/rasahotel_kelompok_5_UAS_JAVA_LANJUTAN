package com.javaproject.rasahotel.dto.category;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SaleRequestDto {

    private String name;
    private Long price;
}
