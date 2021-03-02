package com.medplus.webservice.persistence.repository;

import com.medplus.webservice.persistence.model.AppointmentData;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<AppointmentData, AppointmentData.AppointmentDataId> {
    List<AppointmentData> findByIdPatientId(String patientId);
    List<AppointmentData> findByIdProviderId(String providerId);
}
