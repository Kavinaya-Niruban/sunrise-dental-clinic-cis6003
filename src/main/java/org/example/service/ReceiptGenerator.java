package org.example.service;

import org.example.model.Bill;
import org.example.model.Payment;

public class ReceiptGenerator {

    public String generateReceipt(Bill bill, Payment payment) {

        if (bill == null || payment == null) {
            return "Unable to generate receipt.";
        }

        StringBuilder receipt = new StringBuilder();

        receipt.append("\n");
        receipt.append("========================================\n");
        receipt.append("        SUNRISE DENTAL CLINIC\n");
        receipt.append("             PAYMENT RECEIPT\n");
        receipt.append("========================================\n");

        receipt.append(String.format(
                "Bill ID          : %d%n",
                bill.getBillId()
        ));

        receipt.append(String.format(
                "Appointment ID   : %d%n",
                bill.getAppointmentId()
        ));

        receipt.append("----------------------------------------\n");

        receipt.append(String.format(
                "Treatment Cost   : LKR %.2f%n",
                bill.getTreatmentCost()
        ));

        receipt.append(String.format(
                "Consultation Fee : LKR %.2f%n",
                bill.getConsultationFee()
        ));

        receipt.append(String.format(
                "Total Bill       : LKR %.2f%n",
                bill.getTotalAmount()
        ));

        receipt.append("----------------------------------------\n");

        receipt.append(String.format(
                "Payment Amount   : LKR %.2f%n",
                payment.getPaymentAmount()
        ));

        receipt.append(String.format(
                "Payment Method   : %s%n",
                payment.getPaymentMethod()
        ));

        receipt.append(String.format(
                "Payment Date     : %s%n",
                payment.getPaymentDate()
        ));

        receipt.append("========================================\n");
        receipt.append("       Thank you for choosing us!\n");
        receipt.append("========================================\n");

        return receipt.toString();
    }
}