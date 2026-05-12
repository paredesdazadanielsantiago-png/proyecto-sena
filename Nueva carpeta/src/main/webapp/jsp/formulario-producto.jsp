<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${titulo}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>${titulo}</h1>
            <a href="productos?accion=listar" class="btn-home">← Volver</a>
        </header>
        
        <main>
            <div class="formulario-container">
                <form method="POST" action="productos" class="formulario-producto">
                    <input type="hidden" name="accion" value="${not empty producto ? 'actualizar' : 'crear'}">
                    
                    <c:if test="${not empty producto}">
                        <input type="hidden" name="id" value="${producto.id}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="codigo">Código del Producto*</label>
                        <input type="text" id="codigo" name="codigo" 
                               value="${not empty producto ? producto.codigo : ''}" 
                               required placeholder="Ej: PROD001">
                    </div>
                    
                    <div class="form-group">
                        <label for="nombre">Nombre del Producto*</label>
                        <input type="text" id="nombre" name="nombre" 
                               value="${not empty producto ? producto.nombre : ''}" 
                               required placeholder="Ej: Camiseta Azul">
                    </div>
                    
                    <div class="form-group">
                        <label for="descripcion">Descripción</label>
                        <textarea id="descripcion" name="descripcion" 
                                  rows="4" placeholder="Describa el producto...">${not empty producto ? producto.descripcion : ''}</textarea>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="categoria">Categoría*</label>
                            <select id="categoria" name="categoria" required>
                                <option value="">Seleccione una categoría</option>
                                <option value="Ropa" ${not empty producto && producto.categoria == 'Ropa' ? 'selected' : ''}>Ropa</option>
                                <option value="Electrónica" ${not empty producto && producto.categoria == 'Electrónica' ? 'selected' : ''}>Electrónica</option>
                                <option value="Alimentación" ${not empty producto && producto.categoria == 'Alimentación' ? 'selected' : ''}>Alimentación</option>
                                <option value="Otros" ${not empty producto && producto.categoria == 'Otros' ? 'selected' : ''}>Otros</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="precio">Precio Unitario ($)*</label>
                            <input type="number" id="precio" name="precio" 
                                   value="${not empty producto ? producto.precio : ''}" 
                                   step="0.01" required min="0" placeholder="0.00">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="cantidad">Cantidad en Stock*</label>
                        <input type="number" id="cantidad" name="cantidad" 
                               value="${not empty producto ? producto.cantidad : ''}" 
                               required min="0" placeholder="0">
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <c:if test="${empty producto}">✓ Crear Producto</c:if>
                            <c:if test="${not empty producto}">✓ Actualizar Producto</c:if>
                        </button>
                        <a href="productos?accion=listar" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
        
        <footer>
            <p>&copy; 2026 Sistema de Inventarios</p>
        </footer>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/validacion.js"></script>
</body>
</html>
