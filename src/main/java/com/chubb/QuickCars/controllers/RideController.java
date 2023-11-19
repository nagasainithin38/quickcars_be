package com.chubb.QuickCars.controllers;


import com.chubb.QuickCars.models.Booking;
import com.chubb.QuickCars.reqresdto.RideIds;
import com.chubb.QuickCars.reqresdto.VehicleEmployeeDTO;
import com.chubb.QuickCars.services.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "https://nithin-capstone.netlify.app")
@RestController
@RequestMapping("/ride")
public class RideController {

    @Autowired
    RideService rideService;

    @PostMapping("/toHome")
    public ResponseEntity<?> toHome(@RequestBody Booking requestBody,@RequestParam(value = "username",required = true)String employeeUserName){
        return rideService.toHomeBooking(requestBody,employeeUserName);
    }


//    @GetMapping("/availableVehicles")
//    public ResponseEntity<?> getAvailableVehicles(@RequestParam(value = "date")String date,@RequestParam(value = "start")String start,@RequestParam("end")String end,@RequestParam("vendorUsername")String vendorUsername){
//        return rideService.getAvailableVehicles(start,end,vendorUsername,date);
//    }

    @GetMapping("/pendingBookings")
    public ResponseEntity<?> getAllPendingBookingsByusername(@RequestParam("organisationUsername") String organisationUsername,@RequestParam(value = "sortBy",required = false)String sortBy){
        if(sortBy==null){
            sortBy="from";
        }
        return rideService.getAllPendingBookings(organisationUsername,sortBy);
    }

    @PostMapping("/createRide")
    public ResponseEntity<?> createNewRide(@RequestBody VehicleEmployeeDTO requestBody,@RequestParam("username")String vendorUsername){
        return rideService.mapUsersToCab(requestBody,vendorUsername);
    }

    @GetMapping("/getOrganisations")
    public ResponseEntity<?> getOrganisationsByVendorName(@RequestParam(value = "username") String username){
        System.out.println("In Ride");
        return rideService.getOrganisationsList(username);
    }
    @GetMapping("/getRideDetails")
    public ResponseEntity<?> getRideDetails(@RequestParam(value = "mid")String mid){
        return rideService.getRideDetails(mid);
    }

    @PostMapping("/getAllVehicleRideDetails")
    public ResponseEntity<?> getALlVehicleRideDetails(@RequestBody RideIds rideIds){
        System.out.println("GHel");
        System.out.println(rideIds);
        return rideService.getAllVehicleRideDetials(rideIds.getRideIds());
    }

    @GetMapping("/startRide")
    public ResponseEntity<?> startRide(@RequestParam(value = "username")String username,@RequestParam(value = "rideId") String rideMId){
        return rideService.startRide(username,rideMId);
    }

    @GetMapping("/getRideEmpDetails")
    public ResponseEntity<?> getRideEmpDetails(@RequestParam(value = "rideId")String rideId){
        return  rideService.getRideEmpDetails(rideId);
    }


    @PutMapping("/updateLoc")
    public ResponseEntity<?> updateLoc(@RequestParam(value = "username")String username,@RequestParam(value = "latitude")double latitude,@RequestParam(value = "longitude")double longitude){
        return this.rideService.updateLocation(username,latitude,longitude);
    }

    @GetMapping("/getLatLon")
    public ResponseEntity<?> getLatLon(@RequestParam("username")String username){
        return  this.rideService.getLatlon(username);
    }

    @GetMapping("/endRide")
    public ResponseEntity<?> endRide(@RequestParam("username")String username){
    return rideService.endRide(username);
    }


}
