package com.chubb.QuickCars.repositories;

import com.chubb.QuickCars.models.Organisation;
import com.chubb.QuickCars.models.Userr;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface OrganisationRepository extends MongoRepository<Organisation,String> {

    @Query("{'username':?0}")
    Organisation findByUsername(String username);

    @Query( value = "{'vendorUsername':?0}",fields = "{username:1}")
    List<Organisation> findOrganisationsByVendorUsername(String username);

    @Query("{'verificationStatus':?0}")
    ArrayList<Organisation> findOrganisationStatus(String status);

    @Query("{'vendorAssigned':?0,'verificationStatus':?1}")
    ArrayList<Organisation> findUnassignedOrganisation(boolean status,String vStatus);

}
