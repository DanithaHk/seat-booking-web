package com.example.backend.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.SeatDTO;
import com.example.backend.entity.Bus;
import com.example.backend.entity.Schedule;
import com.example.backend.entity.Seat;
import com.example.backend.entity.SeatStatus;
import com.example.backend.repo.BusRepository;
import com.example.backend.repo.ScheduleRepository;
import com.example.backend.repo.SeatRepository;
import com.example.backend.service.SeatService;
import com.example.backend.util.VarList;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int createSeat(SeatDTO seatDTO) {
        try {
            Bus bus = busRepository.findById(seatDTO.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));

            Seat seat = new Seat();
            seat.setBus(bus);
            seat.setSeatNumber(seatDTO.getSeatNumber());
            seat.setSeatStatus(SeatStatus.AVAILABLE);

            if (seatDTO.getScheduleId() != null) {
                Schedule schedule = scheduleRepository.findById(seatDTO.getScheduleId())
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));
                seat.setSchedule(schedule);
            }

            seatRepository.save(seat);
            return VarList.Created;

        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public List<SeatDTO> getSeatsBySchedule(Integer scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleId(scheduleId);
        return modelMapper.map(
                seats, 
                new org.modelmapper.TypeToken<List<SeatDTO>>() {}.getType()
        );
    }

    @Override
    public List<SeatDTO> getSeatsByBus(Integer busId) {
        List<Seat> seats = seatRepository.findByBusId(busId);
        return modelMapper.map(
                seats, 
                new org.modelmapper.TypeToken<List<SeatDTO>>() {}.getType()
        );
    }

    @Override
    public List<SeatDTO> getAvailableSeatsBySchedule(Integer scheduleId) {
        List<Seat> seats = seatRepository.findByScheduleIdAndSeatStatus(scheduleId, SeatStatus.AVAILABLE);
        return modelMapper.map(
                seats, 
                new org.modelmapper.TypeToken<List<SeatDTO>>() {}.getType()
        );
    }

    @Override
    public int updateSeatStatus(Integer seatId, SeatStatus status) {
        try {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            seat.setSeatStatus(status);
            seatRepository.save(seat);
            return VarList.OK;

        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public Long countAvailableSeats(Integer scheduleId) {
        return seatRepository.countAvailableSeatsBySchedule(scheduleId);
    }

    @Override
    public int deleteSeat(Integer seatId) {
        try {
            if (!seatRepository.existsById(seatId)) {
                return VarList.Not_Found;
            }
            seatRepository.deleteById(seatId);
            return VarList.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public SeatDTO getSeatById(Integer seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        return modelMapper.map(seat, SeatDTO.class);
    }

    @Override
    public List<SeatDTO> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        return modelMapper.map(
                seats, 
                new org.modelmapper.TypeToken<List<SeatDTO>>() {}.getType()
        );
    }
}