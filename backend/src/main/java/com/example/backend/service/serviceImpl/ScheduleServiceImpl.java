package com.example.backend.service.serviceImpl;

import java.time.LocalTime;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.backend.dto.ScheduleDTO;
import com.example.backend.entity.Schedule;
import com.example.backend.repo.ScheduleRepository;
import com.example.backend.service.ScheduleService;
import com.example.backend.utill.VarList;
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

   @Override
    public int createSchedule(ScheduleDTO scheduleDTO) {
        try {
            boolean exists = scheduleRepository
                    .existsByBus_BusIdAndRoute_RouteIdAndDepartureTime(
                            scheduleDTO.getBusId(),
                            scheduleDTO.getRouteId(),
                            scheduleDTO.getDepartureTime()
                    );

            if (exists) {
                return VarList.Not_Acceptable;
            }

            scheduleRepository.save(
                    modelMapper.map(scheduleDTO, com.example.backend.entity.Schedule.class)
            );

            return VarList.Created;

        } catch (Exception e) {
            e.printStackTrace(); // debug help
            return VarList.Internal_Server_Error;
        }
    }

   @Override
   public int deleteScheduleById(Long id) {
         try {
              if (!scheduleRepository.existsById(id)) {
                return VarList.Not_Found;
              }
              scheduleRepository.deleteById(id);
              return VarList.OK;
         } catch (Exception e) {
              return VarList.Internal_Server_Error;
         }
   }

   @Override
    public int editScheduleTimeById(Long id, LocalTime arrivalTime, LocalTime departureTime) {
        try {
            Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);

            if (optionalSchedule.isEmpty()) {
                return VarList.Not_Found; // schedule not found
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

    
}
