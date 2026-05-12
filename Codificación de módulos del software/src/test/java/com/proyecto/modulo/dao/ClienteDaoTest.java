package com.proyecto.modulo.dao;

import com.proyecto.modulo.model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDaoTest {
    private ClienteDao clienteDao;

    @BeforeEach
    void setUp() {
        clienteDao = new ClienteDao();
        clienteDao.crearTablaSiNoExiste();
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./data/modulo-jdbc", "sa", "")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS cliente");
            }
        }
        Path dataDir = Path.of("data");
        if (Files.exists(dataDir)) {
            Files.list(dataDir)
                .filter(path -> path.getFileName().toString().startsWith("modulo-jdbc"))
                .forEach(path -> path.toFile().delete());
            new File("data").delete();
        }
    }

    @Test
    void debeInsertarYListarClientes() {
        Cliente cliente = new Cliente("Prueba Usuario", "prueba@example.com", "300-123-4567");
        Cliente resultado = clienteDao.insertarCliente(cliente);

        assertTrue(resultado.getId() > 0);

        List<Cliente> clientes = clienteDao.listarClientes();
        assertFalse(clientes.isEmpty());
        assertEquals("Prueba Usuario", clientes.get(0).getNombre());
    }

    @Test
    void debeActualizarYEliminarCliente() {
        Cliente cliente = new Cliente("Cliente Test", "test@example.com", "300-987-6543");
        clienteDao.insertarCliente(cliente);

        cliente.setEmail("actualizado@example.com");
        cliente.setTelefono("300-000-0000");
        assertTrue(clienteDao.actualizarCliente(cliente));

        Cliente clienteActualizado = clienteDao.buscarClientePorId(cliente.getId());
        assertNotNull(clienteActualizado);
        assertEquals("actualizado@example.com", clienteActualizado.getEmail());

        assertTrue(clienteDao.eliminarCliente(cliente.getId()));
        assertNull(clienteDao.buscarClientePorId(cliente.getId()));
    }
}
