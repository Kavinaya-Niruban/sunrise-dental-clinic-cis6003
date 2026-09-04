package org.example.service;

import org.example.dao.BillDAO;
import org.example.model.Bill;

import java.util.List;

public class BillService {

    private final BillDAO billDAO;

    public BillService() {
        billDAO = new BillDAO();
    }

    public boolean createBill(Bill bill) {

        if (bill == null) {
            return false;
        }

        if (bill.getAppointmentId() <= 0) {
            return false;
        }

        if (bill.getTreatmentCost() < 0) {
            return false;
        }

        if (bill.getConsultationFee() < 0) {
            return false;
        }

        double total =
                bill.getTreatmentCost()
                        + bill.getConsultationFee();

        bill.setTotalAmount(total);

        return billDAO.addBill(bill);
    }

    public List<Bill> getAllBills() {

        return billDAO.getAllBills();
    }
}