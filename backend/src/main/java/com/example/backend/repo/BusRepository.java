package com.example.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {

   boolean existsByBusNumber(String busNumber);
   // find by bus number
   Bus findByBusNumber(String busNumber);

}
    