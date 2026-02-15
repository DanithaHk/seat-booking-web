package com.example.backend.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.backend.dto.RouteDTO;
import com.example.backend.entity.Route;
import com.example.backend.repo.RouteRepository;
import com.example.backend.service.RouteService;
import com.example.backend.util.VarList;

@Service
public class RouteServiceImpl implements RouteService {
    
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    public RouteServiceImpl(RouteRepository routeRepository, ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int createRoute(RouteDTO routeDTO) {
        try {
            // Check if route already exists
            if (routeRepository.findByStartLocationAndEndLocation(
                    routeDTO.getStartLocation(), 
                    routeDTO.getEndLocation()
            ).isPresent()) {
                return VarList.Not_Acceptable; // Route already exists
            }
            
            Route route = modelMapper.map(routeDTO, Route.class);
            routeRepository.save(route);
            return VarList.Created;
            
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public int deleteRouteById(Integer id) {
        try {
            if (!routeRepository.existsById(id)) {
                return VarList.Not_Found;
            }
            routeRepository.deleteById(id);
            return VarList.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        List<RouteDTO> routeDTOs = modelMapper.map(
                routes, 
                new org.modelmapper.TypeToken<List<RouteDTO>>() {}.getType()
        );
        return routeDTOs;
    }

    @Override
    public RouteDTO getRouteById(Integer id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));
        return modelMapper.map(route, RouteDTO.class);
    }

    @Override
    public int updateRoute(Integer id, RouteDTO routeDTO) {
        try {
            Route route = routeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            
            route.setStartLocation(routeDTO.getStartLocation());
            route.setEndLocation(routeDTO.getEndLocation());
            route.setDistanceKm(routeDTO.getDistanceKm());
            
            routeRepository.save(route);
            return VarList.OK;
            
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }
}