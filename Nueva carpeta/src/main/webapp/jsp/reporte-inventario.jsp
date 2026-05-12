<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte de Inventario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Reporte de Inventario</h1>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-home">← Inicio</a>
        </header>
        
        <nav class="navbar">
            <ul>
                <li><a href="productos?accion=listar" class="btn-nav">← Volver al Inventario</a></li>
                <li><a href="#" class="btn-nav" onclick="window.print()">🖨️ Imprimir</a></li>
            </ul>
        </nav>
        
        <main>
            <section class="reporte">
                <h2>Resumen del Inventario</h2>
                
                <div class="estadisticas">
                    <div class="estadistica-card">
                        <h3>Total de Productos</h3>
                        <p class="numero">${cantidadRegistros}</p>
                    </div>
                    
                    <div class="estadistica-card">
                        <h3>Unidades en Stock</h3>
                        <p class="numero">${totalProductos}</p>
                    </div>
                    
                    <div class="estadistica-card">
                        <h3>Valor Total del Inventario</h3>
                        <p class="numero">$<fmt:formatNumber value="${valorInventario}" pattern="#,##0.00"/></p>
                    </div>
                    
                    <div class="estadistica-card">
                        <h3>Productos Bajo Stock</h3>
                        <p class="numero" style="color: #e74c3c;">${productosBajoStock}</p>
                    </div>
                </div>
                
                <h2>Detalle de Productos</h2>
                <c:if test="${not empty productos}">
                    <div class="tabla-responsive">
                        <table class="tabla-reporte">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Categoría</th>
                                    <th>Precio Unit.</th>
                                    <th>Cantidad</th>
                                    <th>Valor Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="producto" items="${productos}">
                                    <tr class="<c:if test="${producto.cantidad < 5}">bajo-stock</c:if>">
                                        <td>${producto.codigo}</td>
                                        <td>${producto.nombre}</td>
                                        <td>${producto.categoria}</td>
                                        <td>$<fmt:formatNumber value="${producto.precio}" pattern="#,##0.00"/></td>
                                        <td>
                                            <c:if test="${producto.cantidad < 5}">
                                                <span class="alerta-cantidad">⚠️ ${producto.cantidad}</span>
                                            </c:if>
                                            <c:if test="${producto.cantidad >= 5}">
                                                ${producto.cantidad}
                                            </c:if>
                                        </td>
                                        <td>$<fmt:formatNumber value="${producto.precio * producto.cantidad}" pattern="#,##0.00"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                
                <c:if test="${productosBajoStock > 0}">
                    <div class="alert alert-warning" style="margin-top: 20px;">
                        <h3>⚠️ Productos con Bajo Stock</h3>
                        <p>Hay ${productosBajoStock} producto(s) con cantidad menor a 5 unidades.</p>
                        <ul>
                            <c:forEach var="producto" items="${productos}">
                                <c:if test="${producto.cantidad < 5}">
                                    <li>${producto.nombre} (${producto.cantidad} unidades)</li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                
                <div class="pie-reporte">
                    <p><small>Reporte generado el: <fmt:formatDate value="${java.util.Calendar.getInstance().time}" pattern="dd/MM/yyyy HH:mm:ss"/></small></p>
                </div>
            </section>
        </main>
        
        <footer>
            <p>&copy; 2026 Sistema de Inventarios</p>
        </footer>
    </div>
</body>
</html>
