package org.example.dao;

import org.example.model.Payment;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAO {

    public boolean addPayment(Payment payment) {

        String sql = """
                INSERT INTO payments
                (bill_id, payment_amount, payment_method)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, payment.getBillId());
            statement.setDouble(2, payment.getPaymentAmount());
            statement.setString(3, payment.getPaymentMethod());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment findByBillId(int billId) {

        String sql = """
                SELECT payment_id, bill_id, payment_amount,
                       payment_method, payment_date
                FROM payments
                WHERE bill_id = ?
                ORDER BY payment_date DESC
                LIMIT 1
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, billId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Payment(
                        resultSet.getInt("payment_id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getDouble("payment_amount"),
                        resultSet.getString("payment_method"),
                        resultSet.getTimestamp("payment_date")
                                .toLocalDateTime()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
