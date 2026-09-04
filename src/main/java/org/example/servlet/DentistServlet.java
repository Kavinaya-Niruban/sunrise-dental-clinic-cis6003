package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Dentist;
import org.example.service.DentistService;

import java.io.IOException;
import java.util.List;

@WebServlet("/dentists")
public class DentistServlet extends HttpServlet {

    private final DentistService dentistService =
            new DentistService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<Dentist> dentists =
                    dentistService.getAllDentists();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().print("[");

            for (int i = 0; i < dentists.size(); i++) {

                Dentist dentist = dentists.get(i);

                response.getWriter().print(
                        "{\"dentistId\":" +
                                dentist.getDentistId() +
                                ",\"dentistName\":\"" +
                                dentist.getDentistName().replace("\"", "\\\"") +
                                "\",\"contactNumber\":\"" +
                                dentist.getContactNumber().replace("\"", "\\\"") +
                                "\"}"
                );

                if (i < dentists.size() - 1) {
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
                    "{\"error\":\"Unable to load dentists\"}"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String dentistName =
                    request.getParameter("dentistName");

            String contactNumber =
                    request.getParameter("contactNumber");

            Dentist dentist = new Dentist();

            dentist.setDentistName(dentistName);
            dentist.setContactNumber(contactNumber);

            int dentistId =
                    dentistService.addDentist(dentist);

            if (dentistId > 0) {

                response.sendRedirect(
                        "dentists.html?success=true"
                );

            } else {

                response.sendRedirect(
                        "dentists.html?error=true"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "dentists.html?error=invalid"
            );
        }
    }
}