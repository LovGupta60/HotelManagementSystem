package com.example.HotelManagementSystem.service.customer;

import com.example.HotelManagementSystem.Dto.RoomResponseDTo;
import com.example.HotelManagementSystem.Entity.Room;
import com.example.HotelManagementSystem.repositary.RoomRepositary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomserviceCustomer {

    private final RoomRepositary repo;
    public RoomResponseDTo getAvailableRooms(int pageNumber)
    {
        Pageable pageable= PageRequest.of(pageNumber,6);
        Page<Room> roomPage=repo.findByAvailable(true,pageable);

        RoomResponseDTo roomResponseDTo=new RoomResponseDTo();
        roomResponseDTo.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDTo.setTotalPages((roomPage.getTotalPages()));
        roomResponseDTo.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomResponseDTo;
    }
}
