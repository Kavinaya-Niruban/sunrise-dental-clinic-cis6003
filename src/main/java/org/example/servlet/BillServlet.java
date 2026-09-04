package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.model.Appointment;
import org.example.model.Bill;
import org.example.service.BillService;

import java.io.IOException;
import java.util.List;

@WebServlet("/billing")
public class BillServlet extends HttpServlet {

    private final BillService billService =
            new BillService();


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


            if (appointmentNumber != null &&
                    !appointmentNumber.trim().isEmpty()) {

                Appointment appointment =
                        billService.findAppointment(
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


            List<Bill> bills =
                    billService.getAllBills();


            response.getWriter().print("[");


            for (int i = 0; i < bills.size(); i++) {

                Bill bill = bills.get(i);

                response.getWriter().print(
                        billToJson(bill)
                );

                if (i < bills.size() - 1) {
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
                    "{\"error\":\"Unable to process billing request\"}"
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        try {

            String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber"
                    );


            String treatmentCostParameter =
                    request.getParameter(
                            "treatmentCost"
                    );


            String consultationFeeParameter =
                    request.getParameter(
                            "consultationFee"
                    );


            if (appointmentNumber == null ||
                    appointmentNumber.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Appointment number is required."
                );
            }


            if (treatmentCostParameter == null ||
                    treatmentCostParameter.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Treatment cost is required."
                );
            }


            if (consultationFeeParameter == null ||
                    consultationFeeParameter.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Consultation fee is required."
                );
            }


            double treatmentCost =
                    Double.parseDouble(
                            treatmentCostParameter
                    );


            double consultationFee =
                    Double.parseDouble(
                            consultationFeeParameter
                    );


            if (treatmentCost < 0) {

                throw new IllegalArgumentException(
                        "Treatment cost cannot be negative."
                );
            }


            if (consultationFee < 0) {

                throw new IllegalArgumentException(
                        "Consultation fee cannot be negative."
                );
            }


            boolean success =
                    billService.createBill(
                            appointmentNumber,
                            treatmentCost,
                            consultationFee
                    );


            if (success) {

                response.setStatus(
                        HttpServletResponse.SC_OK
                );

                response.getWriter().print(
                        "{\"success\":true}"
                );

            } else {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST
                );

                response.getWriter().print(
                        "{\"success\":false,\"error\":\"Unable to create bill.\"}"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            String errorMessage =
                    e.getMessage();

            if (errorMessage == null ||
                    errorMessage.isEmpty()) {

                errorMessage =
                        "Invalid billing information.";
            }


            errorMessage =
                    errorMessage
                            .replace("\\", "\\\\")
                            .replace("\"", "\\\"");


            response.getWriter().print(
                    "{\"success\":false,\"error\":\""
                            + errorMessage
                            + "\"}"
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
                + "}";
    }


    private String billToJson(Bill bill) {

        return "{"
                + "\"billId\":"
                + bill.getBillId()

                + ",\"appointmentNumber\":\""
                + escapeJson(
                bill.getAppointmentNumber()
        )
                + "\""

                + ",\"dentistName\":\""
                + escapeJson(
                bill.getDentistName()
        )
                + "\""

                + ",\"treatmentName\":\""
                + escapeJson(
                bill.getTreatmentName()
        )
                + "\""

                + ",\"treatmentCost\":"
                + bill.getTreatmentCost()

                + ",\"consultationFee\":"
                + bill.getConsultationFee()

                + ",\"totalAmount\":"
                + bill.getTotalAmount()

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