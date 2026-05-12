# Sistema de Gestión de Inventarios

## Descripción del Proyecto

Aplicación web desarrollada en Java con Servlets, JSP y MySQL para la gestión de inventarios en tiendas pequeñas. Permite crear, leer, actualizar y eliminar productos, con reportes y estadísticas de stock.

### Características Principales

- ✅ **CRUD Completo**: Crear, leer, actualizar y eliminar productos
- ✅ **Formularios HTML con Servlets**: Interfaz amigable para gestionar productos
- ✅ **Métodos GET y POST**: Implementación correcta de protocolos HTTP
- ✅ **Vistas JSP**: Plantillas dinámicas con JSTL
- ✅ **Base de Datos MySQL**: Persistencia de datos
- ✅ **Reportes**: Estadísticas de inventario y alertas de bajo stock
- ✅ **Responsive Design**: Compatible con dispositivos móviles
- ✅ **Validación de Datos**: Validación en cliente y servidor

---

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| Java | 11+ | Lenguaje principal |
| Gradle | 7.6 | Build tool |
| Apache Tomcat | 9.0+ | Servidor web |
| MySQL | 8.0+ | Base de datos |
| JSP | 2.3 | Vistas dinámicas |
| Servlets | 4.0 | Controladores |
| JSTL | 1.2 | Tags para JSP |

---

## Estructura del Proyecto

```
inventario-app/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/inventario/
│   │   │       ├── model/
│   │   │       │   └── Producto.java          # Modelo de datos
│   │   │       ├── dao/
│   │   │       │   ├── ConexionDB.java        # Conexión a BD
│   │   │       │   └── ProductoDAO.java       # Acceso a datos
│   │   │       └── servlets/
│   │   │           └── ProductoServlet.java   # Controlador principal
│   │   └── webapp/
│   │       ├── index.jsp                      # Página de inicio
│   │       ├── WEB-INF/
│   │       │   └── web.xml                    # Descriptor de aplicación
│   │       ├── jsp/
│   │       │   ├── listar-productos.jsp
│   │       │   ├── formulario-producto.jsp
│   │       │   ├── confirmar-eliminar.jsp
│   │       │   └── reporte-inventario.jsp
│   │       ├── css/
│   │       │   └── estilos.css                # Estilos responsivos
│   │       └── js/
│   │           └── validacion.js              # Validación cliente
│
├── build.gradle                                # Configuración Gradle
├── settings.gradle                             # Nombre del proyecto
├── .gitignore                                  # Archivos ignorados por Git
└── README.md                                   # Este archivo
```

---

## Requisitos Previos

1. **Java Development Kit (JDK) 11 o superior**
   ```bash
   java -version
   ```

2. **Gradle 7.6 o superior**
   ```bash
   gradle -v
   ```

3. **MySQL Server 8.0 o superior**
   ```bash
   mysql --version
   ```

4. **Apache Tomcat 9.0 o superior** (para ejecutar la aplicación)

5. **Git** para versionamiento
   ```bash
   git --version
   ```

---

## Configuración de la Base de Datos

### 1. Crear base de datos y tabla

```sql
-- Crear base de datos
CREATE DATABASE IF NOT EXISTS inventario_db;
USE inventario_db;

-- Crear tabla de productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    categoria VARCHAR(50),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_categoria (categoria),
    INDEX idx_codigo (codigo)
);

-- Insertar datos de ejemplo
INSERT INTO productos (codigo, nombre, descripcion, precio, cantidad, categoria) VALUES
('PROD001', 'Camiseta Azul', 'Camiseta de algodón azul talla M', 25.50, 50, 'Ropa'),
('PROD002', 'Pantalón Negro', 'Pantalón de mezclilla negro', 45.00, 30, 'Ropa'),
('PROD003', 'Mouse Inalámbrico', 'Mouse inalámbrico 2.4GHz', 15.99, 5, 'Electrónica'),
('PROD004', 'Teclado Mecánico', 'Teclado mecánico RGB', 89.99, 2, 'Electrónica');
```

### 2. Actualizar credenciales en ConexionDB.java

Editar el archivo `src/main/java/com/inventario/dao/ConexionDB.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/inventario_db";
private static final String USUARIO = "root";      // Tu usuario de MySQL
private static final String CONTRASEÑA = "";       // Tu contraseña de MySQL
```

---

## Instalación y Ejecución

### 1. Clonar o descargar el proyecto

```bash
git clone <URL-del-repositorio>
cd inventario-app
```

### 2. Compilar el proyecto

```bash
gradle clean build
```

### 3. Generar archivo WAR

```bash
gradle war
```

El archivo `inventario-app.war` estará en `build/libs/`

### 4. Desplegar en Tomcat

1. **Opción A: Copia directa**
   ```bash
   cp build/libs/inventario-app.war $CATALINA_HOME/webapps/
   ```

2. **Opción B: Usando Tomcat Manager**
   - Accede a http://localhost:8080/manager/html
   - Sube el archivo WAR

### 5. Acceder a la aplicación

```
http://localhost:8080/inventario-app/
```

---

## Guía de Uso

### Página de Inicio
- Menú principal con accesos a funcionalidades
- Descripción de cada módulo

### Gestionar Productos
- **Listar**: Ver todos los productos en una tabla
- **Crear**: Formulario para agregar nuevo producto
- **Editar**: Modificar información existente
- **Eliminar**: Remover producto con confirmación

### Reportes
- Estadísticas generales de inventario
- Valor total del inventario
- Productos con bajo stock (< 5 unidades)
- Opción de impresión

---

## Métodos HTTP Implementados

### GET - Peticiones de lectura

| URL | Parámetro | Acción |
|-----|-----------|--------|
| `/productos` | - | Listar productos |
| `/productos?accion=formulario` | - | Mostrar formulario crear |
| `/productos?accion=formulario&id=1` | id | Formulario editar |
| `/productos?accion=eliminar&id=1` | id | Confirmación eliminación |
| `/productos?accion=reporte` | - | Generar reporte |

### POST - Peticiones de escritura

| URL | Acción | Parámetros |
|-----|--------|-----------|
| `/productos` | crear | codigo, nombre, descripcion, precio, cantidad, categoria |
| `/productos` | actualizar | id, codigo, nombre, descripcion, precio, cantidad, categoria |
| `/productos` | eliminar | id |

---

## Elementos de Formularios

### Métodos GET y POST

**Ejemplo de Formulario HTML:**
```html
<form method="POST" action="productos">
    <input type="hidden" name="accion" value="crear">
    <input type="text" name="codigo" required>
    <input type="text" name="nombre" required>
    <input type="number" name="precio" step="0.01" required>
    <input type="number" name="cantidad" required>
    <select name="categoria">
        <option>Ropa</option>
        <option>Electrónica</option>
        <option>Alimentación</option>
    </select>
    <button type="submit">Crear</button>
</form>
```

### Elementos JSP

- **Iteración con JSTL**: `<c:forEach var="p" items="${productos}">`
- **Condicionales**: `<c:if test="${p.cantidad < 5}">`
- **Formato de datos**: `<fmt:formatNumber value="${precio}"/>`
- **Expresiones EL**: `${producto.nombre}`

---

## Descripción de Clases

### Modelo
**Producto.java**
- Representa un producto en el sistema
- Propiedades: id, codigo, nombre, descripcion, precio, cantidad, categoria, fechaCreacion, fechaActualizacion
- Getters y setters para acceso a datos

### DAO
**ConexionDB.java**
- Gestiona la conexión a la base de datos MySQL
- Método estático para obtener conexión

**ProductoDAO.java**
- Implementa operaciones CRUD
- Métodos: insertarProducto, obtenerTodos, obtenerPorId, actualizarProducto, eliminarProducto, obtenerPorCategoria

### Servlets
**ProductoServlet.java**
- Extiende HttpServlet
- Implementa doGet() y doPost()
- Procesa acciones: listar, formulario, crear, actualizar, eliminar, reporte
- Maneja redirecciones y atributos de sesión

---

## Validaciones Implementadas

### Cliente (JavaScript)
- Campos requeridos
- Valores numéricos válidos
- Rango de precios y cantidades
- Confirmación antes de eliminar

### Servidor (Java)
- Validación de parámetros
- Manejo de excepciones
- Verificación de integridad de datos
- Mensajes de error descriptivos

---

## Versionamiento con Git

### Configuración inicial

```bash
# Inicializar repositorio
git init

# Agregar archivos
git add .

# Commit inicial
git commit -m "Initial commit: Estructura base de aplicación de inventarios"

# Crear rama principal
git branch -M main
git remote add origin <URL-repositorio>
git push -u origin main
```

### Commits típicos

```bash
# Crear nueva rama para feature
git checkout -b feature/nuevo-modulo

# Hacer cambios y commit
git add .
git commit -m "Feat: Agregar nuevo módulo de reportes"

# Merge a main
git checkout main
git merge feature/nuevo-modulo
git push origin main
```

---

## Troubleshooting

### Error: "No suitable driver found"
- Verificar que `mysql-connector-java` esté en `build.gradle`
- Ejecutar: `gradle clean build`

### Error: "Access denied for user"
- Verificar credenciales en `ConexionDB.java`
- Asegurar que MySQL esté ejecutándose

### Error: "404 - Recurso no encontrado"
- Verificar que la aplicación está desplegada en Tomcat
- Verificar URL correcta: `http://localhost:8080/inventario-app/`

### Base de datos vacía después de cargar
- Ejecutar script SQL para crear tabla e insertar datos
- Verificar conexión a BD en logs de Tomcat

---

## Mejoras Futuras

- [ ] Autenticación y autorización de usuarios
- [ ] Exportar reportes a PDF
- [ ] Búsqueda y filtrado avanzado
- [ ] Historial de cambios en inventario
- [ ] Notificaciones de bajo stock
- [ ] API REST para integración móvil
- [ ] Sistema de categorías dinámicas
- [ ] Análisis de tendencias de ventas

---

## Licencia

Este proyecto es de código abierto y está disponible bajo la Licencia MIT.

---

## Autor

Desarrollo realizado como proyecto educativo de componente formativo.

**Fecha**: Mayo 2026

---

## Soporte

Para reportar problemas o sugerencias:
1. Crear un issue en el repositorio
2. Describir el problema detalladamente
3. Incluir pasos para reproducir
4. Adjuntar logs si es necesario

---

## Notas Importantes

1. **Seguridad**: En producción, usar prepared statements (ya implementado) y validar inputs
2. **Sesiones**: Implementar timeout de sesiones configurables
3. **Logs**: Configurar logging con SLF4J para debugging
4. **CORS**: Si se integra con frontend en otro dominio, configurar CORS
5. **Backups**: Realizar backups regulares de la base de datos

