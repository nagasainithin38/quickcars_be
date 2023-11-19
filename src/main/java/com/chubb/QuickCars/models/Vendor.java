package com.chubb.QuickCars.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;




@Document(collection = "vendor")
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    private ObjectId id;

    private  String verificationStatus; // PENDING VERIFIED REJECTED

    private List<ObjectId>vehicleId;

    private String document;
    private String username;
    private int vehicleCount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public List<ObjectId> getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(List<ObjectId> vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }
}
