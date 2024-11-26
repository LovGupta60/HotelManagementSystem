package com.example.HotelManagementSystem.controller.admin;

import com.example.HotelManagementSystem.service.adminrooms.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @GetMapping("/reservations/{pageNumber}")
    private ResponseEntity<?> getAllReservations(@PathVariable int pageNumber)
    {
        try{
            return ResponseEntity.ok(reservationService.getAllReservation(pageNumber));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    public ResponseEntity<?> changeReservationStatus(@PathVariable Long id,@PathVariable String status)
    {
        boolean success= reservationService.changeReservationStatus(id,status);
        if(success)
        {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

}
