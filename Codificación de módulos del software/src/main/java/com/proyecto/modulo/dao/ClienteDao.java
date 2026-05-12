package com.proyecto.modulo.dao;

import com.proyecto.modulo.model.Cliente;
import com.proyecto.modulo.util.JdbcConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "email VARCHAR(120) NOT NULL, " +
                "telefono VARCHAR(20) NOT NULL)";

        try (Connection connection = JdbcConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new RuntimeException("Error creando la tabla cliente", exception);
        }
    }

    public Cliente insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente(nombre, email, telefono) VALUES (?, ?, ?)";

        try (Connection connection = JdbcConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefono());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }

            return cliente;
        } catch (SQLException exception) {
            throw new RuntimeException("Error insertando el cliente", exception);
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre = ?, email = ?, telefono = ? WHERE id = ?";

        try (Connection connection = JdbcConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefono());
            statement.setInt(4, cliente.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Error actualizando el cliente", exception);
        }
    }

    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection connection = JdbcConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Error eliminando el cliente", exception);
        }
    }

    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT id, nombre, email, telefono FROM cliente WHERE id = ?";

        try (Connection connection = JdbcConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCliente(resultSet);
                }
            }
            return null;
        } catch (SQLException exception) {
            throw new RuntimeException("Error buscando el cliente por id", exception);
        }
    }

    public List<Cliente> listarClientes() {
        String sql = "SELECT id, nombre, email, telefono FROM cliente ORDER BY id";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = JdbcConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clientes.add(mapearCliente(resultSet));
            }
            return clientes;
        } catch (SQLException exception) {
            throw new RuntimeException("Error listando clientes", exception);
        }
    }

    private Cliente mapearCliente(ResultSet resultSet) throws SQLException {
        return new Cliente(
                resultSet.getInt("id"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("telefono")
        );
    }
}
