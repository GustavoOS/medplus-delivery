package com.medplus.webservice;

import com.fasterxml.jackson.databind.SerializationFeature;

import io.micronaut.jackson.annotation.JacksonFeatures;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition (info = @Info (title = "MedPlusApi", version = "0.0"))
@JacksonFeatures (enabledSerializationFeatures = {}, disabledSerializationFeatures =
{ SerializationFeature.WRITE_DATES_AS_TIMESTAMPS }, enabledDeserializationFeatures = {})
public class Application
{

    public static void main(String[] args)
    {
        Micronaut.run(Application.class, args);
    }
}
