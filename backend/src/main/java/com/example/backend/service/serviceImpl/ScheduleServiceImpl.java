package com.example.backend.service.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.backend.dto.ScheduleDTO;
import com.example.backend.entity.Bus;
import com.example.backend.entity.Route;
import com.example.backend.entity.Schedule;
import com.example.backend.repo.BusRepository;
import com.example.backend.repo.RouteRepository;
import com.example.backend.repo.ScheduleRepository;
import com.example.backend.service.ScheduleService;
import com.example.backend.util.VarList;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    public ScheduleServiceImpl(
            ScheduleRepository scheduleRepository,
            BusRepository busRepository,
            RouteRepository routeRepository,
            ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int createSchedule(ScheduleDTO scheduleDTO) {
        try {
            // Fetch Bus and Route entities
            Bus bus = busRepository.findById(scheduleDTO.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));
            
            Route route = routeRepository.findById(scheduleDTO.getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));

            // Check if schedule already exists for this bus, route, and departure time
            List<Schedule> existingSchedules = scheduleRepository.findByBusAndRoute(
                    scheduleDTO.getBusId().intValue(), 
                    scheduleDTO.getRouteId().intValue()
            );

            for (Schedule existing : existingSchedules) {
                if (existing.getDepartureTime().equals(scheduleDTO.getDepartureTime())) {
                    return VarList.Not_Acceptable; // Duplicate schedule
                }
            }

            // Create new schedule
            Schedule schedule = new Schedule();
            schedule.setBus(bus);
            schedule.setRoute(route);
            schedule.setDepartureTime(scheduleDTO.getDepartureTime());
            schedule.setArrivalTime(scheduleDTO.getArrivalTime());
            schedule.setPrice(scheduleDTO.getPrice());

            scheduleRepository.save(schedule);
            return VarList.Created;

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public int deleteScheduleById(Integer id) {
        try {
            if (!scheduleRepository.existsById(id)) {
                return VarList.Not_Found;
            }
            scheduleRepository.deleteById(id);
            return VarList.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public int editScheduleTimeById(Integer id, LocalTime arrivalTime, LocalTime departureTime) {
        try {
            Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);

            if (optionalSchedule.isEmpty()) {
                return VarList.Not_Found;
            }

            Schedule schedule = optionalSchedule.get();
            schedule.setArrivalTime(arrivalTime);
            schedule.setDepartureTime(departureTime);

            scheduleRepository.save(schedule);
            return VarList.OK;

        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

@Override
public List<ScheduleDTO> getAllSchedules() {
    List<Schedule> schedules = scheduleRepository.findAll();

    List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
    for (Schedule schedule : schedules) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setBusId(schedule.getBus().getId()); // manual mapping
        dto.setRouteId(schedule.getRoute().getId()); // manual mapping
        dto.setDepartureTime(schedule.getDepartureTime());
        dto.setArrivalTime(schedule.getArrivalTime());
        dto.setPrice(schedule.getPrice());
        scheduleDTOs.add(dto);
    }

    return scheduleDTOs;
}

    @Override
    public ScheduleDTO getScheduleById(Integer id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return modelMapper.map(schedule, ScheduleDTO.class);
    }

    @Override
    public List<ScheduleDTO> getSchedulesByRoute(Integer routeId) {
        List<Schedule> schedules = scheduleRepository.findByRouteId(routeId);
        return modelMapper.map(
                schedules, 
                new org.modelmapper.TypeToken<List<ScheduleDTO>>() {}.getType()
        );
    }

    @Override
    public List<ScheduleDTO> getSchedulesByBus(Integer busId) {
        List<Schedule> schedules = scheduleRepository.findByBusId(busId);
        return modelMapper.map(
                schedules, 
                new org.modelmapper.TypeToken<List<ScheduleDTO>>() {}.getType()
        );
    }
}