package com.example.backend.dto;

import com.example.backend.entity.SeatStatus;
import java.time.LocalDateTime;

public class SeatDTO {

    private Integer id;
    private Integer busId;
    private Integer scheduleId;
    private String seatNumber;
    private SeatStatus seatStatus;
    private LocalDateTime bookingDate;

    // Additional fields for response (optional - when you need full details)
    private String busNumber;
    private String startLocation;
    private String endLocation;

    // Constructors
    public SeatDTO() {
    }

    public SeatDTO(Integer id, Integer busId, Integer scheduleId, String seatNumber, 
                   SeatStatus seatStatus, LocalDateTime bookingDate) {
        this.id = id;
        this.busId = busId;
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
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
}