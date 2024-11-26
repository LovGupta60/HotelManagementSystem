package com.example.HotelManagementSystem.service.adminrooms;

import com.example.HotelManagementSystem.Dto.RoomDto;
import com.example.HotelManagementSystem.Dto.RoomResponseDTo;
import com.example.HotelManagementSystem.Entity.Room;
import com.example.HotelManagementSystem.repositary.RoomRepositary;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class roomservice {
    private final RoomRepositary repo;
    public boolean postRoom(RoomDto roomDto)
    {
        try{
            Room room=new Room();
            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);
            repo.save(room);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public RoomResponseDTo getAllRooms(int pageNumber)
    {
        Pageable pageable=PageRequest.of(pageNumber,6);
        Page<Room> roomPage=repo.findAll(pageable);

        RoomResponseDTo roomResponseDTo=new RoomResponseDTo();
        roomResponseDTo.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDTo.setTotalPages((roomPage.getTotalPages()));
        roomResponseDTo.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomResponseDTo;
    }
    public RoomDto getRoomById(Long Id)
    {
        Optional<Room> optionalRoom=repo.findById(Id);
        if(optionalRoom.isPresent())
        {
            return optionalRoom.get().getRoomDto();
        }else {
            throw new EntityNotFoundException("Room not present");
        }
    }
    public boolean updateRoom(Long id,RoomDto roomDto)
    {
        Optional<Room> optionalRoom=repo.findById(id);
        if(optionalRoom.isPresent())
        {
            Room existingroom=optionalRoom.get();
            existingroom.setName(roomDto.getName());
            existingroom.setPrice(roomDto.getPrice());
            existingroom.setType(roomDto.getType());

            repo.save(existingroom);
            return true;
        }
        return false;
    }
    public void deleteRoom(Long id)
    {
        Optional<Room> optionalRoom=repo.findById(id);
        if(optionalRoom.isPresent())
        {
            repo.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("Room no present");
        }
    }
}
