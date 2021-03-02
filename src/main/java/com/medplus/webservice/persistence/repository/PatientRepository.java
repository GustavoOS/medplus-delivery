package com.medplus.webservice.persistence.repository;

import com.medplus.webservice.persistence.model.PatientData;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PatientRepository extends CrudRepository<PatientData, String>
{

}
