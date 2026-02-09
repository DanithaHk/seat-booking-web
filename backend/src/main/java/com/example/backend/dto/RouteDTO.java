package com.example.backend.dto;

public class RouteDTO {

    private Integer routeId;
    private String startLocation;
    private String endLocation;
    private Double distanceKm;

    // Constructors
    public RouteDTO() {}

    public RouteDTO(Integer routeId, String startLocation, String endLocation, Double distanceKm) {
        this.routeId = routeId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distanceKm = distanceKm;
    }

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
}
