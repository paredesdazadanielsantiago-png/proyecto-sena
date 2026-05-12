package com.inventario.servlets;

import com.inventario.dao.ProductoDAO;
import com.inventario.model.Producto;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet principal para gestionar operaciones CRUD de productos
 * Maneja métodos GET (lectura) y POST (creación/actualización/eliminación)
 */
public class ProductoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            accion = "listar";
        }
        
        switch (accion) {
            case "listar":
                listarProductos(request, response);
                break;
            case "editar":
                editarProducto(request, response);
                break;
            case "eliminar":
                eliminarProducto(request, response);
                break;
            case "formulario":
                mostrarFormulario(request, response);
                break;
            case "reporte":
                generarReporte(request, response);
                break;
            default:
                listarProductos(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            accion = "crear";
        }
        
        switch (accion) {
            case "crear":
                crearProducto(request, response);
                break;
            case "actualizar":
                actualizarProducto(request, response);
                break;
            case "eliminar":
                eliminarProductoConfirmado(request, response);
                break;
            default:
                listarProductos(request, response);
                break;
        }
    }
    
    /**
     * Acción GET: Muestra la lista de todos los productos
     */
    private void listarProductos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Producto> productos = ProductoDAO.obtenerTodos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/jsp/listar-productos.jsp").forward(request, response);
    }
    
    /**
     * Acción GET: Muestra el formulario para crear o editar un producto
     */
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idProducto = request.getParameter("id");
        Producto producto = null;
        String titulo = "Crear Nuevo Producto";
        
        if (idProducto != null && !idProducto.isEmpty()) {
            int id = Integer.parseInt(idProducto);
            producto = ProductoDAO.obtenerPorId(id);
            titulo = "Editar Producto";
        }
        
        request.setAttribute("producto", producto);
        request.setAttribute("titulo", titulo);
        request.getRequestDispatcher("/jsp/formulario-producto.jsp").forward(request, response);
    }
    
    /**
     * Acción GET: Obtiene un producto para editar
     */
    private void editarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        mostrarFormulario(request, response);
    }
    
    /**
     * Acción GET: Muestra página de confirmación de eliminación
     */
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idProducto = request.getParameter("id");
        if (idProducto != null && !idProducto.isEmpty()) {
            int id = Integer.parseInt(idProducto);
            Producto producto = ProductoDAO.obtenerPorId(id);
            request.setAttribute("producto", producto);
        }
        
        request.getRequestDispatcher("/jsp/confirmar-eliminar.jsp").forward(request, response);
    }
    
    /**
     * Acción POST: Crea un nuevo producto
     */
    private void crearProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Obtener parámetros del formulario
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String categoria = request.getParameter("categoria");
            
            // Crear objeto Producto
            Producto producto = new Producto(codigo, nombre, descripcion, precio, cantidad, categoria);
            
            // Guardar en BD
            boolean exito = ProductoDAO.insertarProducto(producto);
            
            if (exito) {
                request.getSession().setAttribute("mensaje", "Producto creado exitosamente");
                response.sendRedirect("productos?accion=listar");
            } else {
                request.getSession().setAttribute("error", "Error al crear el producto");
                response.sendRedirect("productos?accion=formulario");
            }
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: valores numéricos inválidos");
            response.sendRedirect("productos?accion=formulario");
        }
    }
    
    /**
     * Acción POST: Actualiza un producto existente
     */
    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String codigo = request.getParameter("codigo");
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String categoria = request.getParameter("categoria");
            
            Producto producto = new Producto(codigo, nombre, descripcion, precio, cantidad, categoria);
            producto.setId(id);
            
            boolean exito = ProductoDAO.actualizarProducto(producto);
            
            if (exito) {
                request.getSession().setAttribute("mensaje", "Producto actualizado exitosamente");
                response.sendRedirect("productos?accion=listar");
            } else {
                request.getSession().setAttribute("error", "Error al actualizar el producto");
                response.sendRedirect("productos?accion=editar&id=" + id);
            }
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: valores numéricos inválidos");
            response.sendRedirect("productos?accion=listar");
        }
    }
    
    /**
     * Acción POST: Elimina un producto confirmado
     */
    private void eliminarProductoConfirmado(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            boolean exito = ProductoDAO.eliminarProducto(id);
            
            if (exito) {
                request.getSession().setAttribute("mensaje", "Producto eliminado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al eliminar el producto");
            }
            
            response.sendRedirect("productos?accion=listar");
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: ID de producto inválido");
            response.sendRedirect("productos?accion=listar");
        }
    }
    
    /**
     * Acción GET: Genera un reporte de inventario
     */
    private void generarReporte(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Producto> productos = ProductoDAO.obtenerTodos();
        
        // Calcular estadísticas
        double valorInventario = 0;
        int totalProductos = 0;
        int productosBajoStock = 0;
        
        for (Producto p : productos) {
            valorInventario += p.getPrecio() * p.getCantidad();
            totalProductos += p.getCantidad();
            if (p.getCantidad() < 5) {
                productosBajoStock++;
            }
        }
        
        request.setAttribute("productos", productos);
        request.setAttribute("valorInventario", valorInventario);
        request.setAttribute("totalProductos", totalProductos);
        request.setAttribute("productosBajoStock", productosBajoStock);
        request.setAttribute("cantidadRegistros", productos.size());
        
        request.getRequestDispatcher("/jsp/reporte-inventario.jsp").forward(request, response);
    }
}
