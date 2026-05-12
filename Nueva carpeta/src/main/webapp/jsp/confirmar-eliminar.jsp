<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar Eliminación</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Confirmar Eliminación</h1>
            <a href="productos?accion=listar" class="btn-home">← Volver</a>
        </header>
        
        <main>
            <div class="confirmacion-container">
                <div class="alert alert-warning">
                    <h2>⚠️ Advertencia</h2>
                    <p>¿Está seguro de que desea eliminar este producto? Esta acción no se puede deshacer.</p>
                </div>
                
                <c:if test="${not empty producto}">
                    <div class="detalles-producto">
                        <h3>Producto a Eliminar:</h3>
                        <table class="tabla-detalles">
                            <tr>
                                <th>ID:</th>
                                <td>${producto.id}</td>
                            </tr>
                            <tr>
                                <th>Código:</th>
                                <td>${producto.codigo}</td>
                            </tr>
                            <tr>
                                <th>Nombre:</th>
                                <td>${producto.nombre}</td>
                            </tr>
                            <tr>
                                <th>Categoría:</th>
                                <td>${producto.categoria}</td>
                            </tr>
                            <tr>
                                <th>Precio:</th>
                                <td>$${producto.precio}</td>
                            </tr>
                            <tr>
                                <th>Cantidad:</th>
                                <td>${producto.cantidad}</td>
                            </tr>
                        </table>
                    </div>
                    
                    <div class="confirmacion-acciones">
                        <form method="POST" action="productos" style="display: inline;">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="id" value="${producto.id}">
                            <button type="submit" class="btn btn-danger">🗑️ Eliminar Definitivamente</button>
                        </form>
                        <a href="productos?accion=listar" class="btn btn-secondary">Cancelar</a>
                    </div>
                </c:if>
                
                <c:if test="${empty producto}">
                    <div class="alert alert-error">
                        <p>Producto no encontrado.</p>
                        <a href="productos?accion=listar" class="btn btn-secondary">Volver al Inventario</a>
                    </div>
                </c:if>
            </div>
        </main>
        
        <footer>
            <p>&copy; 2026 Sistema de Inventarios</p>
        </footer>
    </div>
</body>
</html>
