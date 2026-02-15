package com.example.backend.dto;

import java.time.LocalDateTime;

public class RouteDTO {

    private Integer id;
    private String startLocation;
    private String endLocation;
    private Double distanceKm;
    private LocalDateTime createdAt;

    // Constructors
    public RouteDTO() {
    }

    public RouteDTO(Integer id, String startLocation, String endLocation, 
                    Double distanceKm, LocalDateTime createdAt) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distanceKm = distanceKm;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}