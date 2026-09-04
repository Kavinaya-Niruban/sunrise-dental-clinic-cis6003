package org.example.service;

import org.example.dao.PatientDAO;
import org.example.model.Patient;

import java.util.List;

public class PatientService {

    private final PatientDAO patientDAO;

    public PatientService() {
        patientDAO = new PatientDAO();
    }

    public int registerPatient(Patient patient) {

        if (patient == null) {
            return -1;
        }

        if (patient.getPatientName() == null ||
                patient.getPatientName().trim().isEmpty()) {
            return -1;
        }

        if (patient.getAddress() == null ||
                patient.getAddress().trim().isEmpty()) {
            return -1;
        }

        if (patient.getContactNumber() == null ||
                patient.getContactNumber().trim().isEmpty()) {
            return -1;
        }

        patient.setPatientName(patient.getPatientName().trim());
        patient.setAddress(patient.getAddress().trim());
        patient.setContactNumber(patient.getContactNumber().trim());

        return patientDAO.addPatient(patient);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }
}