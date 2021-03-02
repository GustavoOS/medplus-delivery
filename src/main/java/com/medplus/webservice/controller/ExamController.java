package com.medplus.webservice.controller;

import com.medplus.adapter.interfaces.AddExamController;
import com.medplus.adapter.interfaces.ManageExamPresenterImpl;
import com.medplus.adapter.interfaces.RemoveExamController;
import com.medplus.entities.exam.changer.ExamAdder;
import com.medplus.entities.exam.changer.ExamAdderImpl;
import com.medplus.entities.exam.changer.ExamRemover;
import com.medplus.entities.exam.changer.impl.ExamRemoverImpl;
import com.medplus.useCases.exam.AddExamUseCase;
import com.medplus.useCases.exam.ManageExamPresenter;
import com.medplus.useCases.exam.RemoveExamUseCase;
import com.medplus.webservice.Configuration;
import com.medplus.webservice.adapter.PatientGWDatabaseAdapter;
import com.medplus.webservice.model.CreateExamRequest;
import com.medplus.webservice.model.Message;
import com.medplus.webservice.model.RemoveExamRequest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

@Validated
@Controller ("/exam")
public class ExamController
{

    private final AddExamController addExamController = new AddExamController();
    private final RemoveExamController removeExamController = new RemoveExamController();

    private final ManageExamPresenter presenter = new ManageExamPresenterImpl();

    ExamController(PatientGWDatabaseAdapter gateway, Configuration config)
    {
        AddExamUseCase addExamUseCase = new AddExamUseCase();
        RemoveExamUseCase removeExamUseCase = new RemoveExamUseCase();

        ExamAdder examAdder = new ExamAdderImpl();
        ExamRemover examRemover = new ExamRemoverImpl();

        examAdder.setExamFactory(config.getExamFactory());

        addExamUseCase.setPresenter(presenter);
        addExamUseCase.setGateway(gateway);
        addExamUseCase.setExamAdder(examAdder);
        addExamController.setUseCase(addExamUseCase);

        removeExamUseCase.setPresenter(presenter);
        removeExamUseCase.setPatientGateway(gateway);
        removeExamUseCase.setRemover(examRemover);
        removeExamController.setUseCase(removeExamUseCase);

    }

    @Post ()
    public HttpResponse<?> addExam(@Body CreateExamRequest examRequest)
    {
        addExamController.add(examRequest.getPatientId(), examRequest.getExamId(), examRequest.getTitle());

        if (presenter.getStatus().equals("success"))
        {
            return HttpResponse.status(HttpStatus.OK)
                    .body(presenter.getResult());
        }
        else
        {
            return HttpResponse.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(HttpStatus.BAD_REQUEST.getCode(), presenter.getStatus()));
        }
    }

    @Delete ()
    public HttpResponse<?> removeExam(@Body RemoveExamRequest examRequest)
    {
        removeExamController.remove(examRequest.getPatientId(), examRequest.getExamId());

        if (presenter.getStatus().equals("success"))
        {
            return HttpResponse.status(HttpStatus.OK)
                    .body(presenter.getResult());
        }
        else
        {
            return HttpResponse.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(HttpStatus.BAD_REQUEST.getCode(), presenter.getStatus()));
        }
    }
}
