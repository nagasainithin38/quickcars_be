package com.chubb.QuickCars.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    private ObjectId id;
    private String number_plate;

    private String username;
    private String vendor_username;
    private List<String> images;

    private List<String> completedRides;

    private List<String> upcomingRide;

    private String currRide;

    private double latitude;

    private double longitde;

    private String MID;
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCurrRide() {
        return currRide;
    }

    public void setCurrRide(String currRIde) {
        this.currRide = currRIde;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getVendor_username() {
        return vendor_username;
    }

    public void setVendor_username(String vendor_username) {
        this.vendor_username = vendor_username;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCompletedRides() {
        return completedRides;
    }

    public void setCompletedRides(List<String> completedRides) {
        this.completedRides = completedRides;
    }

    public List<String> getUpcomingRide() {
        return upcomingRide;
    }

    public void setUpcomingRide(List<String> upcomingRide) {
        this.upcomingRide = upcomingRide;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitde() {
        return longitde;
    }

    public void setLongitde(double longitde) {
        this.longitde = longitde;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public void appendToUpcomingRides(String ride){
        this.upcomingRide.add(ride);
    }

    public void appendToCompletedRides(String ride){

        List<String> upcomingRide=this.getUpcomingRide();
        List<String> newUpcomingRide=new ArrayList<String>();
        for(String id:upcomingRide){
            if(!id.equals(ride)){
                newUpcomingRide.add(id);
            }
        }
        this.setUpcomingRide(newUpcomingRide);
        this.completedRides.add(ride);

    }
}
