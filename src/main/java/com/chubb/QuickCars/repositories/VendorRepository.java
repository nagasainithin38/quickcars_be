package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Userr;
import com.chubb.QuickCars.models.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

public interface VendorRepository extends MongoRepository<Vendor,String> {

    @Query("{'username':?0}")
    Vendor findByUsername(String username);


    @Query("{'verificationStatus':?0}")
    ArrayList<Vendor> findByStatus(String status);
}
