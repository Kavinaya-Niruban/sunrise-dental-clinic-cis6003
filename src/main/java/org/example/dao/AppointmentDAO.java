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
                    patient_id,
                    dentist_id,
                    treatment_id,
                    appointment_date,
                    appointment_time
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointment.getAppointmentNumber()
            );

            statement.setInt(
                    2,
                    appointment.getPatientId()
            );

            statement.setInt(
                    3,
                    appointment.getDentistId()
            );

            statement.setInt(
                    4,
                    appointment.getTreatmentId()
            );

            statement.setDate(
                    5,
                    java.sql.Date.valueOf(
                            appointment.getAppointmentDate()
                    )
            );

            statement.setTime(
                    6,
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
                    a.patient_id,
                    a.dentist_id,
                    a.treatment_id,
                    a.appointment_date,
                    a.appointment_time,
                    p.patient_name,
                    d.dentist_name,
                    t.treatment_name
                FROM appointments a

                INNER JOIN patients p
                    ON a.patient_id = p.patient_id

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
                        createAppointment(
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
                    a.patient_id,
                    a.dentist_id,
                    a.treatment_id,
                    a.appointment_date,
                    a.appointment_time,
                    p.patient_name,
                    d.dentist_name,
                    t.treatment_name
                FROM appointments a

                INNER JOIN patients p
                    ON a.patient_id = p.patient_id

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

                    return createAppointment(
                            resultSet
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    private Appointment createAppointment(
            ResultSet resultSet)
            throws Exception {

        Appointment appointment =
                new Appointment(
                        resultSet.getInt(
                                "appointment_id"
                        ),

                        resultSet.getString(
                                "appointment_number"
                        ),

                        resultSet.getInt(
                                "patient_id"
                        ),

                        resultSet.getInt(
                                "dentist_id"
                        ),

                        resultSet.getInt(
                                "treatment_id"
                        ),

                        resultSet.getDate(
                                "appointment_date"
                        ).toLocalDate(),

                        resultSet.getTime(
                                "appointment_time"
                        ).toLocalTime()
                );

        appointment.setPatientName(
                resultSet.getString(
                        "patient_name"
                )
        );

        appointment.setDentistName(
                resultSet.getString(
                        "dentist_name"
                )
        );

        appointment.setTreatmentName(
                resultSet.getString(
                        "treatment_name"
                )
        );

        return appointment;
    }
}