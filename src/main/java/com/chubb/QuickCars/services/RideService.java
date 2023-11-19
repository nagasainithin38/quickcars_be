package com.chubb.QuickCars.services;


import com.chubb.QuickCars.models.*;
import com.chubb.QuickCars.repositories.*;
import com.chubb.QuickCars.reqresdto.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {



    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RideRepository rideRepository;
    public ResponseEntity<?> toHomeBooking(Booking requestBody,String employeeUserName){
        requestBody.setStatus(BookingStatus.PENDING);
        LocalDate today = LocalDate.now();
        String tomorrow=today.format(DateTimeFormatter.ISO_DATE);
//        String tomorrow = (today.plusDays(1)).format(DateTimeFormatter.ISO_DATE);
        requestBody.setData(tomorrow);
        Organisation organisation=organisationRepository.findByUsername(requestBody.getOrganisationUsername());
        requestBody.setFrom(organisation.getAddress());
        Booking insertedBookingObj=bookingRepository.save(requestBody);
        Employee employee=employeeRepository.findByUsername(employeeUserName);
        employee.setTo_home_ride(insertedBookingObj.getId().toString());
        employeeRepository.save(employee);
        return ResponseEntity.ok(new StatMsg(ReqStatus.CREATED,"Booking request Successful with reference id "+employee.getTo_home_ride()));
    }

    public ResponseEntity<?> getAllPendingBookings(String organisationUsername,String sortBy){

        System.out.println(sortBy);
        System.out.println(sortBy.equals("from"));
        if(sortBy.equals("form"))
        {
            List<Booking> pendingBookings=bookingRepository.findAllPendingBookingsByUsername(organisationUsername,"PENDING");


            for(int i=0;i<pendingBookings.size();i++){
                pendingBookings.get(i).setMID(pendingBookings.get(i).getId().toString());
            }

            return ResponseEntity.ok(pendingBookings);
        }
        else{
            List<Booking> pendingBookings=bookingRepository.findAllPendingBookingsByUsernameT(organisationUsername,"PENDING");
            for(int i=0;i<pendingBookings.size();i++){
                pendingBookings.get(i).setMID(pendingBookings.get(i).getId().toString());
            }
            return ResponseEntity.ok(pendingBookings);
        }

//        List<Booking> result= new ArrayList<Booking>();
//        for(int i=0;i<pendingBookings.size();i++){
//            if(pendingBookings.get(i).getStatus()==BookingStatus.PENDING){
//                pendingBookings.get(i).setMID(pendingBookings.get(i).getId().toString());
//                result.add(pendingBookings.get(i));
//            }
//
//        }
    }


    public List<Vehicle> getAvailableVehicles(String start,String end,String vendorUsername,String date){

        List <Vehicle> allVehicles=vehicleRepository.findByVendorUsername(vendorUsername);
        if(allVehicles.isEmpty()){
            return allVehicles;
        }
        List<Vehicle> availableVehicle=new ArrayList<Vehicle>();
        for (Vehicle currVehicle : allVehicles) {
            System.out.println(currVehicle.getId());
            List<String> rideIds = currVehicle.getUpcomingRide();
            List<Ride> rideDetails = new ArrayList<Ride>();
            for(String id: rideIds){
                System.out.println(id);
                rideDetails.add(rideRepository.findByMid(id));
            }
            boolean isAssigned = false;
            System.out.println(rideDetails.size());
            for (int j = 0; j < rideDetails.size(); j++) {
                System.out.println(rideDetails.get(j).getDate());
                if(rideDetails.get(j).getDate().equals(date)){
                    System.out.println("ride start "+rideDetails.get(j).getStart()+" ride details end "+rideDetails.get(j).getEnd());
                    System.out.println(start+" "+end);
                    System.out.println(rideDetails.get(j).getStart().compareTo(start) <= 0 && rideDetails.get(j).getEnd().compareTo(start) >= 0);
                    System.out.println(rideDetails.get(j).getStart().compareTo(end) <= 0 && rideDetails.get(j).getEnd().compareTo(end) >= 0);
                    if (rideDetails.get(j).getStart().compareTo(start) <= 0 && rideDetails.get(j).getEnd().compareTo(start) >= 0) {
                        isAssigned = true;
                        break;
                    }
                    if (rideDetails.get(j).getStart().compareTo(end) <= 0 && rideDetails.get(j).getEnd().compareTo(end) >= 0) {
                        isAssigned = true;
                        break;
                    }

                }
            }
            System.out.println(227+" "+currVehicle.getId()+" "+isAssigned);
            if(!isAssigned){
                availableVehicle.add(currVehicle);
            }


        }
        return availableVehicle;
    }


    public ResponseEntity<?> mapUsersToCab(VehicleEmployeeDTO requestBody,String vendorUsername){

        System.out.println(1);
        List<Booking> bookingList=bookingRepository.findAllById(requestBody.getEmployeeIds());
        if(bookingList.isEmpty()){
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"No users selected to create ride"));
        }
//        System.out.println(bookingList.get(0).getFrom().equals(requestBody.getFrom()));
//        System.out.println(bookingList.get(0).getTo().equals(requestBody.getTo()));
        if(!bookingList.get(0).getFrom().equalsIgnoreCase(requestBody.getFrom()) && !bookingList.get(0).getTo().equalsIgnoreCase(requestBody.getTo())){
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Pickup or Drop address to be same for all employees"));
        }

        boolean isDateSame=true;
        boolean isDestinationSame=true;
        boolean isPickupsame=true;

        for(int i=1;i<bookingList.size();i++){
            Booking booking1=bookingList.get(i-1);
            Booking booking2=bookingList.get(i);
            if(!booking1.getData().equals(booking2.getData())){
                isDateSame=false;
                break;
            }
            if(!booking1.getFrom().equals(booking2.getFrom())){
                isPickupsame=false;
            }
            if(!booking1.getTo().equals(booking2.getTo())){
                isDestinationSame=false;
            }
        }
        if(!isDateSame){
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Date should be same for each employee"));
        }
        if(!isPickupsame && !isDestinationSame){
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"Pickup or Drop address to be same for all employees"));
        }
        System.out.println(2);
//       **********************************************************************************************************************************************************************************************************************************************


        List<Vehicle> availableVehicles= this.getAvailableVehicles(requestBody.getStart(),requestBody.getEnd(),vendorUsername,bookingList.get(0).getDate());


        System.out.println(3);
        if(availableVehicles.isEmpty()){
            return ResponseEntity.ok(new StatMsg(ReqStatus.ERROR,"No vehicles available on "+bookingList.get(0).getDate()+" from "+requestBody.getStart()+" to "+requestBody.getEnd()));
        }

        Vehicle AssignedVehicle=availableVehicles.get(0);

        Ride ride=new Ride(RideStatus.ASSIGNED,requestBody.getFrom(),requestBody.getTo(),requestBody.getStart(),requestBody.getEnd(),bookingList.get(0).getDate(),requestBody.getEmployeeIds());

        if(isPickupsame)
        {
            ride.setFrom(bookingList.get(0).getFrom());
        }
        if(isDestinationSame){
            ride.setTo(bookingList.get(0).getTo());
        }
        System.out.println(4);
        ride.setMid(UUID.randomUUID().toString().split("-")[0]);
        Ride newRide=rideRepository.save(ride);

        AssignedVehicle.appendToUpcomingRides(newRide.getMid());
        System.out.println(5);
        vehicleRepository.save(AssignedVehicle);
        System.out.println(6);
        for(int i=0;i<bookingList.size();i++){
            bookingList.get(i).setStatus(BookingStatus.ASSIGNED);
            bookingList.get(i).setVehicleUsername(AssignedVehicle.getUsername());
        }
        System.out.println(7);
        bookingRepository.saveAll(bookingList);
        System.out.println(8);
        return ResponseEntity.ok(new StatMsg(ReqStatus.CREATED,"Ride created Successfully"));
    }


    public ResponseEntity<?> getOrganisationsList(String username){
        System.out.println("In");
        List<Organisation> orgsList=organisationRepository.findOrganisationsByVendorUsername(username);
        List<String> result=new ArrayList<String>();
        for(int i=0;i<orgsList.size();i++){
            result.add(orgsList.get(i).getUsername());
        }
        System.out.println("Out");
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getRideDetails(String mid){
     Optional<Booking> booking=bookingRepository.findById(mid);
     if(booking.isPresent()){
         return ResponseEntity.ok(booking.get());
     }
     return ResponseEntity.ok("");

    }



    public ResponseEntity<?> getAllVehicleRideDetials(ArrayList<String> rideIds){
        List<Ride> result=new ArrayList<Ride>();
        for(String id:rideIds){
            result.add(rideRepository.findByMid(id));
        }
        return ResponseEntity.ok(result);
    }




    public ResponseEntity<?> startRide(String vehicleUserName,String rideMId){

        System.out.println(1);
        Vehicle vehicle=vehicleRepository.findByUsername(vehicleUserName);
        System.out.println(2);
        Ride ride=rideRepository.findByMid(rideMId);
        System.out.println(3);

        vehicle.setCurrRide(rideMId);
        System.out.println(4);
        ride.setRideStatus(RideStatus.STARTED);
        System.out.println(5);
        rideRepository.deleteByMid(ride.getMid());
        ride.setId(null);
        rideRepository.save(ride);
        System.out.println(6);
        vehicleRepository.save(vehicle);
        System.out.println(7);
        for(String empId:ride.getPassengerList()){
            Optional<Booking> empBooking=bookingRepository.findById(empId);
            if(empBooking.isPresent()){
                Booking booking=empBooking.get();
                booking.setStatus(BookingStatus.STARTED);
                bookingRepository.save(booking);
            }

        }


        return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Ride Started Successfully"));
    }



    public ResponseEntity<?> getRideEmpDetails(String rideId){
        List<Booking> bookingList=new ArrayList<Booking>();
        Ride ride=rideRepository.findByMid(rideId);
        for(String bookId:ride.getPassengerList()){
            Optional<Booking> booking=bookingRepository.findById(bookId);
            if(booking.isPresent()){
                bookingList.add(booking.get());
            }
        }
      return ResponseEntity.ok(bookingList);
    }



    public ResponseEntity<?> updateLocation(String username,double latitude,double longitude){
        Vehicle vehicle=vehicleRepository.findByUsername(username);
        vehicle.setLatitude(latitude);
        vehicle.setLongitde(longitude);
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Updated successfully"));
    }


    public  ResponseEntity<?> getLatlon(String username){
        Vehicle vehicle=vehicleRepository.findByUsername(username);
        LatLon latlon=new LatLon();
        latlon.setLatitude(vehicle.getLatitude());
        latlon.setLongitude(vehicle.getLongitde());
        return ResponseEntity.ok(latlon);
    }


    public ResponseEntity<?> endRide(String username){

            Vehicle vehicle=vehicleRepository.findByUsername(username);
            String rideId=vehicle.getCurrRide();
            vehicle.setCurrRide(null);
            vehicle.appendToCompletedRides(rideId);
            Ride ride=rideRepository.findByMid(rideId);
            ride.setRideStatus(RideStatus.ENDED);
            for(String bookId:ride.getPassengerList()){
                Optional<Booking> booking=bookingRepository.findById(bookId);
                if(booking.isPresent()) {
                    Booking currBook = booking.get();
                    currBook.setStatus(BookingStatus.COMPLETED);
                    bookingRepository.save(currBook);
                    Employee emp=employeeRepository.findByRideId(currBook.getId().toString());
                    emp.setTo_home_ride(null);
                    emp.appendToCompletedRide(currBook.getId().toString());
                    employeeRepository.save(emp);
                }
            }
            System.out.println("befor de");
            rideRepository.deleteByMid(ride.getMid());
            System.out.println("after del");
            ride.setId(null);
            rideRepository.save(ride);
            System.out.println("After sav");
            vehicleRepository.save(vehicle);



        return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Ride completed"));
    }

}
