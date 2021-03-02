package com.medplus.webservice.controller;

import com.medplus.adapter.interfaces.AttendancePresenterImpl;
import com.medplus.adapter.interfaces.CheckPatientAvailableDataController;
import com.medplus.useCases.AttendancePresenter;
import com.medplus.useCases.CheckAvailablePatientDataUseCase;
import com.medplus.useCases.PatientAvailableDataDS;
import com.medplus.webservice.adapter.PatientGWDatabaseAdapter;
import com.medplus.webservice.model.Message;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;

@Validated
@Controller ("/patient")
public class PatientController
{
    private final AttendancePresenter presenter = new AttendancePresenterImpl();
    private final CheckPatientAvailableDataController checkPatientController =
            new CheckPatientAvailableDataController();

    PatientController(PatientGWDatabaseAdapter gateway)
    {
        CheckAvailablePatientDataUseCase checkPatientUseCase = new CheckAvailablePatientDataUseCase();;

        checkPatientUseCase.setPresenter(presenter);
        checkPatientUseCase.setPatientGateway(gateway);
        checkPatientController.setUseCase(checkPatientUseCase);
    }

    @Get ("/{patientId}")
    public HttpResponse<?> get(String patientId)
    {
        checkPatientController.check(patientId);

        if (presenter.getStatus().equals("success"))
        {
            PatientAvailableDataDS data = presenter.getData();
            return HttpResponse.status(HttpStatus.OK).body(data);
        }
        else
        {
            Message msg = new Message(HttpStatus.NOT_FOUND.getCode(), "Patient not found.");
            return HttpResponse.status(HttpStatus.NOT_FOUND).body(msg);
        }

    }
}
