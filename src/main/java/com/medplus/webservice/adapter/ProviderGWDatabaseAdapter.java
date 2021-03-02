package com.medplus.webservice.adapter;

import com.medplus.entities.domain.HealthProvider;
import com.medplus.entities.provider.impl.Provider;
import com.medplus.entities.domain.User;
import com.medplus.useCases.ProviderGateway;
import com.medplus.webservice.persistence.model.AppointmentData;
import com.medplus.webservice.persistence.model.ProviderData;
import com.medplus.webservice.persistence.repository.AppointmentRepository;
import com.medplus.webservice.persistence.repository.ProviderRepository;
import com.medplus.webservice.utils.ModelConverter;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProviderGWDatabaseAdapter extends UserGatewayCommonAdapter implements ProviderGateway {

    private final ProviderRepository providerRepository;

    public ProviderGWDatabaseAdapter(ProviderRepository providerRepository, AppointmentRepository appointmentRepository, ModelConverter converter) {
        super(appointmentRepository, converter);
        this.providerRepository = providerRepository;
    }

    @Override
    public User getUser(String id) {
        Optional<ProviderData> provider = providerRepository.findById(id);

        if (provider.isEmpty()) {
            return null;
        }

        return convertProvider(provider.get());
    }

    private HealthProvider convertProvider(ProviderData provider) {
        List<AppointmentData> appointments = appointmentRepository.findByIdProviderId(provider.getId());
        return converter.convertProvider(provider, appointments);
    }

    @Override
    @Transactional // Rollback caso uma das transações falhe
    public void put(User provider) {
        ProviderData providerData = converter.convertProvider((Provider) provider);

        List<AppointmentData> oldAppointments = appointmentRepository.findByIdProviderId(provider.getId());

        List<AppointmentData> newAppointments =
                converter.convertProviderAppointmentList(
                        provider.getAppointments(),
                        provider.getId());
        updateAppointments(oldAppointments, newAppointments);

        if (providerRepository.existsById(providerData.getId())) {
            System.out.printf("Provider %s will be updated%n", provider.getId());
            providerRepository.update(providerData);
        } else {
            System.out.printf("Provider will be created with id %s%n", provider.getId());
            providerRepository.save(providerData);
        }
    }

    @Override
    public ArrayList<HealthProvider> list() {
        ArrayList<HealthProvider> resultList = new ArrayList<>();
        providerRepository.findAll().forEach(providerData -> {
            resultList.add(convertProvider(providerData));
        });
        return resultList;
    }

}
