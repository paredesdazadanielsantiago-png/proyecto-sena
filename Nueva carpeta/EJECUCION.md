# Guía de Ejecución del Proyecto

## Paso 1: Configurar la Base de Datos MySQL

### Opción A: Usando línea de comandos
```bash
mysql -u root -p < database-setup.sql
```

### Opción B: Usando MySQL Workbench o PHPMyAdmin
1. Crear base de datos: `inventario_db`
2. Ejecutar el script `database-setup.sql`

## Paso 2: Actualizar Credenciales de Conexión

Editar: `src/main/java/com/inventario/dao/ConexionDB.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/inventario_db";
private static final String USUARIO = "root";      // TU USUARIO
private static final String CONTRASEÑA = "";       // TU CONTRASEÑA
```

## Paso 3: Compilar el Proyecto

```bash
# Limpiar y compilar
gradle clean build

# Generar archivo WAR
gradle war
```

## Paso 4: Desplegar en Apache Tomcat

### Opción A: Copia del archivo WAR
```bash
# Windows
copy build\libs\inventario-app.war C:\apache-tomcat-9.0\webapps\

# Linux/Mac
cp build/libs/inventario-app.war /usr/local/tomcat/webapps/
```

### Opción B: Usando Tomcat Manager
1. Acceder a: http://localhost:8080/manager/html
2. Usuario: admin / admin
3. Seleccionar "Elegir archivo" → `build/libs/inventario-app.war`
4. Clic en "Desplegar"

## Paso 5: Iniciar Tomcat

### Windows
```bash
%CATALINA_HOME%\bin\startup.bat
```

### Linux/Mac
```bash
$CATALINA_HOME/bin/startup.sh
```

## Paso 6: Acceder a la Aplicación

Abrir navegador y dirigirse a:
```
http://localhost:8080/inventario-app/
```

---

## Solución de Problemas

### Error: "No suitable driver found"
```
Solución: Ejecutar gradle clean build nuevamente
```

### Error: "Access denied for user 'root'"
```
Solución: Verificar credenciales en ConexionDB.java
Asegurar que MySQL esté ejecutándose
```

### Error: "404 Page Not Found"
```
Solución: Verificar que Tomcat esté corriendo en puerto 8080
Verificar que el WAR esté en webapps/
Acceder a http://localhost:8080/manager/html para verificar despliegue
```

### Puerto 8080 en uso
```
Solución A: Cambiar puerto en %CATALINA_HOME%\conf\server.xml
Solución B: Terminar proceso usando puerto 8080
```

---

## Cambios Importantes

1. **Credenciales MySQL**: Actualizar en `ConexionDB.java`
2. **Puerto Tomcat**: Por defecto 8080
3. **Contexto de aplicación**: `/inventario-app`

## Variables de Entorno (Recomendado)

```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-11
set CATALINA_HOME=C:\apache-tomcat-9.0

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export CATALINA_HOME=/usr/local/tomcat
```

---

## Verificación Post-Instalación

- [ ] Aplicación accesible en http://localhost:8080/inventario-app/
- [ ] Botón "Ver Inventario" carga la tabla de productos
- [ ] Botón "Nuevo Producto" muestra formulario
- [ ] Se puede crear un nuevo producto
- [ ] Se puede editar un producto existente
- [ ] Se puede eliminar un producto
- [ ] Se puede generar reporte

---

## Desarrollo y Debug

### Ejecutar Gradle en modo verbose
```bash
gradle build -i
```

### Ver logs de Tomcat
```bash
# Windows
tail -f %CATALINA_HOME%\logs\catalina.log

# Linux/Mac
tail -f $CATALINA_HOME/logs/catalina.log
```

### Compilar sin tests
```bash
gradle build -x test
```

---

## Contacto y Soporte

Revisar `README.md` para documentación completa del proyecto.

