package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Payment;
import org.example.service.PaymentService;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private final PaymentService paymentService =
            new PaymentService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int billId =
                    Integer.parseInt(
                            request.getParameter("billId")
                    );

            double paymentAmount =
                    Double.parseDouble(
                            request.getParameter("paymentAmount")
                    );

            String paymentMethod =
                    request.getParameter("paymentMethod");

            Payment payment =
                    paymentService.processPayment(
                            billId,
                            paymentAmount,
                            paymentMethod
                    );

            if (payment != null) {

                response.sendRedirect(
                        "payment.html?success=true"
                );

            } else {

                response.sendRedirect(
                        "payment.html?error=true"
                );
            }

        } catch (Exception e) {

            response.sendRedirect(
                    "payment.html?error=invalid"
            );
        }
    }
}