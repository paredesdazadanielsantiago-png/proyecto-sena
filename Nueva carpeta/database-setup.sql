-- Script de Setup de Base de Datos para Sistema de Inventarios
-- Ejecutar este script en MySQL para crear la BD y tabla

-- 1. Crear base de datos
CREATE DATABASE IF NOT EXISTS inventario_db;
USE inventario_db;

-- 2. Crear tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID único del producto',
    codigo VARCHAR(50) NOT NULL UNIQUE COMMENT 'Código único del producto',
    nombre VARCHAR(100) NOT NULL COMMENT 'Nombre del producto',
    descripcion TEXT COMMENT 'Descripción detallada del producto',
    precio DECIMAL(10, 2) NOT NULL COMMENT 'Precio unitario del producto',
    cantidad INT NOT NULL DEFAULT 0 COMMENT 'Cantidad disponible en inventario',
    categoria VARCHAR(50) COMMENT 'Categoría del producto',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro',
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Fecha de última actualización',
    INDEX idx_categoria (categoria) COMMENT 'Índice para búsquedas por categoría',
    INDEX idx_codigo (codigo) COMMENT 'Índice para búsquedas por código'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Insertar datos de ejemplo
INSERT INTO productos (codigo, nombre, descripcion, precio, cantidad, categoria) VALUES
('PROD001', 'Camiseta Azul', 'Camiseta de algodón 100% azul talla M', 25.50, 50, 'Ropa'),
('PROD002', 'Pantalón Negro', 'Pantalón de mezclilla negro talla 32', 45.00, 30, 'Ropa'),
('PROD003', 'Mouse Inalámbrico', 'Mouse inalámbrico 2.4GHz, 3 botones', 15.99, 3, 'Electrónica'),
('PROD004', 'Teclado Mecánico', 'Teclado mecánico RGB switches azules', 89.99, 2, 'Electrónica'),
('PROD005', 'Arroz Premium', 'Arroz blanco premium 5 kg', 12.50, 25, 'Alimentación'),
('PROD006', 'Aceite de Oliva', 'Aceite de oliva extra virgen 1 litro', 18.75, 15, 'Alimentación'),
('PROD007', 'Zapatillas Deportivas', 'Zapatillas deportivas talla 40 color rojo', 75.00, 8, 'Ropa'),
('PROD008', 'Monitor LED 24"', 'Monitor LED Full HD 24 pulgadas', 199.99, 1, 'Electrónica');

-- 4. Mostrar tabla creada
SELECT '✓ Base de datos y tabla creadas exitosamente' AS Resultado;
SELECT COUNT(*) AS 'Productos en Inventario' FROM productos;
