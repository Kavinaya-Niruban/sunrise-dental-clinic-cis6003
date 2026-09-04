package org.example.service;

import org.example.model.Treatment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreatmentServiceTest {

    private final TreatmentService treatmentService =
            new TreatmentService();

    @Test
    void shouldRejectTreatmentWithInvalidCost() {

        Treatment treatment = new Treatment(
                0,
                "Root Canal Treatment",
                0,
                "90 min",
                "Endodontic treatment"
        );

        int result = treatmentService.addTreatment(treatment);

        assertEquals(-1, result);
    }
}