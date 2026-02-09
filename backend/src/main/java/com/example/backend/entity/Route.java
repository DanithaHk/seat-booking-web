package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "start_location", nullable = false, length = 100)
    private String startLocation;

    @Column(name = "end_location", nullable = false, length = 100)
    private String endLocation;

   @Column(name = "distance_km")
    private Double distanceKm;

    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

    // Constructors
    public Route() {}

    // Getters & Setters
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
}
