package com.inventario.dao;

import com.inventario.model.Producto;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Producto
 * Maneja todas las operaciones CRUD de productos en la base de datos
 */
public class ProductoDAO {
    
    /**
     * Inserta un nuevo producto en la base de datos
     */
    public static boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio, cantidad, categoria, fecha_creacion, fecha_actualizacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setString(3, producto.getDescripcion());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getCantidad());
            pstmt.setString(6, producto.getCategoria());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene todos los productos de la base de datos
     */
    public static List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY id DESC";
        
        try (Connection conexion = ConexionDB.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto producto = mapearResultSet(rs);
                productos.add(producto);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        
        return productos;
    }
    
    /**
     * Obtiene un producto por su ID
     */
    public static Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Actualiza un producto existente
     */
    public static boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET codigo=?, nombre=?, descripcion=?, precio=?, cantidad=?, categoria=?, fecha_actualizacion=NOW() WHERE id=?";
        
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setString(3, producto.getDescripcion());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getCantidad());
            pstmt.setString(6, producto.getCategoria());
            pstmt.setInt(7, producto.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un producto por su ID
     */
    public static boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene productos por categoría
     */
    public static List<Producto> obtenerPorCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE categoria = ? ORDER BY nombre ASC";
        
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, categoria);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = mapearResultSet(rs);
                    productos.add(producto);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener productos por categoría: " + e.getMessage());
        }
        
        return productos;
    }
    
    /**
     * Mapea un ResultSet a un objeto Producto
     */
    private static Producto mapearResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setCodigo(rs.getString("codigo"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setCantidad(rs.getInt("cantidad"));
        producto.setCategoria(rs.getString("categoria"));
        
        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
        if (fechaCreacion != null) {
            producto.setFechaCreacion(fechaCreacion.toLocalDateTime());
        }
        
        Timestamp fechaActualizacion = rs.getTimestamp("fecha_actualizacion");
        if (fechaActualizacion != null) {
            producto.setFechaActualizacion(fechaActualizacion.toLocalDateTime());
        }
        
        return producto;
    }
}
