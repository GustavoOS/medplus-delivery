package com.medplus.webservice.persistence.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "patient")
public class PatientData extends UserData
{
    @Column
    LocalDate birth;

    @Column
    Boolean isFemale;

    @JoinColumn (name = "patientId")
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    List<ExamData> exams;

    public LocalDate getBirth()
    {
        return birth;
    }

    public Boolean getIsFemale()
    {
        return isFemale;
    }

    public List<ExamData> getExams()
    {
        return exams;
    }

    public void setBirth(LocalDate birth)
    {
        this.birth = birth;
    }

    public void setIsFemale(Boolean isFemale)
    {
        this.isFemale = isFemale;
    }

    public void setExams(List<ExamData> exams)
    {
        this.exams = exams;
    }

}
