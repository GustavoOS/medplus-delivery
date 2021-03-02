package com.medplus.webservice.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import com.medplus.entities.*;
import com.medplus.entities.coordinate.CoordinateDS;
import com.medplus.entities.domain.Appointment;
import com.medplus.entities.domain.Exam;
import com.medplus.entities.domain.HealthProvider;
import com.medplus.entities.domain.Patient;
import com.medplus.entities.exam.changer.ExamFactory;
import com.medplus.entities.provider.impl.Provider;
import com.medplus.factories.AppointmentFactoryImpl;
import com.medplus.factories.PatientFactory;
import com.medplus.useCases.AppointmentFactory;
import com.medplus.webservice.Configuration;
import com.medplus.webservice.persistence.model.AppointmentData;
import com.medplus.webservice.persistence.model.ExamData;
import com.medplus.webservice.persistence.model.PatientData;
import com.medplus.webservice.persistence.model.ProviderData;

@Singleton
public class ModelConverter
{
    private final PatientFactory patientFactory;
    private final ExamFactory examFactory;
    private final AppointmentFactory appointmentFactory = new AppointmentFactoryImpl();

    public ModelConverter(Configuration config)
    {
        this.patientFactory = config.getPatientFactory();
        this.examFactory = config.getExamFactory();
    }

    public Patient convertPatient(PatientData patientData, List<AppointmentData> appointments)
    {
        Patient patient = patientFactory.make();
        patient.setId(patientData.getId());
        patient.setName(patientData.getName());
        patient.setBirth(patientData.getBirth());
        List<Exam> exams = patientData.getExams().stream().map(this::convertExam).collect(Collectors.toList());
        patient.setExams(new ArrayList<>(exams));
        patient.setAppointments(convertPatientAppointmentDataList(appointments));

        return patient;
    }

    public PatientData convertPatient(Patient patient)
    {
        PatientData patientData = new PatientData();
        patientData.setId(patient.getId());
        patientData.setName(patient.getName());
        patientData.setBirth(patient.getBirth());
        List<ExamData> exams = patient.getExams() != null
                ? patient.getExams().stream().map(this::convertExam).collect(Collectors.toList())
                : Collections.emptyList();
        exams.forEach(exam -> exam.setPatientId(patientData.getId()));
        patientData.setExams(exams);
        return patientData;
    }

    public HealthProvider convertProvider(ProviderData providerData, List<AppointmentData> appointments)
    {
        HealthProvider provider = new Provider();
        provider.setId(providerData.getId());
        provider.setName(providerData.getName());
        provider.setSocialMediaURL(providerData.getSocialMediaURL());
        provider.setSpecialization(providerData.getSpecialization());

        if (providerData.getLatitude() != null && providerData.getLongitude() != null)
        {
            provider.setLocal((new CoordinateDS()).with(providerData.getLatitude(), providerData.getLongitude()));
        }

        provider.setAppointments(convertProviderAppointmentDataList(appointments));


        return provider;
    }

    public ProviderData convertProvider(HealthProvider provider)
    {
        ProviderData providerData = new ProviderData();
        providerData.setId(provider.getId());
        providerData.setName(provider.getName());
        providerData.setSocialMediaURL(provider.getSocialMediaURL());
        providerData.setSpecialization(provider.getSpecialization());

        if (provider.getLocal() != null)
        {
            providerData.setLatitude(provider.getLocal().lat());
            providerData.setLongitude(provider.getLocal().lon());
        }

        return providerData;
    }

    public Exam convertExam(ExamData examData)
    {
        return examFactory.make()
                .withId(examData.getId())
                .withTitle(examData.getTitle())
                .withInsertionDateTime(examData.getInsertionDateTime());
    }

    public ExamData convertExam(Exam exam)
    {
        ExamData examData = new ExamData();
        examData.setId(exam.getId());
        examData.setTitle(exam.getTitle());
        examData.setInsertionDateTime(exam.getInsertionDateTime());
        return examData;
    }

    public AppointmentData convertProviderAppointment(Appointment appointment, String providerID) {
        AppointmentData data = new AppointmentData();
        data.setPatientID(appointment.getPeerID());
        data.setProviderID(providerID);
        data.setDateTime(appointment.getDateTime());

        return data;
    }

    public AppointmentData convertPatientAppointment(Appointment appointment, String patientID) {
        AppointmentData data = new AppointmentData();
        data.setPatientID(patientID);
        data.setProviderID(appointment.getPeerID());
        data.setDateTime(appointment.getDateTime());

        return data;
    }

    public ArrayList<Appointment> convertProviderAppointmentDataList(List<AppointmentData> appointments) {

        return appointments.stream().map(app -> {
            return appointmentFactory.make().withDateTime(app.getDateTime()).withPeerID(app.getPatientID());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Appointment> convertPatientAppointmentDataList(List<AppointmentData> appointments) {

        return appointments.stream().map(app -> {
            return appointmentFactory.make().withDateTime(app.getDateTime()).withPeerID(app.getProviderID());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<AppointmentData> convertProviderAppointmentList(List<Appointment> appointments, String providerID) {
        return appointments.stream().map(app -> convertProviderAppointment(app, providerID))
                .collect(Collectors.toList());
    }

    public List<AppointmentData> convertPatientAppointmentList(List<Appointment> appointments, String patientID) {
        return appointments.stream().map(app -> convertProviderAppointment(app, patientID))
                .collect(Collectors.toList());
    }
}
