# 📚 Librería — JDBC con patrón MVC + DAO

Aplicación de consola en **Java 21** que gestiona (CRUD completo) los libros de una librería guardados en **MySQL**, usando **JDBC** puro y una arquitectura **MVC con DAO**.

Proyecto de práctica de la formación de desarrollo de software en **Campuslands** (Sesión 18: JDBC con patrón MVC).

---

## ✨ Funcionalidades

- **Registrar** un libro (título, autor y precio).
- **Listar** todos los libros, ordenados por id.
- **Buscar** libros por autor.
- **Actualizar** el título, autor y precio de un libro por su id.
- **Eliminar** un libro por su id.
- **Avisar** si se intenta actualizar o eliminar un id que no existe, usando las filas afectadas que devuelve `executeUpdate()`.
- **Validar** los datos antes de tocar la base de datos: rechaza títulos o autores vacíos, precios menores o iguales a 0, ids menores o iguales a 0 y búsquedas sin autor.

---

## 🛠️ Tecnologías

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje (records, try-with-resources) |
| Maven | Gestión de dependencias |
| MySQL | Base de datos |
| JDBC (`java.sql`) | Conexión y consultas desde Java |
| `mysql-connector-j` | Driver de MySQL para JDBC |
| IntelliJ IDEA | IDE |
| DBeaver CE | Cliente de base de datos |

---

## 🏗️ Arquitectura

El código está dividido en capas. Cada una tiene una sola responsabilidad y solo habla con la de al lado.

```
Main ──órdenes──► Controlador ──► DAO ──► MySQL
                      │            │
                      │     devuelve Libro / List<Libro>
                      ▼
                    Vista ──► consola
```

| Capa | Clase | Responsabilidad |
|---|---|---|
| **Modelo** | `Libro` | Representa un libro. Es un `record` inmutable que viaja entre capas. |
| **DAO** | `LibroDao` | Única clase con SQL y JDBC: `insertar`, `listar`, `buscarPorAutor`, `actualizar` y `eliminar`. No imprime nada y declara `SQLException` con `throws`. |
| **Vista** | `LibroView` | Solo muestra mensajes y listas por consola. |
| **Controlador** | `LibroController` | Valida los datos (con los métodos privados `datosValidos` e `idValido`), llama al DAO, maneja los errores y le pasa el resultado a la vista. |
| **Main** | `Main` | Arma las piezas y ejecuta el flujo de ejemplo. |

```
src/main/java/org/example
├── Main.java
├── model/        → Libro.java
├── dao/          → LibroDao.java
├── view/         → LibroView.java
└── controller/   → LibroController.java
```

### Buenas prácticas aplicadas

- **`PreparedStatement` con `?`** en todas las consultas, para evitar la inyección SQL.
- **try-with-resources** para cerrar automáticamente `Connection`, `PreparedStatement` y `ResultSet`.
- **Validación en el controlador**: los datos inválidos nunca llegan a MySQL. Las reglas viven en métodos privados reutilizables (DRY), así `registrar` y `actualizar` validan exactamente lo mismo.
- **Filas afectadas**: `actualizar` y `eliminar` devuelven cuántas filas cambiaron, y el controlador usa ese número para avisar si el id no existe.
- **Errores manejados en un solo lugar**: el DAO propaga la `SQLException` y el controlador la convierte en un mensaje para el usuario.
- **Credenciales fuera del código**: la contraseña se lee de la variable de entorno `DB_PASS`.

---

## 🚀 Cómo ejecutarlo

### 1. Requisitos

- JDK 21 o superior
- MySQL 8.4 o superior en `localhost:3306`
- IntelliJ IDEA (o Maven desde la terminal)

### 2. Crear la base de datos

Ejecuta el script [`libreria.sql`](libreria.sql) en MySQL (por ejemplo, desde DBeaver).

### 3. Configurar la contraseña

El programa se conecta como `root` y lee la contraseña de la variable de entorno **`DB_PASS`**.

En IntelliJ: **Run → Edit Configurations... → Main → Environment variables**:

```
DB_PASS=tu_contraseña
```

### 4. Ejecutar

Abre el proyecto en IntelliJ, deja que Maven descargue las dependencias y ejecuta `Main`.

---

## 📸 Capturas

### Ejecución en consola

![Salida en consola](docs/capturas/consola.png)

```
Libro registrado exitosamente: Noches Blancas
Libro registrado exitosamente: Habitos Atomicos
Libro actualizado exitosamente: Noches Blancas
No existe un libro con id 99
Debes ingresar un Autor
Libro eliminado exitosamente: 2
No existe el libro con id 99
Debes ingresar un ID
--- Libros ---
1. Noches Blancas - Fiódor Dostoyevski - $25,00
--- Libros ---
1. Noches Blancas - Fiódor Dostoyevski - $25,00
```

Qué muestra esta ejecución:

- Se registran dos libros (ids 1 y 2).
- El libro 1 se **actualiza** a $25,00, y el 2 se **elimina**.
- Actualizar o eliminar el id 99 no da error: `executeUpdate()` devuelve 0 filas y el controlador avisa que no existe.
- El autor vacío y el id 0 se rechazan en el controlador, sin llegar a MySQL.

### Datos guardados en MySQL

![Tabla Libro en DBeaver](docs/capturas/dbeaver.png)

### Estructura del proyecto

![Estructura en IntelliJ](docs/capturas/estructura.png)

---

## 📖 Lo que aprendí

- Cómo conectarse a MySQL desde Java con JDBC: driver, `Connection`, `PreparedStatement` y `ResultSet`.
- La diferencia entre `executeUpdate()` (INSERT, UPDATE, DELETE) y `executeQuery()` (SELECT).
- Por qué los parámetros de JDBC empiezan en 1 y no en 0.
- Cómo separar responsabilidades con MVC + DAO, y por qué el DAO usa `throws` mientras el controlador usa `try/catch`.
- Cuándo usar un `record` (datos que no cambian) y cuándo una clase normal, y por qué `double` y no `Double` para un dato obligatorio.
- Cómo escribir `UPDATE` y `DELETE` con `PreparedStatement`: el número de cada `set...` sigue el orden de los `?` en el SQL, no el de las columnas de la tabla.
- Por qué un `UPDATE` o `DELETE` sin `WHERE` afecta todas las filas.
- Cómo usar las filas afectadas de `executeUpdate()` para saber si un registro existía.
- Cómo aplicar DRY moviendo las validaciones repetidas a métodos privados.

---

## 👤 Autor

**Eidan Alexander Carreño Cuadros**
GitHub: [@Eidan210](https://github.com/Eidan210) · Portafolio: [eidan210.github.io/portafolio-junior](https://eidan210.github.io/portafolio-junior/)
