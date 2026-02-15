package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.entity.Bus;
import com.example.backend.entity.Status;
import com.example.backend.service.*;

import com.example.backend.util.VarList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for admin operations (ROLE_ADMIN)
 * Requires ADMIN role
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BusService busService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ModelMapper modelMapper;

    // ==================== USER MANAGEMENT ====================

    /**
     * Get all users
     * GET /api/admin/users
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<UserDTO> users = userService.getAll();
            response.put("success", true);
            response.put("data", users);
            response.put("count", users.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get user by email
     * GET /api/admin/users/{email}
     */
    @GetMapping("/users/{email}")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserDTO user = userService.findByEmail(email);
            response.put("success", true);
            response.put("data", user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Update user role
     * PUT /api/admin/users/{email}/role
     */
    @PutMapping("/users/{email}/role")
    public ResponseEntity<Map<String, Object>> updateUserRole(
            @PathVariable String email,
            @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String newRole = body.get("role");
            userService.updateUserRole(email, newRole);
            
            response.put("success", true);
            response.put("message", "User role updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Delete user
     * DELETE /api/admin/users/{id}
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Long userId = Long.valueOf(id);
            userService.deleteUser(userId);
            response.put("success", true);
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== BUS MANAGEMENT ====================

    /**
     * Create bus
     * POST /api/admin/buses
     */
    @PostMapping("/buses")
    public ResponseEntity<Map<String, Object>> createBus(@RequestBody BusDTO busDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = busService.saveBus(busDTO);
            
            if (result == VarList.Created) {
                response.put("success", true);
                response.put("message", "Bus created successfully");
                response.put("data", busDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Bus already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all buses
     * GET /api/admin/buses
     */
    @GetMapping("/buses")
    public ResponseEntity<Map<String, Object>> getAllBuses() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<BusDTO> buses = busService.getAllBuses();
            response.put("success", true);
            response.put("data", buses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get bus by number
     * GET /api/admin/buses/{busNumber}
     */
    @GetMapping("/buses/{busNumber}")
    public ResponseEntity<Map<String, Object>> getBusByNumber(@PathVariable String busNumber) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Bus busEntity = busService.findByBusNumber(busNumber);
            BusDTO busDTO = modelMapper.map(busEntity, BusDTO.class);
            response.put("data", busDTO);
            response.put("success", true);
            response.put("data", busDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Bus not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Update bus status
     * PUT /api/admin/buses/{busNumber}/status
     */
    @PutMapping("/buses/{busNumber}/status")
    public ResponseEntity<Map<String, Object>> updateBusStatus(
            @PathVariable String busNumber,
            @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Status status = Status.valueOf(body.get("status"));
            int result = busService.updateBusStatus(busNumber, status);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Bus status updated successfully");
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
     * Delete bus
     * DELETE /api/admin/buses/{busNumber}
     */
    @DeleteMapping("/buses/{busNumber}")
    public ResponseEntity<Map<String, Object>> deleteBus(@PathVariable String busNumber) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = busService.deleteBusByBusNumber(busNumber);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Bus deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Delete failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== ROUTE MANAGEMENT ====================

    /**
     * Create route
     * POST /api/admin/routes
     */
    @PostMapping("/routes")
    public ResponseEntity<Map<String, Object>> createRoute(@RequestBody RouteDTO routeDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = routeService.createRoute(routeDTO);
            
            if (result == VarList.Created) {
                response.put("success", true);
                response.put("message", "Route created successfully");
                response.put("data", routeDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else if (result == VarList.Not_Acceptable) {
                response.put("success", false);
                response.put("message", "Route already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Creation failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all routes
     * GET /api/admin/routes
     */
    @GetMapping("/routes")
    public ResponseEntity<Map<String, Object>> getAllRoutes() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<RouteDTO> routes = routeService.getAllRoutes();
            response.put("success", true);
            response.put("data", routes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get route by ID
     * GET /api/admin/routes/{id}
     */
    @GetMapping("/routes/{id}")
    public ResponseEntity<Map<String, Object>> getRouteById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            RouteDTO route = routeService.getRouteById(id);
            response.put("success", true);
            response.put("data", route);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Route not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Update route
     * PUT /api/admin/routes/{id}
     */
    @PutMapping("/routes/{id}")
    public ResponseEntity<Map<String, Object>> updateRoute(
            @PathVariable Integer id,
            @RequestBody RouteDTO routeDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = routeService.updateRoute(id, routeDTO);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Route updated successfully");
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
     * Delete route
     * DELETE /api/admin/routes/{id}
     */
    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Map<String, Object>> deleteRoute(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = routeService.deleteRouteById(id);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Route deleted successfully");
                return ResponseEntity.ok(response);
            } else if (result == VarList.Not_Found) {
                response.put("success", false);
                response.put("message", "Route not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Delete failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== SCHEDULE MANAGEMENT ====================

    /**
     * Create schedule
     * POST /api/admin/schedules
     */
    @PostMapping("/schedules")
    public ResponseEntity<Map<String, Object>> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = scheduleService.createSchedule(scheduleDTO);
            
            if (result == VarList.Created) {
                response.put("success", true);
                response.put("message", "Schedule created successfully");
                response.put("data", scheduleDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else if (result == VarList.Not_Acceptable) {
                response.put("success", false);
                response.put("message", "Schedule already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Creation failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all schedules
     * GET /api/admin/schedules
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
     * GET /api/admin/schedules/{id}
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
     * Update schedule time
     * PUT /api/admin/schedules/{id}/time
     */
    @PutMapping("/schedules/{id}/time")
    public ResponseEntity<Map<String, Object>> updateScheduleTime(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            LocalTime arrivalTime = LocalTime.parse(body.get("arrivalTime"));
            LocalTime departureTime = LocalTime.parse(body.get("departureTime"));
            
            int result = scheduleService.editScheduleTimeById(id, arrivalTime, departureTime);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Schedule time updated successfully");
                return ResponseEntity.ok(response);
            } else if (result == VarList.Not_Found) {
                response.put("success", false);
                response.put("message", "Schedule not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
     * Delete schedule
     * DELETE /api/admin/schedules/{id}
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Map<String, Object>> deleteSchedule(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = scheduleService.deleteScheduleById(id);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Schedule deleted successfully");
                return ResponseEntity.ok(response);
            } else if (result == VarList.Not_Found) {
                response.put("success", false);
                response.put("message", "Schedule not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Delete failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== SEAT MANAGEMENT ====================

    /**
     * Create seat
     * POST /api/admin/seats
     */
    @PostMapping("/seats")
    public ResponseEntity<Map<String, Object>> createSeat(@RequestBody SeatDTO seatDTO) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = seatService.createSeat(seatDTO);
            
            if (result == VarList.Created) {
                response.put("success", true);
                response.put("message", "Seat created successfully");
                response.put("data", seatDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Creation failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all seats
     * GET /api/admin/seats
     */
    @GetMapping("/seats")
    public ResponseEntity<Map<String, Object>> getAllSeats() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<SeatDTO> seats = seatService.getAllSeats();
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
     * Get seats by bus
     * GET /api/admin/buses/{busId}/seats
     */
    @GetMapping("/buses/{busId}/seats")
    public ResponseEntity<Map<String, Object>> getSeatsByBus(@PathVariable Integer busId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<SeatDTO> seats = seatService.getSeatsByBus(busId);
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
     * Delete seat
     * DELETE /api/admin/seats/{id}
     */
    @DeleteMapping("/seats/{id}")
    public ResponseEntity<Map<String, Object>> deleteSeat(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = seatService.deleteSeat(id);
            
            if (result == VarList.OK) {
                response.put("success", true);
                response.put("message", "Seat deleted successfully");
                return ResponseEntity.ok(response);
            } else if (result == VarList.Not_Found) {
                response.put("success", false);
                response.put("message", "Seat not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                response.put("success", false);
                response.put("message", "Delete failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== BOOKING MANAGEMENT ====================

    /**
     * Get all bookings
     * GET /api/admin/bookings
     */
    @GetMapping("/bookings")
    public ResponseEntity<Map<String, Object>> getAllBookings() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
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
     * Get booking by ID
     * GET /api/admin/bookings/{id}
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Map<String, Object>> getBookingById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            BookingDTO booking = bookingService.getBookingById(id);
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
     * Get bookings by schedule
     * GET /api/admin/schedules/{scheduleId}/bookings
     */
    @GetMapping("/schedules/{scheduleId}/bookings")
    public ResponseEntity<Map<String, Object>> getBookingsBySchedule(@PathVariable Integer scheduleId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<BookingDTO> bookings = bookingService.getBookingsBySchedule(scheduleId);
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
     * Cancel/Delete booking
     * DELETE /api/admin/bookings/{id}
     */
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Map<String, Object>> cancelBooking(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
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