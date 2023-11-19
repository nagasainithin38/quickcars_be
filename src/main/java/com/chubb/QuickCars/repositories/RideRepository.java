package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RideRepository extends MongoRepository<Ride,String> {


    @Query("{'mid':?0}")
    Ride findByMid(String Mid);

    @Query(value = "{'mid':?0}",delete = true)
    Ride deleteByMid(String mid);


}
