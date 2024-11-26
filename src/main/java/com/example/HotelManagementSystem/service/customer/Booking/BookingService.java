package com.example.HotelManagementSystem.service.customer.Booking;

import com.example.HotelManagementSystem.Dto.ReservationDto;
import com.example.HotelManagementSystem.Dto.ReservationResponseDto;
import com.example.HotelManagementSystem.Entity.HotelUser;
import com.example.HotelManagementSystem.Entity.Reservation;
import com.example.HotelManagementSystem.Entity.Room;
import com.example.HotelManagementSystem.enums.ReservationStatus;
import com.example.HotelManagementSystem.repositary.Reservationrepo;
import com.example.HotelManagementSystem.repositary.RoomRepositary;
import com.example.HotelManagementSystem.repositary.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserRepo repo;
     private final RoomRepositary roomrepo;
     private final Reservationrepo reservationrepo;
     public boolean postReservation(ReservationDto reservationDto)
     {
         Optional<HotelUser> optionalHotelUser=repo.findById(Math.toIntExact(reservationDto.getUserId()));
         Optional<Room> optionalRoom=roomrepo.findById(reservationDto.getRoomId());
         if(optionalRoom.isPresent() && optionalHotelUser.isPresent())
         {
             Reservation reservation=new Reservation();
             reservation.setRoom(optionalRoom.get());
             reservation.setHotelUser(optionalHotelUser.get());
             reservation.setCheckInDate(reservationDto.getCheckInDate());
             reservation.setCheckOutDate(reservationDto.getCheckOutDate());
             reservation.setReservationStatus(ReservationStatus.PENDING);

             Long days= ChronoUnit.DAYS.between(reservationDto.getCheckInDate(),reservationDto.getCheckOutDate());
             reservation.setPrice(optionalRoom.get().getPrice()*days);
             reservationrepo.save(reservation);
             return true;
         }
         return false;
     }
     private final int SEARCH_RESEULT_PER_PAGE=4;
     public ReservationResponseDto getALlReservationByUserId(Long UserId,int pageNumber)
     {
         Pageable pageable= PageRequest.of(pageNumber,SEARCH_RESEULT_PER_PAGE);

         Page<Reservation> reservationPage=reservationrepo.findAllByUserId(pageable,UserId);

         ReservationResponseDto reservationResponseDto=new ReservationResponseDto();
         reservationResponseDto.setReservationDtos(reservationPage.stream().map(Reservation::getReservationDto)
                 .collect(Collectors.toList()));

         reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
         reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

         return reservationResponseDto;
     }

}
