package com.proyecto.modulo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionManager {
    private static final String JDBC_URL = "jdbc:h2:./data/modulo-jdbc;AUTO_SERVER=TRUE";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    private JdbcConnectionManager() {
        // Constructor privado para evitar instanciación
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}
