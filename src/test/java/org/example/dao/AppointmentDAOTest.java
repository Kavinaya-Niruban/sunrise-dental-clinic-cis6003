package org.example.dao;

import org.example.util.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentDAOTest {

    @Test
    void shouldFindAppointmentByAppointmentNumber() {

        String sql = """
                SELECT appointment_number,
                       dentist_id,
                       treatment_id,
                       appointment_date,
                       appointment_time
                FROM appointments
                WHERE appointment_number = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "001");

            try (ResultSet resultSet = statement.executeQuery()) {

                assertTrue(
                        resultSet.next(),
                        "Appointment 001 should exist"
                );

                assertEquals(
                        "001",
                        resultSet.getString("appointment_number")
                );

                assertTrue(
                        resultSet.getInt("dentist_id") > 0,
                        "Dentist ID should be valid"
                );

                assertTrue(
                        resultSet.getInt("treatment_id") > 0,
                        "Treatment ID should be valid"
                );

                assertNotNull(
                        resultSet.getDate("appointment_date")
                );

                assertNotNull(
                        resultSet.getTime("appointment_time")
                );
            }

        } catch (Exception e) {

            fail(
                    "Appointment database test failed: "
                            + e.getMessage()
            );
        }
    }
}