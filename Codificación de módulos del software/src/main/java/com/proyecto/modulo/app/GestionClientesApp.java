package com.proyecto.modulo.app;

import com.proyecto.modulo.dao.ClienteDao;
import com.proyecto.modulo.model.Cliente;

import java.util.List;

public class GestionClientesApp {
    public static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDao();
        clienteDao.crearTablaSiNoExiste();

        System.out.println("=== Módulo de gestión de clientes con JDBC ===");

        Cliente clienteUno = new Cliente("Ana López", "ana.lopez@example.com", "310-555-9012");
        Cliente clienteDos = new Cliente("Luis Pérez", "luis.perez@example.com", "312-777-3344");

        clienteDao.insertarCliente(clienteUno);
        clienteDao.insertarCliente(clienteDos);

        imprimirClientes(clienteDao);

        clienteUno.setEmail("ana.lopez@empresa.com");
        clienteUno.setTelefono("310-555-0000");
        clienteDao.actualizarCliente(clienteUno);

        System.out.println("\n=== Después de actualizar el cliente ===");
        imprimirClientes(clienteDao);

        clienteDao.eliminarCliente(clienteDos.getId());
        System.out.println("\n=== Después de eliminar el segundo cliente ===");
        imprimirClientes(clienteDao);
    }

    private static void imprimirClientes(ClienteDao clienteDao) {
        List<Cliente> clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);
    }
}
