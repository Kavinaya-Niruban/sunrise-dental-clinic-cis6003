package org.example.model;

import java.time.LocalDateTime;

public class Payment {

    private int paymentId;
    private int billId;
    private double paymentAmount;
    private String paymentMethod;
    private LocalDateTime paymentDate;

    public Payment() {
    }

    public Payment(int paymentId, int billId,
                   double paymentAmount, String paymentMethod,
                   LocalDateTime paymentDate) {

        this.paymentId = paymentId;
        this.billId = billId;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}