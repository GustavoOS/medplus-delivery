package com.medplus.webservice.adapter;


import com.medplus.entities.domain.Patient;
import com.medplus.entities.domain.User;
import com.medplus.useCases.UserGateway;
import com.medplus.webservice.persistence.model.AppointmentData;
import com.medplus.webservice.persistence.model.PatientData;
import com.medplus.webservice.persistence.repository.AppointmentRepository;
import com.medplus.webservice.persistence.repository.PatientRepository;
import com.medplus.webservice.utils.ModelConverter;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class PatientGWDatabaseAdapter extends UserGatewayCommonAdapter implements UserGateway {

    private final PatientRepository patientRepository;

    public PatientGWDatabaseAdapter(PatientRepository patientRepository,
                                    AppointmentRepository appointmentRepository,
                                    ModelConverter converter)
    {
        super(appointmentRepository, converter);
        this.patientRepository = patientRepository;
    }

    @Override
    public User getUser(String id) {
        Optional<PatientData> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return null;
        }

        List<AppointmentData> appointments = appointmentRepository.findByIdPatientId(id);
        return converter.convertPatient(patient.get(), appointments);
    }

    @Override
    @Transactional
    public void put(User patient) {
        PatientData patientData = converter.convertPatient((Patient) patient);

        List<AppointmentData> oldAppointments = appointmentRepository.findByIdPatientId(patient.getId());
        List<AppointmentData> newAppointments =
                converter.convertPatientAppointmentList(
                        patient.getAppointments(),
                        patient.getId());
        updateAppointments(oldAppointments, newAppointments);

        if (patientRepository.existsById(patientData.getId())) {
            System.out.printf("Patient %s will be updated%n", patient.getId());
            patientRepository.update(patientData);
        } else {
            System.out.printf("Patient will be created with id %s%n", patient.getId());
            patientRepository.save(patientData);
        }
    }

}
