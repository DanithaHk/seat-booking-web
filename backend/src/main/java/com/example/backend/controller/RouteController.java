package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ResponseDTO;
import com.example.backend.dto.RouteDTO;
import com.example.backend.service.RouteService;
import com.example.backend.utill.VarList;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

@PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveRoute(@RequestBody RouteDTO routeDTO) { 
        try{
            routeService.createRoute(routeDTO);
            return ResponseEntity.status(201)
                        .body(new ResponseDTO(VarList.Created, "Success", null));       
            
        }catch(Exception e){
            return ResponseEntity.status(500)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteRoute(@PathVariable Integer id) { 
        try{
            routeService.deleteRouteById(id);
            return ResponseEntity.status(200)
                        .body(new ResponseDTO(VarList.OK, "Success", null));       
            
        }catch(Exception e){
            return ResponseEntity.status(500)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
@GetMapping("/getAllRoutes")
public ResponseEntity<ResponseDTO> getAllRoutes() {
    try{
        List<RouteDTO> routeDTOs = routeService.getAllRoutes();
        return ResponseEntity.status(200)
                    .body(new ResponseDTO(VarList.OK, "Success", routeDTOs));       
    }catch(Exception e){
        return ResponseEntity.status(500)
                .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
    }
}

}
