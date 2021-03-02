package com.medplus.webservice.adapter;

import com.medplus.entities.domain.User;
import com.medplus.webservice.persistence.model.AppointmentData;
import com.medplus.webservice.persistence.repository.AppointmentRepository;
import com.medplus.webservice.utils.ModelConverter;

import java.util.List;
import java.util.stream.Collectors;

public class UserGatewayCommonAdapter {

    protected final ModelConverter converter;
    protected final AppointmentRepository appointmentRepository;

    public UserGatewayCommonAdapter(AppointmentRepository appointmentRepository, ModelConverter converter) {
        this.converter = converter;
        this.appointmentRepository = appointmentRepository;
    }

    protected void updateAppointments(List<AppointmentData> oldAppointments, List<AppointmentData> newAppointments) {
        List<AppointmentData.AppointmentDataId> oldIds = oldAppointments.stream().map(AppointmentData::getId).collect(Collectors.toList());
        List<AppointmentData.AppointmentDataId> newIds = newAppointments.stream().map(AppointmentData::getId).collect(Collectors.toList());

        List<AppointmentData> toDelete = oldAppointments.stream().filter(old -> !newIds.contains(old.getId())).collect(Collectors.toList());
        List<AppointmentData> toCreate = newAppointments.stream().filter(app -> !oldIds.contains(app.getId())).collect(Collectors.toList());

        appointmentRepository.deleteAll(toDelete);
        appointmentRepository.saveAll(toCreate);
    }

}
