package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

public interface EmployeeRepository extends MongoRepository<Employee,String> {


    @Query("{'username':?0}")
    Employee findByUsername(String username);

    @Query("{'to_home_ride':?0}")
    Employee findByRideId(String rideId);

    @Query("{'org_username':?0}")
    ArrayList<Employee> findAllEmpByUsername(String username);

    @Query(value = "{'username':?0}",delete = true)
    Employee deleteEmp(String username);

}
