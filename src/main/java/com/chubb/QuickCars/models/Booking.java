package com.chubb.QuickCars.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    private ObjectId id;

    private String MID;
    private String organisationUsername;

    private String date;

    private BookingStatus status;

    private String from;

    private String vehicleUsername;

    private String to;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getOrganisationUsername() {
        return organisationUsername;
    }

    public String getMID() {
        return MID;
    }

    public String getVehicleUsername() {
        return vehicleUsername;
    }

    public void setVehicleUsername(String vehicleUsername) {
        this.vehicleUsername = vehicleUsername;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrganisationUsername(String organisationUsername) {
        this.organisationUsername = organisationUsername;
    }

    public String getData() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
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
}
