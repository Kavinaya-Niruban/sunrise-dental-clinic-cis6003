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
                    request.getParameter(
                            "appointmentNumber"
                    );

            response.setContentType(
                    "application/json"
            );

            response.setCharacterEncoding(
                    "UTF-8"
            );

            /*
             * LOAD ONE APPOINTMENT
             */

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


            /*
             * LOAD ALL BILL RECORDS
             */

            List<Bill> bills =
                    billService.getAllBills();

            response.getWriter().print("[");

            for (int i = 0;
                 i < bills.size();
                 i++) {

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

        try {

            String appointmentNumber =
                    request.getParameter(
                            "appointmentNumber"
                    );

            double treatmentCost =
                    Double.parseDouble(
                            request.getParameter(
                                    "treatmentCost"
                            )
                    );

            double consultationFee =
                    Double.parseDouble(
                            request.getParameter(
                                    "consultationFee"
                            )
                    );


            boolean success =
                    billService.createBill(
                            appointmentNumber,
                            treatmentCost,
                            consultationFee
                    );


            response.setContentType(
                    "application/json"
            );

            response.setCharacterEncoding(
                    "UTF-8"
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
                        "{\"success\":false,\"error\":\"Invalid appointment or billing information.\"}"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            response.getWriter().print(
                    "{\"success\":false,\"error\":\"Invalid billing information.\"}"
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