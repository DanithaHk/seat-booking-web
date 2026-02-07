package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.BusDTO;
import com.example.backend.dto.ResponseDTO;
import com.example.backend.entity.Bus;
import com.example.backend.entity.Status;
import com.example.backend.service.BusService;
import com.example.backend.utill.VarList;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/bus")
public class BusController {
    @Autowired
    private BusService busService;
    @PostMapping("/saveBus")
    public ResponseEntity<ResponseDTO> saveBus(@RequestBody @Valid BusDTO busDTO) { 
        try{
            busService.saveBus(busDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Success", busDTO));       
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), busDTO));
        }
    }

@GetMapping("/getAllBuses")
    public ResponseEntity<ResponseDTO> getAllBuses() { 
        try{

            List<BusDTO> busList = busService.getAllBuses();
            if (busList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDTO(VarList.No_Content, "No buses found", null));
            }

            return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Success", busList));       
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

@PutMapping("updateStatus/{busNumber}/{status}")
    public ResponseEntity<ResponseDTO> updateBusStatus(@PathVariable String busNumber, @PathVariable Status status) {
        try{
            busService.updateBusStatus(busNumber, status);
            return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Success", null));       
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
@GetMapping("/searchByBusNumber/{busNumber}")
public ResponseEntity<ResponseDTO> searchByBusNumber(@PathVariable String busNumber) {
    try {
        Bus bus = busService.findByBusNumber(busNumber);
        BusDTO busDTO = new BusDTO();
        busDTO.setBusNumber(bus.getBusNumber());
        busDTO.setBusModel(bus.getBusModel());
        busDTO.setTotalSeats(bus.getTotalSeats());
        busDTO.setStatus(bus.getStatus());
        busDTO.setFuelType(bus.getFuelType());
        busDTO.setBusType(bus.getBusType());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", busDTO));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
    }

   }

@DeleteMapping("/deleteByBusNumber/{busNumber}")
    public ResponseEntity<ResponseDTO> deleteByBusNumber(@PathVariable String busNumber) {
     try {
          busService.deleteBusByBusNumber(busNumber);
          return ResponseEntity.status(HttpStatus.OK)
                 .body(new ResponseDTO(VarList.OK, "Bus deleted successfully", null));
     } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
     }
    }
}