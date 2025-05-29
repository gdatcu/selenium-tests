package org.example; // Asigură-te că pachetul este corect

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtil {

    // Detalii de conectare pentru setup-ul local (conform codului PHP)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/social"; // Host, Port, Nume Baza de Date
    private static final String DB_USER = "root";       // Username MySQL local
    private static final String DB_PASSWORD = "qazXSW@13"; // Parola MySQL local

    public static Connection getConnection() throws SQLException {
        // Înainte de a stabili conexiunea, asigură-te că driverul JDBC este încărcat.
        // Pentru mysql-connector-java 8.x, nu mai este strict necesar Class.forName(),
        // dar este o practică bună pentru compatibilitate mai veche și pentru a fi explicit.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver nu a fost găsit. Asigură-te că dependența Maven este adăugată.");
            throw new SQLException("Driver JDBC lipsă.", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}