package org.example.service;

import org.example.dao.AppointmentDAO;
import org.example.model.Appointment;

import java.util.List;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO;

    public AppointmentService() {
        appointmentDAO = new AppointmentDAO();
    }

    public boolean registerAppointment(
            Appointment appointment) {

        if (appointment == null) {
            return false;
        }

        if (!isValidAppointment(appointment)) {
            return false;
        }

        return appointmentDAO.addAppointment(appointment);
    }

    public List<Appointment> getAllAppointments() {

        return appointmentDAO.getAllAppointments();
    }

    public Appointment searchAppointment(
            String appointmentNumber) {

        if (appointmentNumber == null ||
                appointmentNumber.trim().isEmpty()) {

            return null;
        }

        return appointmentDAO.findByAppointmentNumber(
                appointmentNumber.trim()
        );
    }

    private boolean isValidAppointment(
            Appointment appointment) {

        return appointment.getAppointmentNumber() != null &&
                !appointment.getAppointmentNumber()
                        .trim().isEmpty() &&

                appointment.getPatientName() != null &&
                !appointment.getPatientName()
                        .trim().isEmpty() &&

                appointment.getAddress() != null &&
                !appointment.getAddress()
                        .trim().isEmpty() &&

                appointment.getContactNumber() != null &&
                !appointment.getContactNumber()
                        .trim().isEmpty() &&

                appointment.getAppointmentDate() != null &&

                appointment.getAppointmentTime() != null &&

                appointment.getDentistId() > 0 &&

                appointment.getTreatmentId() > 0;
    }
}