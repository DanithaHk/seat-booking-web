package com.example.backend.repo;

import com.example.backend.entity.Route;
import com.example.backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    // Find schedules by bus id
    List<Schedule> findByBusId(Integer busId);

    // Find schedules by route id
    List<Schedule> findByRouteId(Integer routeId);

    // Custom query: find schedule by bus and route
    @Query("SELECT s FROM Schedule s WHERE s.bus.id = :busId AND s.route.id = :routeId")
    List<Schedule> findByBusAndRoute(@Param("busId") Integer busId,
                                     @Param("routeId") Integer routeId);
}
