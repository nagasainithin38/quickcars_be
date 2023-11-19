package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle,String> {

    @Query("{'username':?0}")
    Vehicle findByUsername(String username);

    @Query("{'vendor_username':?0}")
    List<Vehicle> findByVendorUsername(String vendorUsername);

    @Query(value = "{'username':?0}",delete = true)
    Vehicle deleteByusername(String username);


}
