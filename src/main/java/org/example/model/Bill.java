package org.example.model;

public class Bill {

    private int billId;
    private int appointmentId;
    private String appointmentNumber;
    private String dentistName;
    private String treatmentName;
    private double treatmentCost;
    private double consultationFee;
    private double totalAmount;

    public Bill() {
    }

    public Bill(
            int billId,
            int appointmentId,
            String appointmentNumber,
            String dentistName,
            String treatmentName,
            double treatmentCost,
            double consultationFee,
            double totalAmount) {

        this.billId = billId;
        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;
        this.dentistName = dentistName;
        this.treatmentName = treatmentName;
        this.treatmentCost = treatmentCost;
        this.consultationFee = consultationFee;
        this.totalAmount = totalAmount;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}