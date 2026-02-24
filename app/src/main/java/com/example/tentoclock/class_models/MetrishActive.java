package com.example.tentoclock.class_models;

public class MetrishActive {

    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_COMPLETED = "completed";

    private Long id_tentasMeVraxiona;

    private String firebaseKey;
    private String epitheto;
    private String perioxh;
    private String jobType;
    private String summaryText;
    private String status; // "active", "completed"

    // Default constructor (required for Firebase)
    public MetrishActive() {
    }

    // Constructor with parameters
    public MetrishActive(Long id_tentasMeVraxiona, String epitheto, String perioxh, String jobType, String summaryText) {
        this.epitheto = epitheto;
        this.perioxh = perioxh;
        this.jobType = jobType;
        this.summaryText = summaryText;
        this.id_tentasMeVraxiona = id_tentasMeVraxiona;
    }

    // Getters and Setters

    public Long getId_tentasMeVraxiona() {
        return id_tentasMeVraxiona;
    }

    public void setId_tentasMeVraxiona(Long id_tentasMeVraxiona) {
        this.id_tentasMeVraxiona = id_tentasMeVraxiona;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public String getEpitheto() {
        return epitheto;
    }

    public void setEpitheto(String epitheto) {
        this.epitheto = epitheto;
    }

    public String getPerioxh() {
        return perioxh;
    }

    public void setPerioxh(String perioxh) {
        this.perioxh = perioxh;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString()
    @Override
    public String toString() {
        return "MetrishActive{" +
                "firebaseKey='" + firebaseKey + '\'' +
                ", customerName='" + epitheto + '\'' +
                ", customerRegion='" + perioxh + '\'' +
                ", jobType='" + jobType + '\'' +
                ", summaryText='" + summaryText + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}