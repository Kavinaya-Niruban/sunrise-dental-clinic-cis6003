package org.example.dao;

import org.example.model.Bill;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public boolean addBill(Bill bill) {

        String sql = """
                INSERT INTO bill
                (appointment_id, treatment_cost, consultation_fee, total_amount)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    bill.getAppointmentId()
            );

            statement.setDouble(
                    2,
                    bill.getTreatmentCost()
            );

            statement.setDouble(
                    3,
                    bill.getConsultationFee()
            );

            statement.setDouble(
                    4,
                    bill.getTotalAmount()
            );

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    public List<Bill> getAllBills() {

        List<Bill> bills = new ArrayList<>();

        String sql = """
                SELECT
                    b.bill_id,
                    b.appointment_id,
                    a.appointment_number,
                    d.dentist_name,
                    t.treatment_name,
                    b.treatment_cost,
                    b.consultation_fee,
                    b.total_amount
                FROM bill b

                INNER JOIN appointments a
                    ON b.appointment_id = a.appointment_id

                INNER JOIN dentists d
                    ON a.dentist_id = d.dentist_id

                INNER JOIN treatments t
                    ON a.treatment_id = t.treatment_id

                ORDER BY b.bill_id DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Bill bill = new Bill(

                        resultSet.getInt("bill_id"),

                        resultSet.getInt("appointment_id"),

                        resultSet.getString(
                                "appointment_number"
                        ),

                        resultSet.getString(
                                "dentist_name"
                        ),

                        resultSet.getString(
                                "treatment_name"
                        ),

                        resultSet.getDouble(
                                "treatment_cost"
                        ),

                        resultSet.getDouble(
                                "consultation_fee"
                        ),

                        resultSet.getDouble(
                                "total_amount"
                        )
                );

                bills.add(bill);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return bills;
    }
}
