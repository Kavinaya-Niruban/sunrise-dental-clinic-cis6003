package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.model.User;
import org.example.service.AuthenticationService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthenticationService authenticationService =
            new AuthenticationService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("========== LOGIN ATTEMPT ==========");
        System.out.println("Username received: [" + username + "]");
        System.out.println("Password received: [" + password + "]");

        try {

            User user =
                    authenticationService.login(
                            username,
                            password
                    );

            if (user != null) {

                System.out.println("LOGIN SUCCESS");
                System.out.println("User ID: " + user.getUserId());
                System.out.println("Username: " + user.getUsername());
                System.out.println("Role: " + user.getRole());

                HttpSession session =
                        request.getSession();

                session.setAttribute(
                        "user",
                        user
                );

                response.sendRedirect(
                        "dashboard.html"
                );

            } else {

                System.out.println(
                        "LOGIN FAILED - User not found"
                );

                response.sendRedirect(
                        "login.html?error=invalid"
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "LOGIN ERROR: "
                            + e.getMessage()
            );

            e.printStackTrace();

            response.sendRedirect(
                    "login.html?error=database"
            );
        }

        System.out.println("==================================");
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                "login.html"
        );
    }
}