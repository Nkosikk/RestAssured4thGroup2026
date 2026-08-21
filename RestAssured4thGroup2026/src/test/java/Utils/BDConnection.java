package Utils;

import commons.Routes;

import java.sql.*;


public class BDConnection {
    public static String emailFromDB;
    public static String passwordFromDB;

    public static Connection getConnection() {
        String dbUrl = Routes.DB_URL;
        String dbUsername = Routes.DB_USERNAME;
        String dbPassword = Routes.DB_PASSWORD;

        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

    }

    public static void insertUser(String email, String password) throws SQLException {
        //Connecting, where connection will be closed automatically
        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO RestassuredUsersItu (email,password) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.executeUpdate();

                try (ResultSet generateKeys = ps.getGeneratedKeys()) {
                    if (generateKeys.next()) {
                        System.out.println("Generated record key: " + generateKeys.getInt(1));
                    }

                } catch (SQLException e) {
                    System.out.println("Error inserting value: " + e.getMessage());
                }

            }
        }
    }

    public static void getLoginDetails(String userEmail) throws SQLException {

        try (Connection connection = getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM RestassuredUsersItu WHERE email = ?")) {

                ps.setString(1, userEmail);

                try (ResultSet resultSet = ps.executeQuery()) {

                    while (resultSet.next()) {

                         emailFromDB = resultSet.getString("email");
                         passwordFromDB = resultSet.getString("password");

                        System.out.println(
                                "Email from DB: " + emailFromDB + ", Password from DB: " + passwordFromDB);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }
        }
    }
}
