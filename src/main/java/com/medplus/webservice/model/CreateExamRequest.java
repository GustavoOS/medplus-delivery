package com.medplus.webservice.model;

public class CreateExamRequest
{
    private String examId, title;
    private String patientId;

    public String getExamId()
    {
        return examId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPatientId()
    {
        return patientId;
    }

}
