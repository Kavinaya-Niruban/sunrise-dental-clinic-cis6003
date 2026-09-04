package org.example.service;

import org.example.model.Dentist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DentistServiceTest {

    private final DentistService dentistService =
            new DentistService();

    @Test
    void shouldRejectDentistWithEmptyName() {

        Dentist dentist = new Dentist();

        dentist.setDentistName("");
        dentist.setContactNumber("0771234567");

        int result = dentistService.addDentist(dentist);

        assertEquals(-1, result);
    }

    @Test
    void shouldRejectDentistWithEmptyContactNumber() {

        Dentist dentist = new Dentist();

        dentist.setDentistName("Dr. Silva");
        dentist.setContactNumber("");

        int result = dentistService.addDentist(dentist);

        assertEquals(-1, result);
    }
}