package com.medplus.webservice.impl;

import javax.inject.Singleton;

import com.medplus.entities.exam.changer.ExamFactory;
import com.medplus.entities.exam.changer.impl.ExamFactoryImpl;
import com.medplus.factories.PatientFactory;
import com.medplus.factories.PatientFactoryImpl;
import com.medplus.webservice.Configuration;

@Singleton
public class ConfigurationImpl implements Configuration
{

    @Override
    public PatientFactory getPatientFactory()
    {
        return new PatientFactoryImpl();
    }

    @Override
    public ExamFactory getExamFactory()
    {
        return new ExamFactoryImpl();
    }

}
