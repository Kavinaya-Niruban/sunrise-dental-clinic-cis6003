package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;

public class AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationService() {
        userDAO = new UserDAO();
    }

    public User login(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return userDAO.authenticate(
                username.trim(),
                password
        );
    }

    public boolean isAuthenticated(User user) {
        return user != null;
    }
}