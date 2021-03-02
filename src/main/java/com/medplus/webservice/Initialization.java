package com.medplus.webservice;

import java.util.ArrayList;

import javax.inject.Singleton;


import com.medplus.entities.domain.HealthProvider;
import com.medplus.entities.domain.Patient;
import com.medplus.factories.TestUtils;
import com.medplus.webservice.adapter.PatientGWDatabaseAdapter;
import com.medplus.webservice.adapter.ProviderGWDatabaseAdapter;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;

@Singleton
public class Initialization
{
    private final PatientGWDatabaseAdapter patientGW;
    private final ProviderGWDatabaseAdapter providerGW;

    public Initialization(PatientGWDatabaseAdapter patientGW, ProviderGWDatabaseAdapter providerGW)
    {
        this.patientGW = patientGW;
        this.providerGW = providerGW;
    }

    @EventListener
    void onStartup(StartupEvent event)
    {
        this.initializePatients();
    }

    private void initializePatients()
    {
        System.out.println("Initialize patients");

        ArrayList<Patient> patients = TestUtils.mountPatientList();
        patients.get(0).setExams(TestUtils.mountExamList());

        patients.stream().forEach(this::addPatient);

        ArrayList<HealthProvider> providers = TestUtils.mountProviderList();

        providers.stream().forEach(this::addProvider);

    }

    private void addPatient(Patient patient)
    {
        if (patientGW.getUser(patient.getId()) == null)
        {
            patientGW.put(patient);
        }
    }

    private void addProvider(HealthProvider patient)
    {
        if (providerGW.getUser(patient.getId()) == null)
        {
            providerGW.put(patient);
        }
    }
}
