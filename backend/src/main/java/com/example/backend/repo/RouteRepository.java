package com.example.backend.repo;

import com.example.backend.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByStartLocation(String startLocation);

    List<Route> findByEndLocation(String endLocation);

    Optional<Route> findByStartLocationAndEndLocation(String startLocation, String endLocation);

    @Query("SELECT r FROM Route r WHERE r.startLocation = :start AND r.endLocation = :end")
    List<Route> findRoutesBetweenLocations(@Param("start") String startLocation, 
                                           @Param("end") String endLocation);

    @Query("SELECT r FROM Route r WHERE r.distanceKm <= :maxDistance")
    List<Route> findRoutesByMaxDistance(@Param("maxDistance") Double maxDistance);

    @Query("SELECT DISTINCT r.startLocation FROM Route r ORDER BY r.startLocation")
    List<String> findAllStartLocations();

    @Query("SELECT DISTINCT r.endLocation FROM Route r ORDER BY r.endLocation")
    List<String> findAllEndLocations();
}