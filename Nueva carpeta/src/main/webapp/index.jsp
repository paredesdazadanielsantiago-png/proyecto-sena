<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Gestión de Inventarios</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Sistema de Gestión de Inventarios</h1>
            <p>Aplicación para tiendas pequeñas</p>
        </header>
        
        <nav class="navbar">
            <ul>
                <li><a href="productos?accion=listar" class="btn-nav">Ver Inventario</a></li>
                <li><a href="productos?accion=formulario" class="btn-nav">Nuevo Producto</a></li>
                <li><a href="productos?accion=reporte" class="btn-nav">Reporte</a></li>
            </ul>
        </nav>
        
        <main>
            <section class="bienvenida">
                <h2>Bienvenido</h2>
                <p>Gestiona tu inventario de forma fácil y rápida. Actualiza la cantidad de productos, agrega nuevos artículos y genera reportes de stock.</p>
                
                <div class="opciones">
                    <div class="opcion">
                        <h3>📦 Gestionar Productos</h3>
                        <p>Crea, edita y elimina productos de tu inventario.</p>
                        <a href="productos?accion=listar" class="btn btn-primary">Ir al Inventario</a>
                    </div>
                    
                    <div class="opcion">
                        <h3>📊 Ver Reportes</h3>
                        <p>Consulta estadísticas y reportes de tu inventario.</p>
                        <a href="productos?accion=reporte" class="btn btn-secondary">Ver Reportes</a>
                    </div>
                    
                    <div class="opcion">
                        <h3>➕ Agregar Producto</h3>
                        <p>Registra nuevos productos rápidamente.</p>
                        <a href="productos?accion=formulario" class="btn btn-success">Nuevo Producto</a>
                    </div>
                </div>
            </section>
        </main>
        
        <footer>
            <p>&copy; 2026 Sistema de Inventarios - Tiendas Pequeñas</p>
        </footer>
    </div>
</body>
</html>
