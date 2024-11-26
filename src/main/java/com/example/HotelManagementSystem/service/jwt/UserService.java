package com.example.HotelManagementSystem.service.jwt;

import com.example.HotelManagementSystem.repositary.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return repo.findFirstByEmail(username).orElseThrow(() ->new UsernameNotFoundException("not found"));
            }
        };
    }
}
