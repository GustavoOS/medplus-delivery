package com.medplus.webservice.persistence.repository;

import com.medplus.webservice.persistence.model.ProviderData;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ProviderRepository extends CrudRepository<ProviderData, String>
{
    
}
