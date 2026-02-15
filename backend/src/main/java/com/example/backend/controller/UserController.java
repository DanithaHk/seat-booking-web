package com.example.backend.controller;

import com.example.backend.dto.BookingDTO;
import com.example.backend.dto.BookingRequestDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.service.BookingService;
import com.example.backend.service.UserService;
import com.example.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for logged-in users (ROLE_USER)
 * Requires authentication
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    /**
     * Get current logged-in user profile
     * GET /api/user/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = authentication.getName();
            UserDTO user = userService.findByEmail(email);
            
            response.put("success", true);
            response.put("data", user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Update user profile
     * PUT /api/user/profile
     */
    @PutMapping("/profile")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @RequestBody UserDTO userDTO,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = authentication.getName();
            UserDTO currentUser = userService.findByEmail(email);
            
            if (currentUser == null) {
                response.put("success", false);
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Update allowed fields
            currentUser.setFullName(userDTO.getFullName());
            currentUser.setPhone(userDTO.getPhone());
            
            int result = userService.saveUser(currentUser);
            
            if (result == VarList.Created || result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Profile updated successfully");
                response.put("data", userService.findByEmail(email));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Update failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Create booking for logged-in user
     * POST /api/user/bookings
     */
    @PostMapping("/bookings")
    public ResponseEntity<Map<String, Object>> createBooking(
            @RequestBody BookingRequestDTO bookingRequest,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = authentication.getName();
            UserDTO user = userService.findByEmail(email);
            
            // Set user details from logged-in user
            bookingRequest.setUserName(user.getFullName());
            bookingRequest.setEmail(user.getEmail());
            bookingRequest.setContactNumber(user.getPhone());
            
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

    /**
     * Get all bookings for logged-in user
     * GET /api/user/bookings
     */
    @GetMapping("/bookings")
    public ResponseEntity<Map<String, Object>> getMyBookings(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = authentication.getName();
            List<BookingDTO> bookings = bookingService.getBookingsByEmail(email);
            
            response.put("success", true);
            response.put("data", bookings);
            response.put("count", bookings.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get specific booking by ID (only if it belongs to logged-in user)
     * GET /api/user/bookings/{id}
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Map<String, Object>> getBookingById(
            @PathVariable Integer id,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = authentication.getName();
            BookingDTO booking = bookingService.getBookingById(id);
            
            // Check if booking belongs to logged-in user
            if (!booking.getEmail().equals(email)) {
                response.put("success", false);
                response.put("message", "Access denied");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            response.put("success", true);
            response.put("data", booking);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Booking not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Cancel booking (only if it belongs to logged-in user)
     * DELETE /api/user/bookings/{id}
     */
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Map<String, Object>> cancelBooking(
            @PathVariable Integer id,
            Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String email = authentication.getName();
            BookingDTO booking = bookingService.getBookingById(id);
            
            // Check if booking belongs to logged-in user
            if (!booking.getEmail().equals(email)) {
                response.put("success", false);
                response.put("message", "Access denied");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            int result = bookingService.cancelBooking(id);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Booking cancelled successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Cancellation failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}