package com.example.HotelManagementSystem.controller.customer;

import com.example.HotelManagementSystem.Dto.ReservationDto;
import com.example.HotelManagementSystem.Dto.ReservationResponseDto;
import com.example.HotelManagementSystem.service.customer.Booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    @PostMapping("/book")

    public ResponseEntity<?> postBooking(@RequestBody ReservationDto reservationDto)
    {
        boolean success=bookingService.postReservation(reservationDto);
        if(success)
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/booking/{userId}/{pageNumber}")
    public ResponseEntity<?> getALlBookingByUserId(@PathVariable Long userId,@PathVariable int pageNumber)
    {
        try{
            return ResponseEntity.ok(bookingService.getALlReservationByUserId(userId,pageNumber));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
