package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Appointment;
import org.example.model.Patient;
import org.example.service.AppointmentService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService =
            new AppointmentService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String appointmentNumber =
                    request.getParameter("appointmentNumber");

            String patientName =
                    request.getParameter("patientName");

            String address =
                    request.getParameter("address");

            String contactNumber =
                    request.getParameter("contactNumber");

            int dentistId =
                    Integer.parseInt(request.getParameter("dentistId"));

            int treatmentId =
                    Integer.parseInt(request.getParameter("treatmentId"));

            LocalDate appointmentDate =
                    LocalDate.parse(
                            request.getParameter("appointmentDate"));

            LocalTime appointmentTime =
                    LocalTime.parse(
                            request.getParameter("appointmentTime"));

            Patient patient = new Patient(
                    0,
                    patientName,
                    address,
                    contactNumber
            );

            Appointment appointment = new Appointment(
                    0,
                    appointmentNumber,
                    0,
                    dentistId,
                    treatmentId,
                    appointmentDate,
                    appointmentTime
            );

            boolean success =
                    appointmentService.registerAppointment(
                            patient,
                            appointment
                    );

            if (success) {

                response.sendRedirect(
                        "appointment.html?success=true"
                );

            } else {

                response.sendRedirect(
                        "appointment.html?error=true"
                );
            }

        } catch (Exception e) {

            response.sendRedirect(
                    "appointment.html?error=invalid"
            );
        }
    }
}