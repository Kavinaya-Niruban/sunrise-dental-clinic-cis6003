package org.example.model;

public class Dentist {

    private int dentistId;
    private String dentistName;
    private String contactNumber;

    public Dentist() {
    }

    public Dentist(int dentistId, String dentistName, String contactNumber) {
        this.dentistId = dentistId;
        this.dentistName = dentistName;
        this.contactNumber = contactNumber;
    }

    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}