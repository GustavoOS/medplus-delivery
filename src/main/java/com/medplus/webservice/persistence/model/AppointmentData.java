package com.medplus.webservice.persistence.model;

import com.medplus.entities.domain.Appointment;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointment", indexes = {
        @Index(name = "IDX_APPOINTMENT_PATIENT", columnList = "patientId"),
        @Index(name = "IDX_APPOINTMENT_PROVIDER", columnList = "providerId")
})
public class AppointmentData{

    @EmbeddedId
    private AppointmentDataId id;

    public AppointmentData() {
        id = new AppointmentDataId();
    }

    public AppointmentDataId getId() {
        return id;
    }

    public void setId(AppointmentDataId id) {
        this.id = id;
    }

    public String getPatientID() {
        return id.getPatientId();
    }

    public void setPatientID(String patientId) {
        id.setPatientId(patientId);
    }

    public String getProviderID() {
        return id.getProviderId();
    }

    public void setProviderID(String providerId) {
        id.setProviderId(providerId);
    }

    public LocalDateTime getDateTime() {
        return id.getDateTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        id.setDateTime(dateTime);
    }

    @Embeddable
    public static class AppointmentDataId implements Serializable {
        private String patientId;
        private String providerId;
        private LocalDateTime dateTime;

        public AppointmentDataId() {
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AppointmentDataId that = (AppointmentDataId) o;
            return Objects.equals(patientId, that.patientId) &&
                    Objects.equals(providerId, that.providerId) &&
                    Objects.equals(dateTime, that.dateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(patientId, providerId, dateTime);
        }
    }
}
