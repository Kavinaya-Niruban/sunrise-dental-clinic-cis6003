package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    private final AuthenticationService authenticationService =
            new AuthenticationService();

    @Test
    void shouldRejectEmptyUsername() {

        assertNull(
                authenticationService.login("", "admin123")
        );
    }

    @Test
    void shouldRejectEmptyPassword() {

        assertNull(
                authenticationService.login("admin", "")
        );
    }
}