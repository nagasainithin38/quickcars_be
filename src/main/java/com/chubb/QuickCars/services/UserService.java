package com.chubb.QuickCars.services;


import com.chubb.QuickCars.models.*;
import com.chubb.QuickCars.repositories.*;
import com.chubb.QuickCars.reqresdto.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    MailService mailService;


    public ResponseEntity<?> sos(Sos sos ){
        Userr userr= userRepository.findByUsername(sos.getOrg_username());
        String text="Track my ride I found this something suspecios \nuser details\nname = "+sos.getName()+"\nusername = "+sos.getUsername()+"\nphone number = "+sos.getNumber()+"\nRide location = "+sos.getUrl();
        Mail mail  =new Mail(userr.getEmail(),"Sos by "+sos.getUsername(),text );
        ResponseEntity<?> result=mailService.sendMail(mail);
        System.out.println(result.getBody());
        if(result.getBody().equals("success")){
            return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Sent successfully"));
        }
        else{
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Error happened"));
        }
    }

    public ResponseEntity<?> addVendor(Userr user, Vendor vendor){

        List<Userr> searchedUser=userRepository.findByUsernameOrCode(user.getUsername(),user.getCode());
        if(!searchedUser.isEmpty()){
            if(user.getUsername().equals(searchedUser.get(0).getUsername())){
                return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"User Already exisiting with username "+user.getUsername()));
            }
            else{
                return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"Code "+user.getCode()+" is not available"));
            }
        }
        Vendor insertedVendor=vendorRepository.save(vendor);
        user.setObject_id(insertedVendor.getId());

        Userr insertedUser=userRepository.save(user);
        return ResponseEntity.ok(new StatMsg(ReqStatus.CREATED,"User Created with username "+user.getUsername()));
    }
    public ResponseEntity<?> addOrganisation(Userr user, Organisation organisation){
        List<Userr> searchedUser=userRepository.findByUsernameOrCode(user.getUsername(),user.getCode());
        if(!searchedUser.isEmpty()){
            if(user.getUsername().equals(searchedUser.get(0).getUsername())){
                return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"User Already exisiting with username "+user.getUsername()));
            }
            else{
                return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"Code "+user.getCode()+" is not available"));
            }
        }
        Organisation insertedOrganistation=organisationRepository.save(organisation);
        user.setObject_id(insertedOrganistation.getId());
        Userr insertedUser=userRepository.save(user);
        return ResponseEntity.ok(new StatMsg(ReqStatus.CREATED,"User Created with username "+user.getUsername()));
    }

    public ResponseEntity<?> addVehicle(Userr user, Vehicle vehicle){
        System.out.println(1);
        String username=user.getUsername();
        user.setUsername(user.getUsername()+"@"+user.getCode());
        Userr searchedUser=userRepository.findByUsername(user.getUsername());
        if(searchedUser!=null){
            return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"User Already exisiting with username "+username));
        }
        System.out.println(2);
        vehicle.setUsername(user.getUsername());
        Vehicle insertedVehicle=vehicleRepository.save(vehicle);
        user.setObject_id(insertedVehicle.getId());
        System.out.println(3);
        Userr insertedUser=userRepository.save(user);
        System.out.println(username);
        Vendor vendor=vendorRepository.findByUsername(vehicle.getVendor_username());
        System.out.println("Testing");
        System.out.println(vendor.getVehicleCount());
        vendor.setVehicleCount(vendor.getVehicleCount()+1);
        System.out.println("bef saving");
        vendorRepository.save(vendor);
        System.out.println("after sav");
        return ResponseEntity.ok(new StatMsg(ReqStatus.CREATED,"User Created with username "+user.getUsername()));
    }

    public ResponseEntity<?> addEmployee(Userr user, Employee employee){
        String username=user.getUsername();
        user.setUsername(user.getUsername()+"@"+user.getCode());
        Userr searchedUser=userRepository.findByUsername(user.getUsername());
        if(searchedUser!=null){
            return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"User Already exisiting with username "+username));
        }
        employee.setUsername(user.getUsername());
        Employee insertedEmployee=employeeRepository.save(employee);
        user.setObject_id(insertedEmployee.getId());
        Userr insertedUser=userRepository.save(user);
        return ResponseEntity.ok(new StatMsg(ReqStatus.CREATED,"User Created with username "+user.getUsername()));

    }
    public ResponseEntity<?> login(Login loginBody){

        Userr loginDbObj=userRepository.findByUsername(loginBody.getUsername());
        String token = (UUID.randomUUID().toString());
        if(loginDbObj==null)
            return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"No User Exists with username "+loginBody.getUsername()));


        if(loginDbObj.getPassword().equals(loginBody.getPassword())){
            loginDbObj.setPassword(null);
            if(loginDbObj.getRole().equals("vendor")){

                Vendor vendor=vendorRepository.findByUsername(loginDbObj.getUsername());
                if(vendor.getVerificationStatus().equals("PENDING")){
                    return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Admin should approve your account try again later"));
                }
                UserVenOrg response=new UserVenOrg();
                response.setUserr(loginDbObj);
                response.setVendor(vendor);
                Token isTokenPresent=tokenRepository.findByUsername(loginBody.getUsername());
                if(isTokenPresent!=null)
                {
                    response.setToken(isTokenPresent.getToken());
                }
                else{
                    Token insertToken=new Token();
                    insertToken.setToken(token);
                    insertToken.setUsername(loginDbObj.getUsername());
                    insertToken.setRole("vendor");
                    tokenRepository.save(insertToken);
                    response.setToken(token);
                }

                return ResponseEntity.ok(response);
            }
            else if(loginDbObj.getRole().equals("organisation")){
                Organisation organisation=organisationRepository.findByUsername(loginBody.getUsername());
                if(organisation.getVerificationStatus().equals("PENDING")){
                    return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Admin should approve your account try again later"));
                }
                UserVenOrg response=new UserVenOrg();
                response.setUserr(loginDbObj);
                response.setOrganisation(organisation);
                Token isTokenPresent=tokenRepository.findByUsername(loginBody.getUsername());
                if(isTokenPresent!=null)
                {
                    response.setToken(isTokenPresent.getToken());
                }
                else{
                    Token insertToken=new Token();
                    insertToken.setToken(token);
                    insertToken.setUsername(loginDbObj.getUsername());
                    insertToken.setRole("organisation");
                    tokenRepository.save(insertToken);
                    response.setToken(token);
                }
                return ResponseEntity.ok(response);
            }
            else if(loginDbObj.getRole().equals("employee")){
                Employee employee =employeeRepository.findByUsername(loginDbObj.getUsername());
                UserVenOrg response=new UserVenOrg();
                response.setUserr(loginDbObj);
                response.setEmployee(employee);

                Token isTokenPresent=tokenRepository.findByUsername(loginBody.getUsername());
                if(isTokenPresent!=null)
                {
                    response.setToken(isTokenPresent.getToken());
                }
                else{
                    Token insertToken=new Token();
                    insertToken.setToken(token);
                    insertToken.setUsername(loginDbObj.getUsername());
                    insertToken.setRole("employee");
                    tokenRepository.save(insertToken);
                    response.setToken(token);
                }
                return ResponseEntity.ok(response);
            }
            else if(loginDbObj.getRole().equals("vehicle")){
                Vehicle vehicle =vehicleRepository.findByUsername(loginDbObj.getUsername());
                UserVenOrg response=new UserVenOrg();
                response.setUserr(loginDbObj);
                response.setVehicle(vehicle);
                System.out.println("0");
                Token isTokenPresent=tokenRepository.findByUsername(loginBody.getUsername());
                System.out.println("1");
                System.out.println(isTokenPresent);
                if(isTokenPresent!=null)
                {
                    System.out.println("2");
                    response.setToken(isTokenPresent.getToken());
                }
                else{
                    System.out.println("3");
                    Token insertToken=new Token();
                    insertToken.setToken(token);
                    insertToken.setUsername(loginDbObj.getUsername());
                    insertToken.setRole("vehicle");
                    tokenRepository.save(insertToken);
                    response.setToken(token);
                }
                System.out.println("4");
                return ResponseEntity.ok(response);
            }
            else{
                UserVenOrg response=new UserVenOrg();
                response.setUserr(loginDbObj);
                Token isTokenPresent=tokenRepository.findByUsername(loginBody.getUsername());
                System.out.println("1");
                System.out.println(isTokenPresent);
                if(isTokenPresent!=null)
                {
                    System.out.println("2");
                    response.setToken(isTokenPresent.getToken());
                }
                else{
                    System.out.println("3");
                    Token insertToken=new Token();
                    insertToken.setToken(token);
                    insertToken.setUsername(loginDbObj.getUsername());
                    insertToken.setRole("vehicle");
                    tokenRepository.save(insertToken);
                    response.setToken(token);
                }
                return ResponseEntity.ok(response);
            }

        }

        return  ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"Wrong Password "));
    }

    public ResponseEntity<?> logout(String username){

       Token deletedUser= tokenRepository.deleteByusername(username);
       if(deletedUser!=null)
        return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Logout success"));
       else{
           return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Error from backend"));
       }
    }
    public ResponseEntity<?> updateStatus(String username,String status,String role){

        if(role.equals("vendor"))
        {
            Vendor vendor=vendorRepository.findByUsername(username);
            if(vendor==null){
                return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"No Vendor found with username "+ username));
            }
            vendor.setVerificationStatus(status);
            vendorRepository.save(vendor);
            return ResponseEntity.ok(new StatMsg(ReqStatus.UPDATED,"Status Updated for user "+vendor.getUsername()+" to "+status));
        }
        else if (role.equals("organisation")) {
            Organisation organisation = organisationRepository.findByUsername(username);
            if(organisation==null){
                return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"No Organisation found with username "+ username));
            }
            organisation.setVerificationStatus(status);
            organisationRepository.save(organisation);
            return ResponseEntity.ok(new StatMsg(ReqStatus.UPDATED, "Status Updated for user " + organisation.getUsername() + " to " + status));
        }
        return ResponseEntity.ok(new StatMsg(ReqStatus.FAILED,"No user found to update"));
    }


    public ResponseEntity<?> getDetailsBytoken(String token){
        Token isTokenPresent=tokenRepository.findByToken(token);
        if(isTokenPresent==null){
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"token invalid"));
        }
        Userr user=userRepository.findByUsername(isTokenPresent.getUsername());
        UserVenOrg userDetails=new UserVenOrg();
        userDetails.setUserr(user);
        if(isTokenPresent.getRole().equals("vehicle")){
            Vehicle userVehicle=vehicleRepository.findByUsername(user.getUsername());
            userDetails.setVehicle(userVehicle);
        }
        else if(isTokenPresent.getToken().equals("employee")){
            Employee employee=employeeRepository.findByUsername(user.getUsername());
            userDetails.setEmployee(employee);
        }
        else if(isTokenPresent.getToken().equals("vendor")){
            Vendor vendor=vendorRepository.findByUsername(user.getUsername());
            userDetails.setVendor(vendor);
        } else if (isTokenPresent.getToken().equals("organisation")) {
            Organisation organisation=organisationRepository.findByUsername(user.getUsername());
            userDetails.setOrganisation(organisation);
        }
        userDetails.setToken(token);
        return ResponseEntity.ok(userDetails);
    }

    public ResponseEntity<?> getEmpCompletedDetails(String username){
        Employee emp=employeeRepository.findByUsername(username);
        List<Booking>result=new ArrayList<Booking>();
        for(String id:emp.getCompletedRides()){
            Optional<Booking> booking=bookingRepository.findById(id);
            if(booking.isPresent()){
                Booking temp=booking.get();
                temp.setMID(temp.getId().toString());
                result.add(temp);
            }
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getVehCompletedRideDetails(String username){

        Vehicle vehicle=vehicleRepository.findByUsername(username);
        List<String> completedRide=vehicle.getCompletedRides();
        ArrayList<Ride> rideDetails=new ArrayList<Ride>();
        for(String id:completedRide){
            Ride ride=rideRepository.findByMid(id);
            rideDetails.add(ride);
        }
        return ResponseEntity.ok(rideDetails);
    }


    public ResponseEntity<?> getEmpDetails(String username){
        List<Employee> empList=employeeRepository.findAllEmpByUsername(username);
        List<UserVenOrg> result=new ArrayList<UserVenOrg>();
        for(Employee emp:empList){
            Userr user=userRepository.findByUsername(emp.getUsername());
            UserVenOrg temp=new UserVenOrg();
            user.setPassword(null);
            temp.setUserr(user);
            temp.setEmployee(emp);
            result.add(temp);
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> updateDetails(UserVenOrg userVenOrg){

        Userr userr= userRepository.findByUsername(userVenOrg.getUserr().getUsername());
        userr.setEmail(userVenOrg.getUserr().getEmail());
        userr.setName(userVenOrg.getUserr().getName());
        userr.setNumber(userVenOrg.getUserr().getNumber());
        System.out.println(userr.getEmail());
        userRepository.save(userr);
        if(userr.getRole().equals("organisation")){
            Organisation organisation=organisationRepository.findByUsername(userr.getUsername());
            organisation.setStartTiming(userVenOrg.getOrganisation().getStartTiming());
            organisation.setEndTiming(userVenOrg.getOrganisation().getEndTiming());
            organisation.setDescription(userVenOrg.getOrganisation().getDescription());
            organisation.setBusinessType(userVenOrg.getOrganisation().getBusinessType());
            organisation.setAddress(userVenOrg.getOrganisation().getAddress());
            organisationRepository.save(organisation);
            return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Updated Successfully"));

        }
        else if(userr.getRole().equals("vendor") || userr.getRole().equals("employee")){
            return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Updated Successfully"));
        }
        else if(userr.getRole().equals("vehicle")){
            Vehicle vehicle=vehicleRepository.findByUsername(userVenOrg.getUserr().getUsername());
            vehicle.setNumber_plate(userVenOrg.getVehicle().getNumber_plate());
            System.out.println("before");
            vehicleRepository.save(vehicle);
            System.out.println("after");
            return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Updated Successfully"));
        }
    return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"in process"));
    }


    public ResponseEntity<?> unassign(String username){
        Organisation organisation=organisationRepository.findByUsername(username);
        organisation.setVendorUsername(null);
        organisation.setVendorAssigned(false);
        organisationRepository.save(organisation);
        return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Unassigned"));
    }

    public ResponseEntity<?> deleteEmp(String username){
        Userr userr=userRepository.deleteByUsername(username);
        System.out.println(userr.getUsername());
        if(userr.getRole().equals("employee")){
            Employee emp=employeeRepository.deleteEmp(username);
            System.out.println(emp.getUsername());
        }
        else if(userr.getRole().equals("vehicle")){
            Vehicle vehicle=vehicleRepository.deleteByusername(username);
            Vendor vendor=vendorRepository.findByUsername(vehicle.getVendor_username());
            vendor.setVehicleCount(vendor.getVehicleCount()-1);
            vendorRepository.save(vendor);
            System.out.println(vehicle.getUsername());
        }
        return ResponseEntity.ok(new StatMsg(ReqStatus.DELETED,"deleted"));

    }

}
