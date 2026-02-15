package com.example.backend.service;


import java.util.List;

import com.example.backend.dto.RouteDTO;
public interface RouteService {

    public List<RouteDTO> getAllRoutes();
    public RouteDTO getRouteById(Integer id);
    public int createRoute(RouteDTO routeDTO);
    public int updateRoute(Integer id, RouteDTO routeDTO);
    public int deleteRouteById(Integer id);
}
