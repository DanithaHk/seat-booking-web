package com.example.backend.controller;

import com.example.backend.dto.BookingRequestDTO;
import com.example.backend.dto.ScheduleDTO;
import com.example.backend.dto.SeatDTO;
import com.example.backend.service.BookingService;
import com.example.backend.service.ScheduleService;
import com.example.backend.service.SeatService;
import com.example.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Public endpoints - no authentication required
 * Guest users can search schedules, view seats, and create bookings
 */
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingService bookingService;

    /**
     * Get all schedules
     * GET /api/public/schedules
     */
    @GetMapping("/schedules")
    public ResponseEntity<Map<String, Object>> getAllSchedules() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
            response.put("success", true);
            response.put("data", schedules);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get schedule by ID
     * GET /api/public/schedules/{id}
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<Map<String, Object>> getScheduleById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            ScheduleDTO schedule = scheduleService.getScheduleById(id);
            response.put("success", true);
            response.put("data", schedule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Schedule not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get available seats for a schedule
     * GET /api/public/schedules/{scheduleId}/seats/available
     */
    @GetMapping("/schedules/{scheduleId}/seats/available")
    public ResponseEntity<Map<String, Object>> getAvailableSeats(@PathVariable Integer scheduleId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<SeatDTO> seats = seatService.getAvailableSeatsBySchedule(scheduleId);
            Long availableCount = seatService.countAvailableSeats(scheduleId);
            
            response.put("success", true);
            response.put("data", seats);
            response.put("availableCount", availableCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all seats for a schedule (including booked ones)
     * GET /api/public/schedules/{scheduleId}/seats
     */
    @GetMapping("/schedules/{scheduleId}/seats")
    public ResponseEntity<Map<String, Object>> getAllSeatsForSchedule(@PathVariable Integer scheduleId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<SeatDTO> seats = seatService.getSeatsBySchedule(scheduleId);
            response.put("success", true);
            response.put("data", seats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Create booking (for guest users without account)
     * POST /api/public/bookings
     */
    @PostMapping("/bookings")
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody BookingRequestDTO bookingRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = bookingService.createBooking(bookingRequest);
            
            if (result == VarList.Created) {
                response.put("success", true);
                response.put("message", "Booking created successfully");
                response.put("bookingDetails", bookingRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Booking failed. Seats may be unavailable.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}