package org.example.service;

import org.example.dao.BillDAO;
import org.example.model.Bill;

public class BillingService {

    private final BillDAO billDAO;

    public BillingService() {
        billDAO = new BillDAO();
    }

    public Bill calculateBill(
            int appointmentId,
            double treatmentCost,
            double consultationFee) {

        if (appointmentId <= 0) {
            return null;
        }

        if (treatmentCost < 0 || consultationFee < 0) {
            return null;
        }

        double totalAmount = treatmentCost + consultationFee;

        return new Bill(
                0,
                appointmentId,
                treatmentCost,
                consultationFee,
                totalAmount
        );
    }

    public boolean saveBill(Bill bill) {

        if (bill == null) {
            return false;
        }

        if (bill.getAppointmentId() <= 0) {
            return false;
        }

        if (bill.getTreatmentCost() < 0 ||
                bill.getConsultationFee() < 0) {
            return false;
        }

        return billDAO.addBill(bill);
    }

    public Bill getBill(int appointmentId) {

        if (appointmentId <= 0) {
            return null;
        }

        return billDAO.findByAppointmentId(appointmentId);
    }
}