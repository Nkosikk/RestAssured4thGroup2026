package utils;

import com.sun.jdi.connect.spi.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    public static java.sql.Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ndosian6b8b7_teaching/CeejayUsers";
        String user = "ndosian6b8b7_teaching";
        String password = "ndosian6b8b7_teaching";
        return DriverManager.getConnection(url, user, password);
    }

    public static void InsertNewUser(String email, String password) throws SQLException {

        try (java.sql.Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO CeejayUsers (email, password) VALUES (?, ?)");
            {
                stmt.setString(1, email);
                stmt.setString(2, password);
            }

            stmt.executeUpdate();
        }
    }
}