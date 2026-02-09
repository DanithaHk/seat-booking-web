package com.example.backend.repo;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsByBus_BusIdAndRoute_RouteIdAndDepartureTime(
            Integer busId,
            Integer routeId,
            LocalTime departureTime
    );


}
