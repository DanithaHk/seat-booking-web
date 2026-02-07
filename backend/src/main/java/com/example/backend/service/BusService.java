package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.BusDTO;
import com.example.backend.entity.Bus;
import com.example.backend.entity.Status;

public interface BusService {

    int saveBus(BusDTO busDTO) ;

    List<BusDTO> getAllBuses();
    // update bus status
    int updateBusStatus(String busNumber, Status status);
    //search by bus number
    Bus findByBusNumber(String busNumber);
    // delete bus by bus number
    int deleteBusByBusNumber(String busNumber);
}
