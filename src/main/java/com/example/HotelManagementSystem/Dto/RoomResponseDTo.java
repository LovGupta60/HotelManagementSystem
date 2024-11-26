package com.example.HotelManagementSystem.Dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDTo {
    private List<RoomDto> roomDtoList;
    private Integer totalPages;
    private Integer pageNumber;
}
