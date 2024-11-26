package com.example.HotelManagementSystem.Dto;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
public class RoomDto {
    private Long id;

    private String name;
    private String type;
    private Long price;
    private Boolean available;
}
