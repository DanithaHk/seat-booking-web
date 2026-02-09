package com.example.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.ScheduleDTO;
import com.example.backend.service.ScheduleService;
import com.example.backend.utill.VarList;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            // Call the service to create the schedule
            int status = scheduleService.createSchedule(scheduleDTO);
            if (status == VarList.Created) {
                return ResponseEntity.status(201)
                        .body(new ResponseDTO(VarList.Created, "Schedule created successfully", null));
            } else if (status == VarList.Not_Acceptable) {
                return ResponseEntity.status(406)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Schedule already exists", null));
            } else {
                return ResponseEntity.status(500)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to create schedule", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteSchedule(@PathVariable Long id) {
        try {
            int status = scheduleService.deleteScheduleById(id);
            if (status == VarList.OK) {
                return ResponseEntity.status(200)
                        .body(new ResponseDTO(VarList.OK, "Schedule deleted successfully", null));
            } else if (status == VarList.Not_Found) {
                return ResponseEntity.status(404)
                        .body(new ResponseDTO(VarList.Not_Found, "Schedule not found", null));
            } else {
                return ResponseEntity.status(500)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to delete schedule", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @PutMapping("updateTime/{id}")
    public ResponseEntity<ResponseDTO> updateScheduleTime(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) {
        LocalTime arrivalTime = scheduleDTO.getArrivalTime();
        LocalTime departureTime = scheduleDTO.getDepartureTime();
        try {
            int status = scheduleService.editScheduleTimeById(id, arrivalTime, departureTime);
            if (status == VarList.OK) {
                return ResponseEntity.status(200)
                        .body(new ResponseDTO(VarList.OK, "Schedule time updated successfully", null));
            } else if (status == VarList.Not_Found) {
                return ResponseEntity.status(404)
                        .body(new ResponseDTO(VarList.Not_Found, "Schedule not found", null));
            } else {
                return ResponseEntity.status(500)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Failed to update schedule time", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
