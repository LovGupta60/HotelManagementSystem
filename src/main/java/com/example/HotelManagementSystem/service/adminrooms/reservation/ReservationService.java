package com.example.HotelManagementSystem.service.adminrooms.reservation;

import com.example.HotelManagementSystem.Dto.ReservationDto;
import com.example.HotelManagementSystem.Dto.ReservationResponseDto;
import com.example.HotelManagementSystem.Entity.Reservation;
import com.example.HotelManagementSystem.Entity.Room;
import com.example.HotelManagementSystem.enums.ReservationStatus;
import com.example.HotelManagementSystem.repositary.Reservationrepo;
import com.example.HotelManagementSystem.repositary.RoomRepositary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService{
    private final Reservationrepo repo;
    private final RoomRepositary roomrepo;
    public static final int SEARCH_RESEULT_PER_PAGE=4;
    public ReservationResponseDto getAllReservation(int pageNumber)
    {
        Pageable pageable= PageRequest.of(pageNumber,SEARCH_RESEULT_PER_PAGE);

        Page<Reservation> reservationPage=repo.findAll(pageable);

        ReservationResponseDto reservationResponseDto=new ReservationResponseDto();
        reservationResponseDto.setReservationDtos(reservationPage.stream().map(Reservation::getReservationDto)
                .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }
    public boolean changeReservationStatus(Long id,String status)
    {
        Optional<Reservation> optionalReservation=repo.findById(id);
        if(optionalReservation.isPresent())
        {
            Reservation existingReservation=optionalReservation.get();

            if(Objects.equals(status,"Approve"))
            {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            }
            else {
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }
            repo.save(existingReservation);
            Room existingRoom=existingReservation.getRoom();
            existingRoom.setAvailable(false);

            roomrepo.save(existingRoom);
            return true;
        }
        return false;
    }

}
