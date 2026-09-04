package org.example.dao;

import org.example.model.Treatment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreatmentDAOTest {

    private final TreatmentDAO treatmentDAO = new TreatmentDAO();

    @Test
    void shouldRetrieveAllTreatments() {

        List<Treatment> treatments = treatmentDAO.getAllTreatments();

        assertNotNull(treatments);
        assertFalse(treatments.isEmpty());
    }
}