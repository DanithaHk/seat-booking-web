package com.example.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {

    private Integer id;
    private String userName;
    private String contactNumber;
    private String email;
    private Integer scheduleId;
    private LocalDateTime bookingDate;
    private Double totalPrice;
    private List<Integer> seatIds;

    // Additional fields for response (optional - when you need full details)
    private String busNumber;
    private String startLocation;
    private String endLocation;
    private String departureTime;
    private String arrivalTime;
    private List<String> seatNumbers;

    // Constructors
    public BookingDTO() {
    }

    public BookingDTO(Integer id, String userName, String contactNumber, String email,
                      Integer scheduleId, LocalDateTime bookingDate,double totalPrice, List<Integer> seatIds) {
        this.id = id;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.scheduleId = scheduleId;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.seatIds = seatIds;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }
}