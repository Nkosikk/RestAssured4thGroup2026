package utils;

import java.sql.*;

public class DBConnection {

    public static String emailFromDB;
    public static String passwordFromDB;

    public static Connection getConnection() throws SQLException {

        String dbURL = commons.Routes.DB_URL;
        String dbUsername = commons.Routes.DB_USERNAME;
        String dbPassword = commons.Routes.DB_PASSWORD;

        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }

    public static void insertUser(String email, String password) throws SQLException {
        //Connecting, where connection will be closed automatically
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO BrenAPIAssessment (email, password) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Generated record key: " + generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error inserting value: " + e.getMessage());
            }
        }
    }


    public static void getLoginDetails(String userEmail) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM BrenAPIAssessment WHERE email = ?")) {
                ps.setString(1, userEmail);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        emailFromDB = resultSet.getString("email");
                        passwordFromDB = resultSet.getString("password");
                        System.out.println("Email from DB: " + emailFromDB + ", Password from DB: " + passwordFromDB);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }
        }
    }

    public static void testConnection() {

        try (Connection connection = getConnection()) {

            if (connection != null && !connection.isClosed()) {

                System.out.println("✅ Database connection successful!");

            }

        } catch (SQLException e) {

            System.out.println("❌ Database connection failed!");

            e.printStackTrace();

        }

    }


}
