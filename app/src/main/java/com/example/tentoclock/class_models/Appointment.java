package com.example.tentoclock.class_models;

import java.util.Date;

public class Appointment {
    private String appointmentId;
    private String clientId;
    private String date; // You might want to use a Date object later
    private String time;
    private String typeOfWork;
    private String notes;
    private String status;

    public Appointment() {
        // Default constructor required for Firebase
    }

    public Appointment(String appointmentId, String clientId, String date, String time, String typeOfWork, String notes, String status) {
        this.appointmentId = appointmentId;
        this.clientId = clientId;
        this.date = date;
        this.time = time;
        this.typeOfWork = typeOfWork;
        this.notes = notes;
        this.status = status;
    }

    // Getters and setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}