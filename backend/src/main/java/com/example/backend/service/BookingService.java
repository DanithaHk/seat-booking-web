package com.example.backend.service;

import com.example.backend.dto.BookingDTO;
import com.example.backend.dto.BookingRequestDTO;
import com.example.backend.entity.Booking;

import java.util.List;

public interface BookingService {
    
    /**
     * Create a new booking
     */
    int createBooking(BookingRequestDTO dto);
    
    /**
     * Get all bookings (admin only)
     */
    List<BookingDTO> getAllBookings();
    
    BookingDTO getBookingById(Integer bookingId);
    
    /**
     * Get bookings by user email
     */
    List<BookingDTO> getBookingsByEmail(String email);
    
    /**
     * Get bookings by schedule ID
     */
    List<BookingDTO> getBookingsBySchedule(Integer scheduleId);
    
    /**
     * Cancel/Delete booking
     */
    int cancelBooking(Integer id);
}