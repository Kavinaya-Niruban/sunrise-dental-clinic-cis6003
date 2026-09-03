package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Bill;
import org.example.service.BillingService;

import java.io.IOException;

@WebServlet("/billing")
public class BillServlet extends HttpServlet {

    private final BillingService billingService =
            new BillingService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int appointmentId =
                    Integer.parseInt(
                            request.getParameter("appointmentId")
                    );

            double treatmentCost =
                    Double.parseDouble(
                            request.getParameter("treatmentCost")
                    );

            double consultationFee =
                    Double.parseDouble(
                            request.getParameter("consultationFee")
                    );

            Bill bill = billingService.calculateBill(
                    appointmentId,
                    treatmentCost,
                    consultationFee
            );

            if (bill != null &&
                    billingService.saveBill(bill)) {

                response.sendRedirect(
                        "billing.html?success=true"
                );

            } else {

                response.sendRedirect(
                        "billing.html?error=true"
                );
            }

        } catch (Exception e) {

            response.sendRedirect(
                    "billing.html?error=invalid"
            );
        }
    }
}