package com.example.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {
     // Find all BookingSeat entries by booking ID
    List<BookingSeat> findByBookingId(Integer bookingId);
}
