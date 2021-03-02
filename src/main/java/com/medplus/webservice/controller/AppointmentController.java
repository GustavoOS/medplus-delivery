package com.medplus.webservice.controller;

import com.medplus.adapter.interfaces.BookController;
import com.medplus.adapter.interfaces.CancelAppointmentPresenterImpl;
import com.medplus.adapter.interfaces.SchedulePresenterImpl;
import com.medplus.entities.*;
import com.medplus.entities.appointment.AppointmentImpl;
import com.medplus.entities.scheduler.DayScheduler;
import com.medplus.entities.scheduler.DaySchedulerImpl;
import com.medplus.useCases.CancelAppointmentPresenter;
import com.medplus.useCases.book.BookAppointmentUseCase;
import com.medplus.useCases.cancelation.CancelAppointmentUseCase;
import com.medplus.webservice.adapter.PatientGWDatabaseAdapter;
import com.medplus.webservice.adapter.ProviderGWDatabaseAdapter;
import com.medplus.webservice.model.CancelAppointmentRequest;
import com.medplus.webservice.model.CreateAppointmentRequest;
import com.medplus.webservice.model.Message;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

import javax.validation.Valid;

@Validated
@Controller("/appointment")
public class AppointmentController {
    private final SchedulePresenterImpl presenter = new SchedulePresenterImpl();
    private final BookAppointmentUseCase bookAppointmentUseCase = new BookAppointmentUseCase();
    private final CancelAppointmentUseCase cancelPatientAppointmentUseCase = new CancelAppointmentUseCase();
    private final CancelAppointmentUseCase cancelProviderAppointmentUseCase = new CancelAppointmentUseCase();
    private final BookController bookController = new BookController();


    AppointmentController(PatientGWDatabaseAdapter patientGW, ProviderGWDatabaseAdapter providerGw) {
        bookAppointmentUseCase.setPresenter(presenter);
        DayScheduler dayScheduler = new DaySchedulerImpl();
        bookAppointmentUseCase.setDaySchedule(dayScheduler);
        bookAppointmentUseCase.setPatientGW(patientGW);
        bookAppointmentUseCase.setProviderGW(providerGw);
        bookController.setUseCase(bookAppointmentUseCase);

        CancelAppointmentPresenter cancelAppointmentPresenter = new CancelAppointmentPresenterImpl();
        cancelPatientAppointmentUseCase.setPresenter(cancelAppointmentPresenter);
        cancelPatientAppointmentUseCase.setUserGW(patientGW);
        cancelPatientAppointmentUseCase.setPeerGW(providerGw);

        cancelProviderAppointmentUseCase.setPresenter(cancelAppointmentPresenter);
        cancelProviderAppointmentUseCase.setUserGW(providerGw);
        cancelProviderAppointmentUseCase.setPeerGW(patientGW);
    }

    @Post("/book")
    public HttpResponse<?> book(@Body CreateAppointmentRequest appointment) {
        bookController.setAppointment(
                appointment.getPatientID(),
                appointment.getProviderId(),
                appointment.getDateTime());
        boolean success = presenter.getStatus().equals("success");
        String message = success ? "Appointment booked successfully." : "The booking failed";
        HttpStatus code = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return HttpResponse.status(code).body(new Message(code.getCode(), message));
    }

    @Post("/patient/cancel")
    public HttpResponse<?> cancelPatient(@Valid @Body CancelAppointmentRequest req) {
        cancelPatientAppointmentUseCase.cancel(req.getId(), req.getDateTime());

        boolean success = presenter.getStatus().equals("success");
        String message = success ? "Appointment canceled successfully." : "The cancellation failed";
        HttpStatus code = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return HttpResponse.status(code).body(new Message(code.getCode(), message));
    }

    @Post("/provider/cancel")
    public HttpResponse<?> cancelProvider(@Valid @Body CancelAppointmentRequest req) {
        cancelProviderAppointmentUseCase.cancel(req.getId(), req.getDateTime());

        boolean success = presenter.getStatus().equals("success");
        String message = success ? "Appointment canceled successfully." : "The cancellation failed";
        HttpStatus code = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return HttpResponse.status(code).body(new Message(code.getCode(), message));
    }
}
