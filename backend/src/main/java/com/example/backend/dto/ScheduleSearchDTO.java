package com.example.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for searching schedules
 */
public class ScheduleSearchDTO {

    private String startLocation;
    private String endLocation;
    private LocalDate travelDate;
    private LocalTime departureTimeFrom;
    private LocalTime departureTimeTo;
    private Integer minSeatsAvailable;

    // Constructors
    public ScheduleSearchDTO() {
    }

    public ScheduleSearchDTO(String startLocation, String endLocation, LocalDate travelDate) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.travelDate = travelDate;
    }

    // Getters and Setters
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

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public LocalTime getDepartureTimeFrom() {
        return departureTimeFrom;
    }

    public void setDepartureTimeFrom(LocalTime departureTimeFrom) {
        this.departureTimeFrom = departureTimeFrom;
    }

    public LocalTime getDepartureTimeTo() {
        return departureTimeTo;
    }

    public void setDepartureTimeTo(LocalTime departureTimeTo) {
        this.departureTimeTo = departureTimeTo;
    }

    public Integer getMinSeatsAvailable() {
        return minSeatsAvailable;
    }

    public void setMinSeatsAvailable(Integer minSeatsAvailable) {
        this.minSeatsAvailable = minSeatsAvailable;
    }
}