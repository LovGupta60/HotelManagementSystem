package com.example.HotelManagementSystem.Entity;

import com.example.HotelManagementSystem.Dto.ReservationDto;
import com.example.HotelManagementSystem.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long price;
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private HotelUser hotelUser;

    public ReservationDto getReservationDto()
    {
        ReservationDto reservationDto=new ReservationDto();

        reservationDto.setId(id);
        reservationDto.setPrice(price);
        reservationDto.setCheckInDate(checkInDate);
        reservationDto.setCheckOutDate(checkOutDate);
        reservationDto.setReservationStatus(reservationStatus);
        reservationDto.setUserId((long) hotelUser.getId());
        reservationDto.setUsername(hotelUser.getUsername());

        reservationDto.setRoomId(room.getId());
        reservationDto.setRoomName(room.getName());
        reservationDto.setRoomType(Long.valueOf(room.getType()));
          return reservationDto;
    }
}
