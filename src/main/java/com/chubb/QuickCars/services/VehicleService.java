package com.chubb.QuickCars.services;


import com.chubb.QuickCars.models.Vehicle;
import com.chubb.QuickCars.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {


    @Autowired
    VehicleRepository vehicleRepository;




    public ResponseEntity<?> getAllVehicles(String username){
        List<Vehicle> result=this.vehicleRepository.findByVendorUsername(username);
        for(int i=0;i<result.size();i++){
            result.get(i).setMID(result.get(i).getId().toString());
        }

        return  ResponseEntity.ok(result);
    }



}
