package org.example.service;

import org.example.dao.TreatmentDAO;
import org.example.model.Treatment;

import java.util.List;

public class TreatmentService {

    private final TreatmentDAO treatmentDAO;

    public TreatmentService() {
        treatmentDAO = new TreatmentDAO();
    }

    public List<Treatment> getAllTreatments() {
        return treatmentDAO.getAllTreatments();
    }

    public int addTreatment(Treatment treatment) {

        if (treatment == null) {
            return -1;
        }

        if (treatment.getTreatmentName() == null ||
                treatment.getTreatmentName().trim().isEmpty()) {
            return -1;
        }

        if (treatment.getTreatmentCost() <= 0) {
            return -1;
        }

        if (treatment.getDuration() == null ||
                treatment.getDuration().trim().isEmpty()) {
            return -1;
        }

        if (treatment.getDescription() == null ||
                treatment.getDescription().trim().isEmpty()) {
            return -1;
        }

        treatment.setTreatmentName(
                treatment.getTreatmentName().trim()
        );

        treatment.setDuration(
                treatment.getDuration().trim()
        );

        treatment.setDescription(
                treatment.getDescription().trim()
        );

        return treatmentDAO.addTreatment(treatment);
    }
}