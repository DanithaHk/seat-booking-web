package com.example.backend.service;

import java.time.LocalTime;

import com.example.backend.dto.ScheduleDTO;

public interface ScheduleService {

    int createSchedule(ScheduleDTO scheduleDTO);
    // delete schedule by id
    int deleteScheduleById(Long id);

    // edit schedule time by id
    int editScheduleTimeById(Long id, LocalTime arrivalTime, LocalTime departureTime);

}
