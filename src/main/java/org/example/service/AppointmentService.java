package org.example.service;

import org.example.dao.AppointmentDAO;
import org.example.dao.PatientDAO;
import org.example.model.Appointment;
import org.example.model.Patient;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;

    public AppointmentService() {
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
    }

    public boolean registerAppointment(
            Patient patient,
            Appointment appointment) {

        if (patient == null || appointment == null) {
            return false;
        }

        if (!isValidPatient(patient)) {
            return false;
        }

        if (!isValidAppointment(appointment)) {
            return false;
        }

        int patientId = patientDAO.addPatient(patient);

        if (patientId == -1) {
            return false;
        }

        appointment.setPatientId(patientId);

        return appointmentDAO.addAppointment(appointment);
    }

    public Appointment searchAppointment(String appointmentNumber) {

        if (appointmentNumber == null ||
                appointmentNumber.trim().isEmpty()) {
            return null;
        }

        return appointmentDAO.findByAppointmentNumber(
                appointmentNumber.trim()
        );
    }

    private boolean isValidPatient(Patient patient) {

        return patient.getPatientName() != null &&
                !patient.getPatientName().trim().isEmpty() &&
                patient.getAddress() != null &&
                !patient.getAddress().trim().isEmpty() &&
                patient.getContactNumber() != null &&
                !patient.getContactNumber().trim().isEmpty();
    }

    private boolean isValidAppointment(Appointment appointment) {

        return appointment.getAppointmentNumber() != null &&
                !appointment.getAppointmentNumber().trim().isEmpty() &&
                appointment.getAppointmentDate() != null &&
                appointment.getAppointmentTime() != null &&
                appointment.getDentistId() > 0 &&
                appointment.getTreatmentId() > 0;
    }
}