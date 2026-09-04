package org.example.service;

import org.example.model.Bill;

public class ReceiptGenerator {

    public String generateReceipt(Bill bill) {

        if (bill == null) {
            return "Unable to generate receipt.";
        }

        StringBuilder receipt = new StringBuilder();

        receipt.append("\n");
        receipt.append("========================================\n");
        receipt.append("        SUNRISE DENTAL CLINIC\n");
        receipt.append("             BILL RECEIPT\n");
        receipt.append("========================================\n");

        receipt.append(String.format(
                "Bill ID          : %d%n",
                bill.getBillId()
        ));

        receipt.append(String.format(
                "Appointment No.  : %s%n",
                bill.getAppointmentNumber()
        ));

        receipt.append(String.format(
                "Dentist          : %s%n",
                bill.getDentistName()
        ));

        receipt.append(String.format(
                "Treatment        : %s%n",
                bill.getTreatmentName()
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

        receipt.append("----------------------------------------\n");

        receipt.append(String.format(
                "TOTAL BILL       : LKR %.2f%n",
                bill.getTotalAmount()
        ));

        receipt.append("========================================\n");
        receipt.append("       Thank you for choosing us!\n");
        receipt.append("========================================\n");

        return receipt.toString();
    }
}