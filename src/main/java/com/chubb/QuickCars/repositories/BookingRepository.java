package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking,String> {


    @Query(value = "{'organisationUsername':?0,'status':?1}",sort = "{'from':1}")
    List<Booking> findAllPendingBookingsByUsername(String organisationUsername,String status);

    @Query(value = "{'organisationUsername':?0,'status':?1}",sort = "{'to':1}")
    List<Booking> findAllPendingBookingsByUsernameT(String organisationUsername,String status);

}