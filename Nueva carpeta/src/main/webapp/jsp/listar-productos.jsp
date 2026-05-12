<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventario de Productos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Inventario de Productos</h1>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-home">← Inicio</a>
        </header>
        
        <nav class="navbar">
            <ul>
                <li><a href="productos?accion=formulario" class="btn-nav">➕ Agregar Producto</a></li>
                <li><a href="productos?accion=reporte" class="btn-nav">📊 Ver Reporte</a></li>
            </ul>
        </nav>
        
        <main>
            <!-- Mensajes de sesión -->
            <c:if test="${not empty sessionScope.mensaje}">
                <div class="alert alert-success">
                    ${sessionScope.mensaje}
                    <% session.removeAttribute("mensaje"); %>
                </div>
            </c:if>
            
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-error">
                    ${sessionScope.error}
                    <% session.removeAttribute("error"); %>
                </div>
            </c:if>
            
            <!-- Tabla de productos -->
            <c:if test="${not empty productos}">
                <div class="tabla-responsive">
                    <table class="tabla-productos">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Código</th>
                                <th>Nombre</th>
                                <th>Categoría</th>
                                <th>Precio</th>
                                <th>Cantidad</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${productos}">
                                <tr class="<c:if test="${producto.cantidad < 5}">bajo-stock</c:if>">
                                    <td>${producto.id}</td>
                                    <td>${producto.codigo}</td>
                                    <td>${producto.nombre}</td>
                                    <td>${producto.categoria}</td>
                                    <td>$${producto.precio}</td>
                                    <td>
                                        <span class="cantidad 
                                            <c:if test="${producto.cantidad < 5}">cantidad-bajo</c:if>">
                                            ${producto.cantidad}
                                        </span>
                                    </td>
                                    <td class="acciones">
                                        <a href="productos?accion=editar&id=${producto.id}" class="btn btn-sm btn-editar">✏️ Editar</a>
                                        <a href="productos?accion=eliminar&id=${producto.id}" class="btn btn-sm btn-eliminar" onclick="return confirm('¿Está seguro de eliminar este producto?')">🗑️ Eliminar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="resumen">
                    <p><strong>Total de registros: ${productos.size()}</strong></p>
                </div>
            </c:if>
            
            <c:if test="${empty productos}">
                <div class="alert alert-info">
                    <p>No hay productos en el inventario. <a href="productos?accion=formulario">Agregar uno ahora</a></p>
                </div>
            </c:if>
        </main>
        
        <footer>
            <p>&copy; 2026 Sistema de Inventarios</p>
        </footer>
    </div>
</body>
</html>
