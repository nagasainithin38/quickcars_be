package com.chubb.QuickCars.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "organisation")
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {

    @Id
    private ObjectId id;

    private String verificationStatus; // PENDING VERIFIED REJECTED
    private String username;
    private String address;
    private boolean vendorAssigned;

    private String vendorUsername;

    private String businessType;

    private  String description;

    private String startTiming;

    private  String endTiming;

    private  String websiteLink;

    private List<ObjectId> employeesList;

    private String code;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isVendorAssigned() {
        return vendorAssigned;
    }

    public void setVendorAssigned(boolean vendorAssigned) {
        this.vendorAssigned = vendorAssigned;
    }

    public String getVendorUsername() {
        return vendorUsername;
    }

    public void setVendorUsername(String vendorUsername) {
        this.vendorUsername = vendorUsername;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTiming() {
        return startTiming;
    }

    public void setStartTiming(String startTiming) {
        this.startTiming = startTiming;
    }

    public String getEndTiming() {
        return endTiming;
    }

    public void setEndTiming(String endTiming) {
        this.endTiming = endTiming;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public List<ObjectId> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<ObjectId> employeesList) {
        this.employeesList = employeesList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
