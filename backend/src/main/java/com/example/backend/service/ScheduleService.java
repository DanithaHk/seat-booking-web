package com.example.backend.service;

import java.time.LocalTime;
import java.util.List;

import com.example.backend.dto.ScheduleDTO;

public interface ScheduleService {

    public int createSchedule(ScheduleDTO scheduleDTO) ;
    public int deleteScheduleById(Integer id);
    public int editScheduleTimeById(Integer id, LocalTime arrivalTime, LocalTime departureTime);
    public List<ScheduleDTO> getAllSchedules();
    public ScheduleDTO getScheduleById(Integer id);
    public List<ScheduleDTO> getSchedulesByRoute(Integer routeId);
    public List<ScheduleDTO> getSchedulesByBus(Integer busId);
}
