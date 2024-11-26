package com.example.HotelManagementSystem.repositary;

import com.example.HotelManagementSystem.Entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface Reservationrepo extends JpaRepository<Reservation,Long> {
    Page<Reservation> findAllByUserId(Pageable pageable, Long Id);
}
