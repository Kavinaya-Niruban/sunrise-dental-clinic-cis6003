package org.example.dao;

import org.example.model.Treatment;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    public List<Treatment> getAllTreatments() {

        List<Treatment> treatments = new ArrayList<>();

        String sql = """
                SELECT treatment_id, treatment_name, treatment_cost,
                       duration, description
                FROM treatments
                ORDER BY treatment_name
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Treatment treatment = new Treatment(
                        resultSet.getInt("treatment_id"),
                        resultSet.getString("treatment_name"),
                        resultSet.getDouble("treatment_cost"),
                        resultSet.getString("duration"),
                        resultSet.getString("description")
                );

                treatments.add(treatment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return treatments;
    }

    public int addTreatment(Treatment treatment) {

        String sql = """
                INSERT INTO treatments
                (treatment_name, treatment_cost, duration, description)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, treatment.getTreatmentName());
            statement.setDouble(2, treatment.getTreatmentCost());
            statement.setString(3, treatment.getDuration());
            statement.setString(4, treatment.getDescription());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}