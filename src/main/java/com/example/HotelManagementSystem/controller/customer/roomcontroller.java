package com.example.HotelManagementSystem.controller.customer;

import com.example.HotelManagementSystem.service.customer.RoomserviceCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class roomcontroller {

    private final RoomserviceCustomer s;
    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAvailableRooms(@PathVariable int pageNumber)
    {
        return ResponseEntity.ok(s.getAvailableRooms(pageNumber));
    }
}
