package com.medplus.webservice.model;

public class Message
{
    private final int status;
    private final String message;

    public Message(int status, String message)
    {
        super();
        this.status = status;
        this.message = message;
    }

    public int getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }

}
