package com.example.HotelManagementSystem.service;

import com.example.HotelManagementSystem.Dto.SignupRequest;
import com.example.HotelManagementSystem.Dto.UserDto;
import com.example.HotelManagementSystem.Entity.HotelUser;
import com.example.HotelManagementSystem.enums.UserRole;
import com.example.HotelManagementSystem.repositary.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {
    @Autowired
    private UserRepo repo;
    @PostConstruct
    public void createUserAccount()
    {
        Optional<HotelUser> adminaccount=repo.findByUserRole(UserRole.ADMIN);
        if(adminaccount.isEmpty())
        {
            HotelUser user=new HotelUser();
            user.setEmail("scdcd");
            user.setName("Lov");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            repo.save(user);
            System.out.println("Admin account created");
        }
        else {
            System.out.println("Admin account exist");
        }
    }
    public UserDto createUser(SignupRequest signupRequest)
    {
        if(repo.findFirstByEmail(signupRequest.getEmail()).isPresent())
        {
            throw new EntityExistsException("present");
        }
        HotelUser user= new HotelUser();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        HotelUser createduser=repo.save(user);
        return createduser.getUserDto();
    }
}
