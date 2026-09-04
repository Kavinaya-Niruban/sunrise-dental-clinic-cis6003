package org.example.dao;

import org.example.model.Appointment;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public boolean addAppointment(
            Appointment appointment) {

        String sql = """
                INSERT INTO appointments
                (
                    appointment_number,
                    patient_name,
                    address,
                    contact_number,
                    dentist_id,
                    treatment_id,
                    appointment_date,
                    appointment_time
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointment.getAppointmentNumber()
            );

            statement.setString(
                    2,
                    appointment.getPatientName()
            );

            statement.setString(
                    3,
                    appointment.getAddress()
            );

            statement.setString(
                    4,
                    appointment.getContactNumber()
            );

            statement.setInt(
                    5,
                    appointment.getDentistId()
            );

            statement.setInt(
                    6,
                    appointment.getTreatmentId()
            );

            statement.setDate(
                    7,
                    java.sql.Date.valueOf(
                            appointment.getAppointmentDate()
                    )
            );

            statement.setTime(
                    8,
                    java.sql.Time.valueOf(
                            appointment.getAppointmentTime()
                    )
            );

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.appointment_id,
                    a.appointment_number,
                    a.patient_name,
                    a.address,
                    a.contact_number,
                    a.dentist_id,
                    a.treatment_id,
                    a.appointment_date,
                    a.appointment_time,
                    d.dentist_name,
                    t.treatment_name
                FROM appointments a
                INNER JOIN dentists d
                    ON a.dentist_id = d.dentist_id
                INNER JOIN treatments t
                    ON a.treatment_id = t.treatment_id
                ORDER BY
                    a.appointment_date DESC,
                    a.appointment_time ASC
                """;

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Appointment appointment =
                        createAppointmentFromResultSet(
                                resultSet
                        );

                appointments.add(appointment);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return appointments;
    }


    public Appointment findByAppointmentNumber(
            String appointmentNumber) {

        String sql = """
                SELECT
                    a.appointment_id,
                    a.appointment_number,
                    a.patient_name,
                    a.address,
                    a.contact_number,
                    a.dentist_id,
                    a.treatment_id,
                    a.appointment_date,
                    a.appointment_time,
                    d.dentist_name,
                    t.treatment_name
                FROM appointments a
                INNER JOIN dentists d
                    ON a.dentist_id = d.dentist_id
                INNER JOIN treatments t
                    ON a.treatment_id = t.treatment_id
                WHERE a.appointment_number = ?
                """;

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointmentNumber
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return createAppointmentFromResultSet(
                            resultSet
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    private Appointment createAppointmentFromResultSet(
            ResultSet resultSet)
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentId(
                resultSet.getInt("appointment_id")
        );

        appointment.setAppointmentNumber(
                resultSet.getString("appointment_number")
        );

        appointment.setPatientName(
                resultSet.getString("patient_name")
        );

        appointment.setAddress(
                resultSet.getString("address")
        );

        appointment.setContactNumber(
                resultSet.getString("contact_number")
        );

        appointment.setDentistId(
                resultSet.getInt("dentist_id")
        );

        appointment.setTreatmentId(
                resultSet.getInt("treatment_id")
        );

        appointment.setAppointmentDate(
                resultSet.getDate(
                        "appointment_date"
                ).toLocalDate()
        );

        appointment.setAppointmentTime(
                resultSet.getTime(
                        "appointment_time"
                ).toLocalTime()
        );

        appointment.setDentistName(
                resultSet.getString("dentist_name")
        );

        appointment.setTreatmentName(
                resultSet.getString("treatment_name")
        );

        return appointment;
    }
}