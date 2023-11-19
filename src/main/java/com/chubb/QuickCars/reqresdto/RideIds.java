package com.chubb.QuickCars.reqresdto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;



@AllArgsConstructor
@NoArgsConstructor
public class RideIds {

    private ArrayList<String> rideIds;

    public ArrayList<String> getRideIds() {
        return rideIds;
    }

    public void setRideIds(ArrayList<String> rideIds) {
        this.rideIds = rideIds;
    }
}
