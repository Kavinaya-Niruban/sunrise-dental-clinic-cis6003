package org.example.service;

import org.example.dao.AppointmentDAO;
import org.example.dao.BillDAO;
import org.example.model.Appointment;
import org.example.model.Bill;

import java.util.List;

public class BillService {

    private final BillDAO billDAO;
    private final AppointmentDAO appointmentDAO;

    public BillService() {
        billDAO = new BillDAO();
        appointmentDAO = new AppointmentDAO();
    }

    public Appointment findAppointment(String appointmentNumber) {

        if (appointmentNumber == null ||
                appointmentNumber.trim().isEmpty()) {

            return null;
        }

        return appointmentDAO.findByAppointmentNumber(
                appointmentNumber.trim()
        );
    }

    public boolean createBill(
            String appointmentNumber,
            double treatmentCost,
            double consultationFee) {

        if (appointmentNumber == null ||
                appointmentNumber.trim().isEmpty()) {

            return false;
        }

        if (treatmentCost < 0 ||
                consultationFee < 0) {

            return false;
        }

        Appointment appointment =
                appointmentDAO.findByAppointmentNumber(
                        appointmentNumber.trim()
                );

        if (appointment == null) {
            return false;
        }

        double totalAmount =
                treatmentCost + consultationFee;

        Bill bill = new Bill(
                0,
                appointment.getAppointmentId(),
                appointment.getAppointmentNumber(),
                appointment.getDentistName(),
                appointment.getTreatmentName(),
                treatmentCost,
                consultationFee,
                totalAmount
        );

        return billDAO.addBill(bill);
    }

    public List<Bill> getAllBills() {
        return billDAO.getAllBills();
    }
}