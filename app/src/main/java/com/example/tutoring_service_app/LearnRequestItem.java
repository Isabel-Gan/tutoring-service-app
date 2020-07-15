package com.example.tutoring_service_app;

public class LearnRequestItem {

    private String subject;
    private String description;
    private boolean status;
    private String requested;
    private int deleteImageSrc;
    private int acceptImageSrc;
    private String databaseId;

    public String getID() {return databaseId;}

    public void setID(String id) {databaseId = id;}

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

    public int getDeleteImageResource() { return deleteImageSrc; }

    public void setDeleteImageResource(int s) { deleteImageSrc = s; }

    public int getAcceptImageResource() { return acceptImageSrc; }

    public void setAcceptImageResource(int s) { acceptImageSrc = s; }

}
