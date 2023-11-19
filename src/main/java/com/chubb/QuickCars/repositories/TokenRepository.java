package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<Token,String> {

    @Query("{username:?0}")
    Token findByUsername(String username);

    @Query(value = "{username:?0}",delete = true)
    Token deleteByusername(String Username);


    @Query(value = "{token:?0}")
    Token findByToken(String token);

}
