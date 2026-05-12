# Módulo JDBC para Gestión de Clientes

Este proyecto es un módulo de software que utiliza JDBC para conectarse a una base de datos y ejecutar operaciones CRUD sobre una entidad `Cliente`.

## Características

- Conexión a base de datos mediante JDBC
- Uso de H2 como base de datos embebida para pruebas y desarrollo
- Diseño por capas con paquetes separando modelo, acceso a datos y aplicación
- Estándares de nombramiento para clases, métodos y variables

## Estructura del proyecto

- `com.proyecto.modulo.model` - clases de dominio
- `com.proyecto.modulo.dao` - acceso a datos con JDBC
- `com.proyecto.modulo.util` - gestor de conexión JDBC
- `com.proyecto.modulo.app` - clase principal de ejecución

## Ejecución

Usar Maven para compilar y ejecutar:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.proyecto.modulo.app.GestionClientesApp"
```

## Notas

El proyecto está diseñado para respetar los artefactos del ciclo de software: nombres coherentes, separación de responsabilidades y uso de paquetes según estándares.
