package com.example.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO for creating a new booking
 */
public class BookingRequestDTO {

    private Long scheduleId;
    private String userName;
    private String contactNumber;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    private List<Integer> seatIds;
    private Double totalPrice;
    // Constructors
    public BookingRequestDTO() {
    }

    public BookingRequestDTO(Long scheduleId, String userName, String contactNumber, 
                             String email, LocalDate date, LocalTime time, List<Integer> seatIds , Double totalPrice) {
        this.scheduleId = scheduleId;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.date = date;
        this.time = time;
        this.seatIds = seatIds;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}