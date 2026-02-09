package com.example.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Route;
@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    boolean existsByStartLocationAndEndLocation(String startLocation, String endLocation);
}
