package com.medplus.webservice.model;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Introspected
public class CancelAppointmentRequest {
    @NotNull
    private String id;
    @NotNull
    private LocalDateTime dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
