package com.example.HotelManagementSystem.controller.admin;

import com.example.HotelManagementSystem.Dto.RoomDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.RsaAlgorithm;
import org.springframework.web.bind.annotation.*;
import com.example.HotelManagementSystem.service.adminrooms.roomservice;


@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class RoomController {

    private final roomservice roomservice;
    @PostMapping("/room")
    public ResponseEntity<?> postRoom(@RequestBody RoomDto roomDto)
    {
        boolean success=roomservice.postRoom(roomDto);
        if(success==true)
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAllRoom(@PathVariable int pageNumber){
        return ResponseEntity.ok(roomservice.getAllRooms(pageNumber));
    }
    @GetMapping("/room/{Id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long Id)
    {
        try {
            return ResponseEntity.ok(roomservice.getRoomById(Id));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something gets wrong");
        }
    }
    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id,@RequestBody RoomDto roomDto)
    {
        boolean success=roomservice.updateRoom(id,roomDto);
        if(success)
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id)
    {
        try {
            roomservice.deleteRoom(id);
            return ResponseEntity.ok(null);
        }catch(EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
