package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

            List<Bill> bills =
                    billService.getAllBills();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().print("[");

            for (int i = 0; i < bills.size(); i++) {

                Bill bill = bills.get(i);

                response.getWriter().print(
                        "{"
                                + "\"billId\":"
                                + bill.getBillId()

                                + ",\"appointmentId\":"
                                + bill.getAppointmentId()

                                + ",\"treatmentCost\":"
                                + bill.getTreatmentCost()

                                + ",\"consultationFee\":"
                                + bill.getConsultationFee()

                                + ",\"totalAmount\":"
                                + bill.getTotalAmount()

                                + "}"
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
                    "{\"error\":\"Unable to load bills\"}"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int appointmentId =
                    Integer.parseInt(
                            request.getParameter(
                                    "appointmentId"
                            )
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

            Bill bill = new Bill();

            bill.setAppointmentId(
                    appointmentId
            );

            bill.setTreatmentCost(
                    treatmentCost
            );

            bill.setConsultationFee(
                    consultationFee
            );

            boolean success =
                    billService.createBill(bill);

            if (success) {

                response.sendRedirect(
                        "billing.html?success=true"
                );

            } else {

                response.sendRedirect(
                        "billing.html?error=true"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "billing.html?error=invalid"
            );
        }
    }
}