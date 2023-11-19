package com.chubb.QuickCars.controllers;


import com.chubb.QuickCars.models.Mail;
import com.chubb.QuickCars.repositories.UserRepository;
import com.chubb.QuickCars.services.AdminService;
import com.chubb.QuickCars.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;


    @GetMapping("/pending")
    public ResponseEntity<?> getPendingReq(@RequestParam("type")String type){
        return adminService.getPending(type,"PENDING");
    }
    @GetMapping("/verified")
    public ResponseEntity<?> getVerifiedReq(@RequestParam("type")String type){
        return adminService.getPending(type,"VERIFIED");
    }

    @GetMapping("/verify")
    public ResponseEntity<?> approve(@RequestParam("username")String username){
        return adminService.approveReq(username);
    }

    @GetMapping("/getVendorList")
    public ResponseEntity<?> getVendorList(){
        return adminService.getVendorList();
    }

    @GetMapping("/getUnOrganisationList")
    public ResponseEntity<?> getUnassignedList(){
        return adminService.getUnAssignedOrgs();
    }
    @GetMapping("/getassignedList")
    public ResponseEntity<?> getassignedList(){
        return adminService.getAssignedOrgs();
    }
    @GetMapping("/map")
    public ResponseEntity<?> mapOrgVen(@RequestParam("vun")String vendorUsername,@RequestParam("oun")String organisationUsername){
        return adminService.mapOrgVen(vendorUsername,organisationUsername);
    }



}
