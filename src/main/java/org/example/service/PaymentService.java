package org.example.service;

import org.example.dao.BillDAO;
import org.example.dao.PaymentDAO;
import org.example.model.Bill;
import org.example.model.Payment;

import java.time.LocalDateTime;

public class PaymentService {

    private final PaymentDAO paymentDAO;
    private final BillDAO billDAO;

    public PaymentService() {
        paymentDAO = new PaymentDAO();
        billDAO = new BillDAO();
    }

    public Payment processPayment(int billId,
                                  double paymentAmount,
                                  String paymentMethod) {

        if (billId <= 0) {
            return null;
        }

        if (paymentAmount <= 0) {
            return null;
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            return null;
        }

        Bill bill = billDAO.findById(billId);

        if (bill == null) {
            return null;
        }

        if (paymentAmount > bill.getTotalAmount()) {
            return null;
        }

        Payment payment = new Payment(
                0,
                billId,
                paymentAmount,
                paymentMethod.trim().toUpperCase(),
                LocalDateTime.now()
        );

        if (paymentDAO.addPayment(payment)) {
            return payment;
        }

        return null;
    }

    public Payment getPayment(int billId) {

        if (billId <= 0) {
            return null;
        }

        return paymentDAO.findByBillId(billId);
    }
}