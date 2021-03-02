package com.medplus.webservice.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "exam")
public class ExamData
{

    @Id
    @Column (name = "id", updatable = false, nullable = false)
    protected String id;

    @Column (name = "patientId")
    protected String patientId;

    @NotNull
    @Column (name = "title", nullable = false)
    protected String title;

    @NotNull
    @Column (name = "insertionDateTime", nullable = false)
    protected LocalDateTime insertionDateTime;

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public LocalDateTime getInsertionDateTime()
    {
        return insertionDateTime;
    }

    public String getPatientId()
    {
        return patientId;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setInsertionDateTime(LocalDateTime insertionDateTime)
    {
        this.insertionDateTime = insertionDateTime;
    }

    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
    }

}
