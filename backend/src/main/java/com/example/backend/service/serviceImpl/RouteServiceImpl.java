package com.example.backend.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.backend.dto.RouteDTO;
import com.example.backend.entity.Route;
import com.example.backend.repo.RouteRepository;
import com.example.backend.service.RouteService;
import com.example.backend.utill.VarList;
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
            if (routeRepository.existsByStartLocationAndEndLocation(routeDTO.getStartLocation(), routeDTO.getEndLocation())) {
                return VarList.Not_Found;
        }
        Route route = modelMapper.map(routeDTO, Route.class);
        
            routeRepository.save(route);
            return VarList.OK;
        } catch (Exception e) {
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
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        List<RouteDTO> routeDTOs = modelMapper.map(routes, new org.modelmapper.TypeToken<List<RouteDTO>>() {}.getType());
        return routeDTOs;
    }

}
