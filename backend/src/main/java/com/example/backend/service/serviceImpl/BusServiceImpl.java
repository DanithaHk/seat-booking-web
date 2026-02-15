package com.example.backend.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.BusDTO;
import com.example.backend.entity.Bus;
import com.example.backend.entity.Status;
import com.example.backend.repo.BusRepository;
import com.example.backend.service.BusService;
import com.example.backend.util.VarList;

@Service
public class BusServiceImpl implements BusService {
    
    @Autowired
    private BusRepository busRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveBus(BusDTO busDTO) {
        if (busRepository.existsByBusNumber(busDTO.getBusNumber())) {
            throw new RuntimeException("Bus with number " + busDTO.getBusNumber() + " already exists.");
        }
        busRepository.save(modelMapper.map(busDTO, Bus.class));
        return VarList.Created;
    }

    @Override
    public List<BusDTO> getAllBuses() {
        List<BusDTO> busList = modelMapper.map(
                busRepository.findAll(), 
                new org.modelmapper.TypeToken<List<BusDTO>>() {}.getType()
        );
        return busList;
    }

    @Override
    public int updateBusStatus(String busNumber, Status status) {
        Bus bus = busRepository.findByBusNumber(busNumber)
                .orElseThrow(() -> new RuntimeException("Bus with number " + busNumber + " not found."));
        
        bus.setStatus(status);
        busRepository.save(bus);
        return VarList.OK;
    }

    @Override
    public Bus findByBusNumber(String busNumber) {
        Bus bus = busRepository.findByBusNumber(busNumber)
                .orElseThrow(() -> new RuntimeException("Bus with number " + busNumber + " not found."));
        return bus;
    }

    @Override
    public int deleteBusByBusNumber(String busNumber) {
        Bus bus = busRepository.findByBusNumber(busNumber)
                .orElseThrow(() -> new RuntimeException("Bus with number " + busNumber + " not found."));
        
        busRepository.delete(bus);
        return VarList.OK;
    }
}