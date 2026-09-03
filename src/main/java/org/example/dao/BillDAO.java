package org.example.dao;

import org.example.model.Bill;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillDAO {

    public boolean addBill(Bill bill) {

        String sql = """
                INSERT INTO bills
                (appointment_id, treatment_cost, consultation_fee, total_amount)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bill.getAppointmentId());
            statement.setDouble(2, bill.getTreatmentCost());
            statement.setDouble(3, bill.getConsultationFee());
            statement.setDouble(4, bill.getTotalAmount());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bill findByAppointmentId(int appointmentId) {

        String sql = """
                SELECT bill_id, appointment_id, treatment_cost,
                       consultation_fee, total_amount
                FROM bills
                WHERE appointment_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Bill(
                        resultSet.getInt("bill_id"),
                        resultSet.getInt("appointment_id"),
                        resultSet.getDouble("treatment_cost"),
                        resultSet.getDouble("consultation_fee"),
                        resultSet.getDouble("total_amount")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}