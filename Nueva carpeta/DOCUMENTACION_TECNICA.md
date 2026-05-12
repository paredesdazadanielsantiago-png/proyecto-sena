# Documentación Técnica - Sistema de Gestión de Inventarios

## Arquitectura de la Aplicación

### Patrón de Arquitectura: MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────────────┐
│           Vista (JSP + HTML)                        │
│  ├── index.jsp (Inicio)                             │
│  ├── listar-productos.jsp                           │
│  ├── formulario-producto.jsp                        │
│  ├── confirmar-eliminar.jsp                         │
│  └── reporte-inventario.jsp                         │
└──────────────────┬──────────────────────────────────┘
                   │ (HTTP GET/POST)
                   ▼
┌─────────────────────────────────────────────────────┐
│      Controlador (Servlet)                          │
│  ProductoServlet                                    │
│  ├── doGet()                                        │
│  ├── doPost()                                       │
│  └── Acciones: listar, crear, editar, eliminar      │
└──────────────────┬──────────────────────────────────┘
                   │ (Lógica de negocio)
                   ▼
┌─────────────────────────────────────────────────────┐
│      Modelo (Java Classes)                          │
│  ├── ProductoDAO (Acceso a datos)                   │
│  └── Producto (Entidad)                             │
└──────────────────┬──────────────────────────────────┘
                   │ (SQL)
                   ▼
┌─────────────────────────────────────────────────────┐
│      Base de Datos (MySQL)                          │
│  ├── tabla: productos                               │
│  └── índices: categoria, codigo                     │
└─────────────────────────────────────────────────────┘
```

---

## Flujo de Datos - Caso de Uso: Crear Producto

```
1. Usuario accede a: /productos?accion=formulario
                        ↓
2. ProductoServlet.doGet() → mostrarFormulario()
                        ↓
3. Despliega: formulario-producto.jsp
                        ↓
4. Usuario completa formulario y hace POST
                        ↓
5. ProductoServlet.doPost() → crearProducto()
   - Obtiene parámetros del request
   - Crea objeto Producto
   - Valida datos
                        ↓
6. ProductoDAO.insertarProducto()
   - Prepara sentencia SQL INSERT
   - Ejecuta operación en BD
                        ↓
7. Si éxito: 
   - Mensaje en sesión
   - Redirige a: /productos?accion=listar
                        ↓
8. Muestra lista actualizada de productos
```

---

## Métodos HTTP - Análisis Detallado

### GET (Lectura segura e idempotente)

#### `GET /productos`
- **Acción**: listar (por defecto)
- **Respuesta**: Tabla HTML con todos los productos
- **Atributos Request**: productos (List)
- **Forward**: listar-productos.jsp

#### `GET /productos?accion=formulario`
- **Acción**: mostrarFormulario
- **Parámetro**: id (opcional)
- **Respuesta**: Formulario para crear o editar
- **Atributos Request**: producto (null o Producto), titulo
- **Forward**: formulario-producto.jsp

#### `GET /productos?accion=reporte`
- **Acción**: generarReporte
- **Respuesta**: Reporte de inventario con estadísticas
- **Atributos Request**: productos, valorInventario, totalProductos, productosBajoStock
- **Forward**: reporte-inventario.jsp

### POST (Escritura no idempotente)

#### `POST /productos` (accion=crear)
- **Parámetros**: codigo, nombre, descripcion, precio, cantidad, categoria
- **Lógica**:
  1. Valida entrada (NumberFormatException)
  2. Crea objeto Producto
  3. Llama ProductoDAO.insertarProducto()
  4. Si éxito: Redirect a listar
  5. Si error: Redirect a formulario con mensaje de error

#### `POST /productos` (accion=actualizar)
- **Parámetros**: id, codigo, nombre, descripcion, precio, cantidad, categoria
- **Lógica**: Similar a crear, pero actualiza registro existente

#### `POST /productos` (accion=eliminar)
- **Parámetros**: id
- **Lógica**: Elimina producto y redirige a listado

---

## Componentes JSP - Análisis de Etiquetas

### listar-productos.jsp

```jsp
<!-- Iteración JSTL -->
<c:forEach var="producto" items="${productos}">
    <!-- Condicional -->
    <c:if test="${producto.cantidad < 5}">
        <!-- Expresión EL -->
        <span class="cantidad-bajo">${producto.cantidad}</span>
    </c:if>
</c:forEach>

<!-- Condicional con else implícito -->
<c:if test="${empty productos}">
    <p>No hay productos</p>
</c:if>
```

### reporte-inventario.jsp

```jsp
<!-- Formato de números -->
<fmt:formatNumber value="${valorInventario}" pattern="#,##0.00"/>

<!-- Formato de fechas -->
<fmt:formatDate value="${java.util.Calendar.getInstance().time}" 
                 pattern="dd/MM/yyyy HH:mm:ss"/>
```

---

## Validaciones - Capas Múltiples

### Capa 1: Cliente (JavaScript)

**Archivo**: `validacion.js`

```javascript
// Validar evento submit del formulario
formulario.addEventListener('submit', function(e) {
    if (!codigo) {
        e.preventDefault();
        alert('El código es requerido');
    }
    // ... más validaciones
});
```

**Ventajas**:
- Respuesta inmediata al usuario
- Reduce tráfico de red
- Mejora UX

### Capa 2: Servidor (Java)

**Archivo**: `ProductoServlet.java`

```java
try {
    double precio = Double.parseDouble(request.getParameter("precio"));
    // Validación de rango
    if (precio < 0) {
        throw new NumberFormatException();
    }
} catch (NumberFormatException e) {
    request.getSession().setAttribute("error", "Precio inválido");
    response.sendRedirect("productos?accion=formulario");
}
```

**Ventajas**:
- Seguridad: imposible bypasear
- Manejo de excepciones robusto
- Mensajes descriptivos

### Capa 3: BD (SQL)

```sql
ALTER TABLE productos ADD CONSTRAINT
CHECK (precio > 0 AND cantidad >= 0);

CREATE UNIQUE INDEX idx_codigo ON productos(codigo);
```

**Ventajas**:
- Integridad referencial
- Previene duplicados
- Validación de dominio

---

## Patrón DAO (Data Access Object)

### Beneficios

1. **Abstracción**: Lógica de BD separada de servlets
2. **Reutilización**: Métodos usables en múltiples controladores
3. **Testabilidad**: Fácil crear mock objects
4. **Mantenimiento**: Cambios en SQL centralizados

### Estructura del DAO

```java
public class ProductoDAO {
    
    // CRUD Operations
    static boolean insertarProducto(Producto p)
    static List<Producto> obtenerTodos()
    static Producto obtenerPorId(int id)
    static boolean actualizarProducto(Producto p)
    static boolean eliminarProducto(int id)
    
    // Consultas especializadas
    static List<Producto> obtenerPorCategoria(String cat)
    
    // Helper privado
    static Producto mapearResultSet(ResultSet rs)
}
```

---

## Gestión de Sesiones HTTP

### Atributos de Sesión Usados

```java
// Mensaje de éxito
request.getSession().setAttribute("mensaje", "Producto creado exitosamente");

// Mensaje de error
request.getSession().setAttribute("error", "Error al crear el producto");
```

### Limpieza en JSP

```jsp
<c:if test="${not empty sessionScope.mensaje}">
    <div class="alert alert-success">
        ${sessionScope.mensaje}
        <!-- Remover después de mostrar -->
        <% session.removeAttribute("mensaje"); %>
    </div>
</c:if>
```

---

## Ciclo de Vida de una Solicitud

### Ejemplo: Crear Producto

```
1. Usuario carga formulario
   GET /productos?accion=formulario
   ├─ ProductoServlet.doGet() → mostrarFormulario()
   ├─ Obtiene producto = null
   ├─ Request.setAttribute("titulo", "Crear...")
   └─ Forward → formulario-producto.jsp ✓

2. Usuario completa y envía
   POST /productos (accion=crear)
   ├─ ProductoServlet.doPost() → crearProducto()
   ├─ Obtiene parámetros del form
   ├─ Crea objeto Producto
   ├─ Valida (try/catch)
   ├─ Llama ProductoDAO.insertarProducto()
   │  ├─ Obtiene conexión
   │  ├─ Prepara PreparedStatement
   │  ├─ Binds parámetros (?)
   │  ├─ Ejecuta executeUpdate()
   │  └─ Cierra recursos
   ├─ Si éxito: setAttribute("mensaje", "...")
   ├─ Redirect → /productos?accion=listar
   └─ Sesión se mantiene → mensaje visible ✓

3. Usuario ve lista actualizada
   GET /productos?accion=listar (por defecto)
   ├─ ProductoServlet.doGet() → listarProductos()
   ├─ ProductoDAO.obtenerTodos()
   ├─ Itera BD y crea List<Producto>
   ├─ Request.setAttribute("productos", lista)
   ├─ Forward → listar-productos.jsp
   └─ JSP renderiza tabla con JSTL ✓
```

---

## Índices de Base de Datos

### idx_categoria

```sql
CREATE INDEX idx_categoria ON productos(categoria);
```

**Beneficios**:
- Búsquedas por categoría más rápidas
- Mejora performance en queries:
  ```sql
  SELECT * FROM productos WHERE categoria = 'Ropa';
  ```

### idx_codigo

```sql
CREATE UNIQUE INDEX idx_codigo ON productos(codigo);
```

**Beneficios**:
- Garantiza unicidad de códigos
- Búsquedas por código O(log n)
- Previene duplicados automáticamente

---

## Tipos de Datos MySQL Utilizados

| Campo | Tipo | Tamaño | Justificación |
|-------|------|--------|---------------|
| id | INT | 4 bytes | Auto-incremento, pk |
| codigo | VARCHAR(50) | Variable | Código único |
| nombre | VARCHAR(100) | Variable | Nombres usuales < 100 chars |
| descripcion | TEXT | 65KB | Texto largo |
| precio | DECIMAL(10,2) | 5 bytes | 2 decimales necesarios |
| cantidad | INT | 4 bytes | Cantidad entera |
| categoria | VARCHAR(50) | Variable | Categoría corta |
| fecha_creacion | TIMESTAMP | 4 bytes | Marca temporal |

---

## Seguridad Implementada

### SQL Injection Prevention

```java
// ❌ Vulnerable
String query = "SELECT * FROM productos WHERE id = " + id;

// ✅ Seguro (PreparedStatement)
PreparedStatement pstmt = conexion.prepareStatement(
    "SELECT * FROM productos WHERE id = ?"
);
pstmt.setInt(1, id);
```

### Validación de Entrada

```java
// Validar tipos de datos
int cantidad = Integer.parseInt(request.getParameter("cantidad"));

// Validar rangos
if (cantidad < 0 || cantidad > 999999) {
    throw new NumberFormatException();
}

// Validar campos requeridos
if (nombre == null || nombre.trim().isEmpty()) {
    throw new IllegalArgumentException("Nombre requerido");
}
```

---

## Performance y Optimizaciones

### 1. Conexión a BD
```java
// Obtener conexión
Connection conexion = ConexionDB.obtenerConexion();

// Try-with-resources (cierre automático)
try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
    // Usar
} // Cierre automático
```

### 2. Consultas Optimizadas
```sql
-- ❌ Sin índice - Full table scan
SELECT * FROM productos WHERE codigo = 'PROD001';

-- ✅ Con índice - Búsqueda O(log n)
CREATE INDEX idx_codigo ON productos(codigo);
```

### 3. Caching Potencial
```java
// Futuro: implementar cache de categorías
static Map<String, List<Producto>> cache = new HashMap<>();
```

---

## Testing Manual - Checklist

- [ ] Listar productos vacío
- [ ] Crear producto exitoso
- [ ] Crear con datos inválidos (validación cliente)
- [ ] Crear con precio negativo (validación servidor)
- [ ] Crear con código duplicado (error BD)
- [ ] Editar producto existente
- [ ] Eliminar con confirmación
- [ ] Ver reporte con estadísticas
- [ ] Filtrar por categoría (futuro)
- [ ] Búsqueda de bajo stock (< 5 unidades)

---

## Posibles Extensiones

1. **Autenticación**: Spring Security
2. **API REST**: JAX-RS / Spring Boot
3. **Paginación**: Implementar LIMIT/OFFSET
4. **Exportar PDF**: iText o Apache PDFBox
5. **Gráficos**: Chart.js o Google Charts
6. **Búsqueda avanzada**: Full-text search
7. **Auditoría**: Tabla de logs de cambios
8. **Notificaciones**: Email para bajo stock

