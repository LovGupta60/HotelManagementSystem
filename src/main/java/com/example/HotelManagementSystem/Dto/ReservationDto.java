package com.example.HotelManagementSystem.Dto;

import com.example.HotelManagementSystem.Entity.HotelUser;
import com.example.HotelManagementSystem.enums.ReservationStatus;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ReservationDto {
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long price;
    private ReservationStatus reservationStatus;
    private Long roomId;
    private Long roomType;
    private String roomName;
    private Long userId;
    private String username;

}
