package com.chubb.QuickCars.controllers;


import com.chubb.QuickCars.models.Mail;
import com.chubb.QuickCars.reqresdto.*;
import com.chubb.QuickCars.services.MailService;
import com.chubb.QuickCars.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userSerivice;
    @Autowired
    MailService mailService;


    @PostMapping("/sos")
    public ResponseEntity<?> sos(@RequestBody Sos sos){
        return userSerivice.sos(sos);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserVenOrg requestBody){
        System.out.println("Hello");
        System.out.println(requestBody.getEmployee());

        if(requestBody.getUserr().getRole().equals("vendor")){

             return   userSerivice.addVendor(requestBody.getUserr(),requestBody.getVendor());
        }
        else if(requestBody.getUserr().getRole().equals("organisation")){
            return  userSerivice.addOrganisation(requestBody.getUserr(),requestBody.getOrganisation());
        }
        else if(requestBody.getUserr().getRole().equals("vehicle")){
            return userSerivice.addVehicle(requestBody.getUserr(), requestBody.getVehicle());
        }
        else if (requestBody.getUserr().getRole().equals("employee")) {
            return userSerivice.addEmployee(requestBody.getUserr(),requestBody.getEmployee());
        }
        StatMsg response=new StatMsg(ReqStatus.ERROR,"No proper Role for creation");
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginBody){
        return userSerivice.login(loginBody);
    }

    @PutMapping("/updateStatus/{username}/{status}/{role}")
    public ResponseEntity<?> updateStatus(@PathVariable String username,@PathVariable String status,@PathVariable String role){
        return userSerivice.updateStatus(username,status,role);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam(value = "username") String username){
        return userSerivice.logout(username);
    }

    @GetMapping("/isLogin")
    public ResponseEntity<?> getDetailsBytoken(@RequestParam(value = "token")String token){
        return userSerivice.getDetailsBytoken(token);
    }

    @GetMapping("/getCompletedRides")
    public ResponseEntity<?> getEmpCompletedDetails(@RequestParam("username")String username){
        return userSerivice.getEmpCompletedDetails(username);
    }


    @GetMapping("/getVehCompletedRides")
    public ResponseEntity<?> getVehCompletedRides(@RequestParam("username")String username){
        return userSerivice.getVehCompletedRideDetails(username);
    }

    @GetMapping("/getEmpList")
    public ResponseEntity<?> getEmpList(@RequestParam("username")String username){
        return userSerivice.getEmpDetails(username);
    }

    @PutMapping("/updateDetails")
    public ResponseEntity<?> updateDetails(@RequestBody UserVenOrg body){
        return userSerivice.updateDetails(body);
    }


    @GetMapping("/unassign")
    public ResponseEntity<?> unassigned(@RequestParam("username") String username){
        return userSerivice.unassign(username);
    }


    @DeleteMapping("/user")
    public ResponseEntity<?> deleteEmp(@RequestParam("username") String username){
        return userSerivice.deleteEmp(username);
    }

}
