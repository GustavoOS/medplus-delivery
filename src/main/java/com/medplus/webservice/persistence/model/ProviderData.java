package com.medplus.webservice.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "provider")
public class ProviderData extends UserData
{

    private String socialMediaURL;
    private String specialization;

    private Double local_latitude = Double.valueOf(0);
    private Double local_longitude = Double.valueOf(0);

    public String getSocialMediaURL()
    {
        return socialMediaURL;
    }

    public String getSpecialization()
    {
        return specialization;
    }

    public Double getLatitude()
    {
        return local_latitude;
    }

    public Double getLongitude()
    {
        return local_longitude;
    }

    public void setSocialMediaURL(String socialMediaURL)
    {
        this.socialMediaURL = socialMediaURL;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    public void setLatitude(Double local_latitude)
    {
        this.local_latitude = local_latitude;
    }

    public void setLongitude(Double local_longitude)
    {
        this.local_longitude = local_longitude;
    }

}
