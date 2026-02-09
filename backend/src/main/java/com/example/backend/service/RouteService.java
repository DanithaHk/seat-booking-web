package com.example.backend.service;


import java.util.List;

import com.example.backend.dto.RouteDTO;
public interface RouteService {

    int createRoute(RouteDTO routeDTO);
    // delete route by id
    int deleteRouteById(Integer id);

    // Get all routes
    List<RouteDTO> getAllRoutes();
}
