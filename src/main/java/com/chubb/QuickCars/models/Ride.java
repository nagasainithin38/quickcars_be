package com.chubb.QuickCars.models;

import com.chubb.QuickCars.reqresdto.RideStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "ride")
@AllArgsConstructor
@NoArgsConstructor
public class Ride {
    @Id
    private ObjectId Id;

    private RideStatus rideStatus;

    private String from;
    private String to;
    private String start;
    private String end;
    private String date;
    private List<String> passengerList;

    private  String mid;

    public ObjectId getId() {
        return Id;
    }

    public Ride(RideStatus rideStatus, String from, String to, String start, String end, String date, List<String> passengerList) {
        this.rideStatus = rideStatus;
        this.from = from;
        this.to = to;
        this.start = start;
        this.end = end;
        this.date = date;
        this.passengerList = passengerList;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setId(ObjectId id) {
        Id = id;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<String> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<String> passengerList) {
        this.passengerList = passengerList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
