package com.example.HotelManagementSystem.Dto;

import com.example.HotelManagementSystem.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String email;
    private String username;
    private UserRole userRole;
}
