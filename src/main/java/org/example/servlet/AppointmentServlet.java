package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.model.Appointment;
import org.example.service.AppointmentService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService =
            new AppointmentService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String appointmentNumber =
                    request.getParameter("appointmentNumber");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // SEARCH BY APPOINTMENT NUMBER
            if (appointmentNumber != null &&
                    !appointmentNumber.trim().isEmpty()) {

                Appointment appointment =
                        appointmentService.searchAppointment(
                                appointmentNumber.trim()
                        );

                if (appointment == null) {

                    response.setStatus(
                            HttpServletResponse.SC_NOT_FOUND
                    );

                    response.getWriter().print(
                            "{\"error\":\"Appointment not found\"}"
                    );

                    return;
                }

                response.getWriter().print(
                        appointmentToJson(appointment)
                );

                return;
            }

            // LOAD ALL APPOINTMENTS
            List<Appointment> appointments =
                    appointmentService.getAllAppointments();

            response.getWriter().print("[");

            for (int i = 0;
                 i < appointments.size();
                 i++) {

                Appointment appointment =
                        appointments.get(i);

                response.getWriter().print(
                        appointmentToJson(appointment)
                );

                if (i < appointments.size() - 1) {
                    response.getWriter().print(",");
                }
            }

            response.getWriter().print("]");

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().print(
                    "{\"error\":\"Unable to process appointment request\"}"
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber"
                    );

            String patientName =
                    request.getParameter(
                            "patientName"
                    );

            String address =
                    request.getParameter(
                            "address"
                    );

            String contactNumber =
                    request.getParameter(
                            "contactNumber"
                    );

            int dentistId =
                    Integer.parseInt(
                            request.getParameter(
                                    "dentistId"
                            )
                    );

            int treatmentId =
                    Integer.parseInt(
                            request.getParameter(
                                    "treatmentId"
                            )
                    );

            LocalDate appointmentDate =
                    LocalDate.parse(
                            request.getParameter(
                                    "appointmentDate"
                            )
                    );

            LocalTime appointmentTime =
                    LocalTime.parse(
                            request.getParameter(
                                    "appointmentTime"
                            )
                    );

            Appointment appointment =
                    new Appointment(
                            0,
                            appointmentNumber,
                            patientName,
                            address,
                            contactNumber,
                            dentistId,
                            treatmentId,
                            appointmentDate,
                            appointmentTime
                    );

            boolean success =
                    appointmentService.registerAppointment(
                            appointment
                    );

            if (success) {

                response.sendRedirect(
                        "appointments.html?success=true"
                );

            } else {

                response.sendRedirect(
                        "appointments.html?error=true"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "appointments.html?error=invalid"
            );
        }
    }


    private String appointmentToJson(
            Appointment appointment) {

        return "{"
                + "\"appointmentId\":"
                + appointment.getAppointmentId()

                + ",\"appointmentNumber\":\""
                + escapeJson(
                appointment.getAppointmentNumber()
        )
                + "\""

                + ",\"patientName\":\""
                + escapeJson(
                appointment.getPatientName()
        )
                + "\""

                + ",\"address\":\""
                + escapeJson(
                appointment.getAddress()
        )
                + "\""

                + ",\"contactNumber\":\""
                + escapeJson(
                appointment.getContactNumber()
        )
                + "\""

                + ",\"dentistName\":\""
                + escapeJson(
                appointment.getDentistName()
        )
                + "\""

                + ",\"treatmentName\":\""
                + escapeJson(
                appointment.getTreatmentName()
        )
                + "\""

                + ",\"appointmentDate\":\""
                + appointment.getAppointmentDate()
                + "\""

                + ",\"appointmentTime\":\""
                + appointment.getAppointmentTime()
                + "\""

                + "}";
    }


    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}