package com.chubb.QuickCars.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    private ObjectId id;

    private String org_username;

    private String username;

    private List<String> completedRides;

    private String to_home_ride;

    private String to_office_ride;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getOrg_username() {
        return org_username;
    }

    public void setOrg_username(String org_username) {
        this.org_username = org_username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getCompletedRides() {
        return completedRides;
    }

    public void setCompletedRides(List<String> completedRides) {
        this.completedRides = completedRides;
    }

    public String getTo_home_ride() {
        return to_home_ride;
    }

    public void setTo_home_ride(String to_home_ride) {
        this.to_home_ride = to_home_ride;
    }

    public String getTo_office_ride() {
        return to_office_ride;
    }

    public void setTo_office_ride(String to_office_ride) {
        this.to_office_ride = to_office_ride;
    }

    public void appendToCompletedRide(String rideId){this.completedRides.add(rideId);}
}
