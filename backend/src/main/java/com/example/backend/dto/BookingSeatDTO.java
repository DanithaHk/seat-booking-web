package com.example.backend.dto;

public class BookingSeatDTO {

    private Integer id;
    private Integer bookingId;
    private Integer seatId;
    
    // Additional fields for response
    private String seatNumber;

    // Constructors
    public BookingSeatDTO() {
    }

    public BookingSeatDTO(Integer id, Integer bookingId, Integer seatId) {
        this.id = id;
        this.bookingId = bookingId;
        this.seatId = seatId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}