package com.chubb.QuickCars.controllers;


import com.chubb.QuickCars.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://nithin-capstone.netlify.app")
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAllVehicles(@RequestParam(value = "username")String username){
        return this.vehicleService.getAllVehicles(username);
    }

}
