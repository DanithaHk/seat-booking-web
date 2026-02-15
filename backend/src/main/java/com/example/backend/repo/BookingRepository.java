package com.example.backend.repo;

import com.example.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByEmail(String email);

    List<Booking> findByScheduleId(Integer scheduleId);

    List<Booking> findByContactNumber(String contactNumber);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findBookingsBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.schedule.id = :scheduleId ORDER BY b.bookingDate DESC")
    List<Booking> findBookingsByScheduleOrderByDate(@Param("scheduleId") Integer scheduleId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.schedule.id = :scheduleId")
    Long countBookingsBySchedule(@Param("scheduleId") Integer scheduleId);
}