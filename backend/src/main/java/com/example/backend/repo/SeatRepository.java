package com.example.backend.repo;

import com.example.backend.entity.Seat;
import com.example.backend.entity.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    // Existing methods
    List<Seat> findByScheduleIdAndSeatStatus(Integer scheduleId, SeatStatus seatStatus);

    @Query("SELECT s FROM Seat s WHERE s.schedule.id = :scheduleId AND s.seatStatus = :seatStatus")
    List<Seat> findSeatsByScheduleAndStatus(@Param("scheduleId") Integer scheduleId,
                                            @Param("seatStatus") SeatStatus seatStatus);

    List<Seat> findByScheduleId(Integer scheduleId);
    List<Seat> findByBusId(Integer busId);
    List<Seat> findBySeatStatus(SeatStatus seatStatus);

    @Query("SELECT s FROM Seat s WHERE s.schedule.id = :scheduleId AND s.seatStatus = 'AVAILABLE'")
    List<Seat> findAvailableSeatsBySchedule(@Param("scheduleId") Integer scheduleId);

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.schedule.id = :scheduleId AND s.seatStatus = 'AVAILABLE'")
    Long countAvailableSeatsBySchedule(@Param("scheduleId") Integer scheduleId);

    // ✅ Add this method to replace the missing findByBusAndSchedule
    @Query("SELECT s FROM Seat s WHERE s.bus.id = :busId AND s.schedule.id = :scheduleId")
    List<Seat> findByBusIdAndScheduleId(@Param("busId") Integer busId, @Param("scheduleId") Integer scheduleId);
}
