package com.example.backend.repo;

import com.example.backend.entity.Bus;
import com.example.backend.entity.BusType;
import com.example.backend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    Optional<Bus> findByBusNumber(String busNumber);

    List<Bus> findByStatus(Status status);

    List<Bus> findByBusType(BusType busType);

    @Query("SELECT b FROM Bus b WHERE b.status = 'ACTIVE'")
    List<Bus> findAllActiveBuses();

    @Query("SELECT b FROM Bus b WHERE b.totalSeats >= :minSeats")
    List<Bus> findBusesByMinimumSeats(@Param("minSeats") Integer minSeats);

    boolean existsByBusNumber(String busNumber);
}