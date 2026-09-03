package org.example.dao;

import org.example.model.Appointment;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AppointmentDAO {

    public boolean addAppointment(Appointment appointment) {

        String sql = """
                INSERT INTO appointments
                (appointment_number, patient_id, dentist_id, treatment_id,
                 appointment_date, appointment_time)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointment.getAppointmentNumber());
            statement.setInt(2, appointment.getPatientId());
            statement.setInt(3, appointment.getDentistId());
            statement.setInt(4, appointment.getTreatmentId());
            statement.setDate(5, java.sql.Date.valueOf(appointment.getAppointmentDate()));
            statement.setTime(6, java.sql.Time.valueOf(appointment.getAppointmentTime()));

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Appointment findByAppointmentNumber(String appointmentNumber) {

        String sql = """
                SELECT appointment_id, appointment_number, patient_id,
                       dentist_id, treatment_id, appointment_date,
                       appointment_time
                FROM appointments
                WHERE appointment_number = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointmentNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Appointment(
                        resultSet.getInt("appointment_id"),
                        resultSet.getString("appointment_number"),
                        resultSet.getInt("patient_id"),
                        resultSet.getInt("dentist_id"),
                        resultSet.getInt("treatment_id"),
                        resultSet.getDate("appointment_date").toLocalDate(),
                        resultSet.getTime("appointment_time").toLocalTime()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}