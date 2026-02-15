package com.example.backend.dto;

import com.example.backend.entity.BusType;
import com.example.backend.entity.FuelType;
import com.example.backend.entity.Status;
import java.time.LocalDateTime;

public class BusDTO {

    private Integer id;
    private String busNumber;
    private String busModel;
    private FuelType fuelType;
    private BusType busType;
    private Integer totalSeats;
    private Status status;
    private LocalDateTime createdAt;

    // Constructors
    public BusDTO() {
    }

    public BusDTO(Integer id, String busNumber, String busModel, FuelType fuelType, 
                  BusType busType, Integer totalSeats, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.busNumber = busNumber;
        this.busModel = busModel;
        this.fuelType = fuelType;
        this.busType = busType;
        this.totalSeats = totalSeats;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public BusType getBusType() {
        return busType;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}