package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    private final AppointmentService appointmentService =
            new AppointmentService();

    @Test
    void shouldRejectEmptyAppointmentNumber() {

        assertNull(
                appointmentService.searchAppointment("")
        );
    }

    @Test
    void shouldRejectNullAppointmentNumber() {

        assertNull(
                appointmentService.searchAppointment(null)
        );
    }
}