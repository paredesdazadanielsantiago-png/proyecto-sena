package com.inventario.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 */
public class ConexionDB {
    
    private static final String URL = "jdbc:mysql://localhost:3306/inventario_db";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene una conexión a la base de datos
     * @return Conexión a la BD
     * @throws SQLException si hay error en la conexión
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }
    
    /**
     * Cierra la conexión a la base de datos
     * @param conexion Conexión a cerrar
     */
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
