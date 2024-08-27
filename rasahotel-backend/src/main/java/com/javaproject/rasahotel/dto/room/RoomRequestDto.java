package com.javaproject.rasahotel.dto.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomRequestDto {
    
    private String name;
    private String description;
    private Long price;
    private String categoryId;
}
