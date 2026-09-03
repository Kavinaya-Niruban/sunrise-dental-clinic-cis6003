package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Appointment;
import org.example.service.AppointmentService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/search-appointment")
public class SearchAppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService =
            new AppointmentService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String appointmentNumber =
                request.getParameter("appointmentNumber");

        Appointment appointment =
                appointmentService.searchAppointment(
                        appointmentNumber
                );

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Appointment Details</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Sunrise Dental Clinic</h1>");
        out.println("<h2>Appointment Details</h2>");

        if (appointment != null) {

            out.println("<p><strong>Appointment Number:</strong> "
                    + appointment.getAppointmentNumber()
                    + "</p>");

            out.println("<p><strong>Patient ID:</strong> "
                    + appointment.getPatientId()
                    + "</p>");

            out.println("<p><strong>Dentist ID:</strong> "
                    + appointment.getDentistId()
                    + "</p>");

            out.println("<p><strong>Treatment ID:</strong> "
                    + appointment.getTreatmentId()
                    + "</p>");

            out.println("<p><strong>Date:</strong> "
                    + appointment.getAppointmentDate()
                    + "</p>");

            out.println("<p><strong>Time:</strong> "
                    + appointment.getAppointmentTime()
                    + "</p>");

        } else {

            out.println("<p>Appointment not found.</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}