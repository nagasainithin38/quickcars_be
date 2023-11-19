package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Userr;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<Userr, String> {

    @Query("{'username':?0}")
    Userr findByUsername(String username);

    @Query("{$or:[{'username':?0},{'code':?1}]}")
    List<Userr> findByUsernameOrCode(String username, String code);

    @Query(value = "{'username':?0}",delete = true)
    Userr deleteByUsername(String username);

}
