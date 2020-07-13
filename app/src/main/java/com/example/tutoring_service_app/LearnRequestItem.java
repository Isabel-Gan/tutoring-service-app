package com.example.tutoring_service_app;

public class LearnRequestItem {

    private String subject;
    private String description;
    private boolean status;
    private String requested;
    private int imageSrc;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String s) {
        subject = s;
    }

    public String getDescription() { return description;}

    public void setDescription(String s) {description = s;}

    public boolean getStatus() {return status;}

    public void setStatus(boolean b) {status = b;}

    public String getRequested() {return requested;}

    public void setRequested(String r) {requested = r;}

    public int getImageResource() { return imageSrc; }

    public void setImageResource(int s) { imageSrc = s; }

}
