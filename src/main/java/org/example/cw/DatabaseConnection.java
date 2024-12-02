package org.example.cw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database configuration details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/OOPCW"; // Replace OOPCW with your database name
    private static final String DB_USER = "root"; // Default username for phpMyAdmin
    private static final String DB_PASSWORD = ""; // Default password (leave empty if not set)

    /**
     * Establishes a connection to the database.
     *
     * @return Connection object to interact with the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
