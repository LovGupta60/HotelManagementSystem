package com.example.HotelManagementSystem.controller;

import com.example.HotelManagementSystem.Dto.AuthenticationResponse;
import com.example.HotelManagementSystem.Dto.Authenticationrequest;
import com.example.HotelManagementSystem.Dto.SignupRequest;
import com.example.HotelManagementSystem.Dto.UserDto;
import com.example.HotelManagementSystem.Entity.HotelUser;
import com.example.HotelManagementSystem.repositary.UserRepo;
import com.example.HotelManagementSystem.service.AuthService;
import com.example.HotelManagementSystem.service.jwt.UserService;
import com.example.HotelManagementSystem.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo repo;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest)
    {
        try{
            UserDto userDto=service.createUser(signupRequest);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("User already exist",HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody Authenticationrequest authenticationRequest)
    {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }
        catch(BadCredentialsException e)
        {
            throw new BadCredentialsException("Incorrect");
        }
        final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<HotelUser>optionalHotelUser=repo.findFirstByEmail(userDetails.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(optionalHotelUser.isPresent())
        {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalHotelUser.get().getUserRole());
            authenticationResponse.setUserId((long) optionalHotelUser.get().getId());
        }
        return authenticationResponse;

    }
}
