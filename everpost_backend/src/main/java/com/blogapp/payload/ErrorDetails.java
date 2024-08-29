package com.blogapp.payload;

import java.util.Date;

public class ErrorDetails {
    private Date date;
    private String name;
    private String details;

    public ErrorDetails(Date date, String name, String details) {
        this.date = date;
        this.name = name;
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }
}
