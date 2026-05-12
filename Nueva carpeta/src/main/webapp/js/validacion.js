// Validación de formularios
document.addEventListener('DOMContentLoaded', function() {
    const formulario = document.querySelector('.formulario-producto');
    
    if (formulario) {
        formulario.addEventListener('submit', function(e) {
            // Validar campos requeridos
            const codigo = document.getElementById('codigo').value.trim();
            const nombre = document.getElementById('nombre').value.trim();
            const precio = document.getElementById('precio').value;
            const cantidad = document.getElementById('cantidad').value;
            const categoria = document.getElementById('categoria').value;
            
            if (!codigo) {
                e.preventDefault();
                alert('El código del producto es requerido');
                return false;
            }
            
            if (!nombre) {
                e.preventDefault();
                alert('El nombre del producto es requerido');
                return false;
            }
            
            if (!categoria) {
                e.preventDefault();
                alert('Debe seleccionar una categoría');
                return false;
            }
            
            if (!precio || parseFloat(precio) < 0) {
                e.preventDefault();
                alert('Debe ingresar un precio válido (mayor o igual a 0)');
                return false;
            }
            
            if (!cantidad || parseInt(cantidad) < 0) {
                e.preventDefault();
                alert('Debe ingresar una cantidad válida (mayor o igual a 0)');
                return false;
            }
            
            return true;
        });
    }
});

// Función para confirmar eliminación
function confirmarEliminacion() {
    return confirm('¿Está seguro de que desea eliminar este producto? Esta acción no se puede deshacer.');
}

// Función para formatear moneda
function formatearMoneda(valor) {
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP'
    }).format(valor);
}
