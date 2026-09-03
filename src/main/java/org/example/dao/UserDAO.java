package org.example.dao;

import org.example.model.User;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticate(String username, String password) {

        String sql = """
                SELECT user_id, username, password, role
                FROM users
                WHERE username = ? AND password = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}