package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.SeatDTO;
import com.example.backend.entity.SeatStatus;

public interface SeatService {

    public int createSeat(SeatDTO seatDTO);
     public List<SeatDTO> getSeatsBySchedule(Integer scheduleId);
     public List<SeatDTO> getSeatsByBus(Integer busId);
     public List<SeatDTO> getAvailableSeatsBySchedule(Integer scheduleId);
     public int updateSeatStatus(Integer seatId, SeatStatus status);
     public Long countAvailableSeats(Integer scheduleId);
     public int deleteSeat(Integer seatId);
     public SeatDTO getSeatById(Integer seatId);
     public List<SeatDTO> getAllSeats();
}
