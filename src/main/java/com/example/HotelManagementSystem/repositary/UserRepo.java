package com.example.HotelManagementSystem.repositary;

import com.example.HotelManagementSystem.Entity.HotelUser;
import com.example.HotelManagementSystem.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<HotelUser,Integer> {
    Optional<HotelUser> findFirstByEmail(String email);
    Optional<HotelUser> findByUserRole(UserRole userRole);
}
