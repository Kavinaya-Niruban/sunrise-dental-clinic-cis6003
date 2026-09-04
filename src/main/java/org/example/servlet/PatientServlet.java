package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Patient;
import org.example.service.PatientService;

import java.io.IOException;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {


    private PatientService patientService;

    @Override
    public void init() throws ServletException {
        patientService = new PatientService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String patientName = request.getParameter("patientName");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");

        Patient patient = new Patient();

        patient.setPatientName(patientName);
        patient.setAddress(address);
        patient.setContactNumber(contactNumber);

        int patientId = patientService.registerPatient(patient);

        if (patientId > 0) {

            response.sendRedirect(
                    "patients.html?success=true&patientId=" + patientId
            );

        } else {

            response.sendRedirect(
                    "patients.html?error=true"
            );
        }
    }

}
