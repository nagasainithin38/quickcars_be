package com.chubb.QuickCars.services;


import com.chubb.QuickCars.models.Organisation;
import com.chubb.QuickCars.models.Userr;
import com.chubb.QuickCars.models.Vendor;
import com.chubb.QuickCars.repositories.EmployeeRepository;
import com.chubb.QuickCars.repositories.OrganisationRepository;
import com.chubb.QuickCars.repositories.UserRepository;
import com.chubb.QuickCars.repositories.VendorRepository;
import com.chubb.QuickCars.reqresdto.ReqStatus;
import com.chubb.QuickCars.reqresdto.StatMsg;
import com.chubb.QuickCars.reqresdto.UserVenOrg;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    OrganisationRepository organisationRepository;


    public ResponseEntity<?> getPending(String type,String status){
        if(type.equals("vendor")){
            ArrayList<Vendor> vendorPendingList=vendorRepository.findByStatus(status);
            ArrayList<UserVenOrg> result=new ArrayList<UserVenOrg>();
            for(int i=0;i< vendorPendingList.size();i++){
                Userr userr=userRepository.findByUsername(vendorPendingList.get(i).getUsername());
                UserVenOrg res=new UserVenOrg();
                res.setUserr(userr);
                res.setVendor(vendorPendingList.get(i));
                result.add(res);
            }

            return ResponseEntity.ok(result);
        }
        else{
            ArrayList<Organisation> organisationPendingList=organisationRepository.findOrganisationStatus(status);
            ArrayList<UserVenOrg> result=new ArrayList<UserVenOrg>();
            for(int i=0;i< organisationPendingList.size();i++){
                Userr userr=userRepository.findByUsername(organisationPendingList.get(i).getUsername());
                UserVenOrg res=new UserVenOrg();
                res.setUserr(userr);
                res.setOrganisation(organisationPendingList.get(i));
                result.add(res);
            }
            return ResponseEntity.ok(result);
        }
    }




    public ResponseEntity<?> approveReq(String username){
        Userr user=userRepository.findByUsername(username);
        if(user.getRole().equals("vendor")){
            Vendor vendor=vendorRepository.findByUsername(username);
            vendor.setVerificationStatus("VERIFIED");
            vendorRepository.save(vendor);
        return  ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,username+" is verified "));
        }else{
            Organisation organisation=organisationRepository.findByUsername(username);
            organisation.setVerificationStatus("VERIFIED");
            organisationRepository.save(organisation);
            return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,username+" is verified"));
        }

    }



    public ResponseEntity<?> getVendorList(){
        List<Vendor> vendors=vendorRepository.findByStatus("VERIFIED");
        return ResponseEntity.ok(vendors);
    }

    public ResponseEntity<?> getUnAssignedOrgs(){
        List<Organisation> organisations=organisationRepository.findUnassignedOrganisation(false,"VERIFIED");
        return ResponseEntity.ok(organisations);
    }

    public ResponseEntity<?> getAssignedOrgs(){
        List<Organisation> organisations=organisationRepository.findUnassignedOrganisation(true,"VERIFIED");
        return ResponseEntity.ok(organisations);
    }
    public ResponseEntity<?> mapOrgVen(String vendorUsername,String orgUsername){
        Organisation organisation=organisationRepository.findByUsername(orgUsername);
        organisation.setVendorAssigned(true);
        organisation.setVendorUsername(vendorUsername);
        organisationRepository.save(organisation);
        return ResponseEntity.ok(new StatMsg(ReqStatus.SUCCESS,"Mapped successfully"));
    }

}
