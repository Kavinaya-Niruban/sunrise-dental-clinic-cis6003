package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Treatment;
import org.example.service.TreatmentService;

import java.io.IOException;
import java.util.List;

@WebServlet("/treatments")
public class TreatmentServlet extends HttpServlet {

    private final TreatmentService treatmentService =
            new TreatmentService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<Treatment> treatments =
                    treatmentService.getAllTreatments();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().print("[");

            for (int i = 0; i < treatments.size(); i++) {

                Treatment treatment = treatments.get(i);

                response.getWriter().print(
                        "{\"treatmentId\":" +
                                treatment.getTreatmentId() +
                                ",\"treatmentName\":\"" +
                                treatment.getTreatmentName().replace("\"", "\\\"") +
                                "\",\"treatmentCost\":" +
                                treatment.getTreatmentCost() +
                                ",\"duration\":\"" +
                                treatment.getDuration().replace("\"", "\\\"") +
                                "\",\"description\":\"" +
                                treatment.getDescription().replace("\"", "\\\"") +
                                "\"}"
                );

                if (i < treatments.size() - 1) {
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
                    "{\"error\":\"Unable to load treatments\"}"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String treatmentName =
                    request.getParameter("treatmentName");

            String treatmentCost =
                    request.getParameter("treatmentCost");

            String duration =
                    request.getParameter("duration");

            String description =
                    request.getParameter("description");

            double cost =
                    Double.parseDouble(treatmentCost);

            Treatment treatment = new Treatment(
                    0,
                    treatmentName,
                    cost,
                    duration,
                    description
            );

            int treatmentId =
                    treatmentService.addTreatment(treatment);

            if (treatmentId > 0) {

                response.sendRedirect(
                        "treatments.html?success=true&treatmentId=" +
                                treatmentId
                );

            } else {

                response.sendRedirect(
                        "treatments.html?error=true"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "treatments.html?error=invalid"
            );
        }
    }
}