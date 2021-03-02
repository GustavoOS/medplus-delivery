package com.medplus.webservice;

import com.medplus.entities.exam.changer.ExamFactory;
import com.medplus.factories.PatientFactory;

public interface Configuration
{
    PatientFactory getPatientFactory();
    
    ExamFactory getExamFactory();
}
