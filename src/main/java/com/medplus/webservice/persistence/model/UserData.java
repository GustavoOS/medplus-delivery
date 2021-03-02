package com.medplus.webservice.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "user")
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public class UserData
{

    @Id
    @Column (name = "id", updatable = false, nullable = false)
    protected String id;

    @NotNull
    @Column (name = "name", nullable = false)
    protected String name;

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
