package org.example.dao;

import org.example.model.Dentist;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DentistDAOTest {

    private final DentistDAO dentistDAO = new DentistDAO();

    @Test
    void shouldRetrieveAllDentists() {

        List<Dentist> dentists = dentistDAO.getAllDentists();

        assertNotNull(dentists);
        assertFalse(dentists.isEmpty());
    }
}