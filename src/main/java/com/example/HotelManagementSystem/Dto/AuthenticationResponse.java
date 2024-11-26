package com.example.HotelManagementSystem.Dto;

import com.example.HotelManagementSystem.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}
