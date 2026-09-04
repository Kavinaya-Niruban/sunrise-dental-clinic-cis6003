package org.example.service;

import org.example.dao.DentistDAO;
import org.example.model.Dentist;

import java.util.List;

public class DentistService {

    private final DentistDAO dentistDAO;

    public DentistService() {
        dentistDAO = new DentistDAO();
    }

    public List<Dentist> getAllDentists() {
        return dentistDAO.getAllDentists();
    }

    public int addDentist(Dentist dentist) {

        if (dentist == null) {
            return -1;
        }

        if (dentist.getDentistName() == null ||
                dentist.getDentistName().trim().isEmpty()) {
            return -1;
        }

        if (dentist.getContactNumber() == null ||
                dentist.getContactNumber().trim().isEmpty()) {
            return -1;
        }

        dentist.setDentistName(dentist.getDentistName().trim());
        dentist.setContactNumber(dentist.getContactNumber().trim());

        return dentistDAO.addDentist(dentist);
    }
}