# Informe Técnico — Entrega 3
## Proyecto: Sistema de Gestión de Clínica Odontológica "Sonrisa Feliz"
**Materia:** Paradigmas de Programación — UADE FAIN  
**Docente:** Lic. Claudio Godio  
**Año:** 2026

---

## 1. Introducción

El presente informe documenta los aportes técnicos realizados en la tercera entrega del proyecto integrador de la materia Paradigmas de Programación. El sistema modela una clínica odontológica y permite gestionar pacientes, odontólogos y turnos a través de una interfaz de texto en consola.

En esta entrega se incorporaron los siguientes conceptos del paradigma orientado a objetos:

- Jerarquía de excepciones chequeadas personalizadas
- Uso avanzado de la API de Streams de Java
- Implementación de la interfaz `Comparable<T>`
- Persistencia de datos mediante archivos CSV
- Dos diagramas de secuencia UML que modelan flujos clave del sistema

---

## 2. Jerarquía de Excepciones

### 2.1 Diseño general

Se implementó una jerarquía de excepciones **chequeadas** (es decir, subclases de `Exception`, no de `RuntimeException`). Esto obliga al compilador a verificar que todo código que pueda lanzar una excepción la declare o la atrape, lo cual hace explícito el contrato de cada método.

La clase base es `ClinicaException`, de la que heredan cuatro subclases especializadas:

```
Exception
  └── ClinicaException
        ├── PacienteNoEncontradoException
        ├── OdontologoNoEncontradoException
        ├── TurnoYaReservadoException
        └── DatoInvalidoException
```

Todas las excepciones reciben un mensaje descriptivo como parámetro de constructor, que es propagado mediante `super(mensaje)` y luego recuperado con `getMessage()` en la capa de presentación.

### 2.2 Descripción de cada excepción

#### `ClinicaException`
**Clase base** de la jerarquía. Extiende directamente de `Exception`. No se lanza directamente en el sistema; su rol es unificar todas las excepciones de negocio bajo un tipo común. El `ClinicaController` captura siempre `ClinicaException` para mostrar el mensaje de error sin importar el subtipo concreto.

#### `PacienteNoEncontradoException`
**Cuándo se lanza:** Cuando se intenta buscar, actualizar o eliminar un paciente cuyo ID o DNI no existe en el repositorio.

**Ejemplos de situaciones:**
- Se intenta registrar un turno para el paciente con ID 99, pero ese paciente no está en el sistema.
- El usuario busca un paciente por DNI y ninguno coincide.
- Se intenta eliminar un paciente que ya fue eliminado previamente.

**Lanzada por:** `PacienteServiceImpl.buscarPorId()`, `PacienteServiceImpl.buscarPorDni()`, `PacienteServiceImpl.eliminarPorId()`, `PacienteServiceImpl.actualizar()`.

#### `OdontologoNoEncontradoException`
**Cuándo se lanza:** Cuando se intenta buscar, actualizar o eliminar un odontólogo cuyo ID o matrícula no existe en el sistema.

**Ejemplos de situaciones:**
- Al registrar un turno, el ID del odontólogo ingresado no corresponde a ningún odontólogo registrado.
- Se busca un odontólogo por matrícula y esta no existe.

**Lanzada por:** `OdontologoServiceImpl.buscarPorId()`, `OdontologoServiceImpl.buscarPorMatricula()`, `OdontologoServiceImpl.eliminarPorId()`, `OdontologoServiceImpl.actualizar()`.

#### `TurnoYaReservadoException`
**Cuándo se lanza:** Cuando se intenta registrar o modificar un turno en una fecha y hora en que el odontólogo ya tiene otro turno activo (estado PENDIENTE o CONFIRMADO).

**Ejemplo de situación:**
- El Dr. García tiene turno el 10/06/2026 a las 09:00. Si se intenta asignarle otro turno el mismo día y horario, el sistema rechaza la operación con este error.

**Lanzada por:** `TurnoServiceImpl.verificarDisponibilidadOdontologo()`, invocada desde `guardar()` y `actualizar()`.

**Nota de diseño:** Los turnos CANCELADOS no bloquean horarios — la verificación los excluye explícitamente.

#### `DatoInvalidoException`
**Cuándo se lanza:** Cuando un dato ingresado no supera las validaciones del sistema antes de persistirse. Cubre validaciones tanto de pacientes como de turnos.

**Ejemplos de situaciones para pacientes:**
- Nombre o apellido vacío, o contiene caracteres no alfabéticos.
- DNI con menos de 7 o más de 8 dígitos.
- Email sin formato válido (sin `@` o sin dominio).
- Fecha de ingreso posterior a hoy.

**Ejemplos de situaciones para turnos:**
- Fecha del turno anterior al día de hoy.
- Hora fuera del rango de atención (08:00–19:59).
- Hora no alineada a intervalos de 30 minutos (ej: 09:15 no es válida; sí lo son 09:00 y 09:30).
- Paciente u odontólogo nulos en el objeto Turno.

**Lanzada por:** `PacienteServiceImpl.validarDatos()`, `OdontologoServiceImpl.validarDatos()`, `TurnoServiceImpl.validarDatos()`, `OdontologoServiceImpl.crearOdontologo()`.

### 2.3 Captura en la capa de presentación

El `ClinicaController` captura todas las excepciones de forma uniforme:

```java
try {
    servicioTurno.registrarTurno(idPaciente, idOdontologo, fecha, hora);
    vista.mostrarMensaje("Turno registrado correctamente.");
} catch (ClinicaException e) {
    vista.mostrarError(e.getMessage());
}
```

Al capturar el tipo base `ClinicaException`, el controlador no necesita conocer el subtipo específico. El mensaje de error ya fue redactado por la clase que lanzó la excepción, y la vista lo presenta directamente al usuario.

---

## 3. Diagrama de Secuencia 1 — Alta de un Nuevo Turno

### 3.1 Descripción del flujo

Este diagrama modela el proceso completo de registrar un turno en el sistema. Participan cinco capas: el usuario, la vista, el controlador, los servicios y el repositorio.

El flujo comienza cuando el usuario selecciona la opción "Registrar turno" en el menú. La vista solicita los datos necesarios (ID de paciente, ID de odontólogo, fecha y hora) y los entrega al controlador como un objeto `DatoTurno`.

El controlador delega la operación a `TurnoServiceImpl.registrarTurno()`, que internamente realiza dos pasos principales:

**Paso 1 — Crear el turno (`crearTurno`):**  
Se consultan los servicios de paciente y odontólogo para recuperar los objetos completos a partir de los IDs provistos. Si alguno no existe, se lanza la excepción correspondiente (`PacienteNoEncontradoException` u `OdontologoNoEncontradoException`) y el flujo se interrumpe. El controlador captura la excepción y muestra el error en pantalla.

**Paso 2 — Guardar el turno (`guardar`):**  
Se validan los datos del turno (fecha, hora, horario de atención). Si alguno es inválido, se lanza `DatoInvalidoException`. Luego se verifica disponibilidad del odontólogo mediante un recorrido con `Stream.anyMatch()` sobre todos los turnos existentes. Si hay conflicto de horario, se lanza `TurnoYaReservadoException`. Si todo es válido, el turno se persiste en el repositorio y se agrega al historial clínico del paciente.

### 3.2 Decisiones de diseño destacadas

- **Separación en dos grupos (`crearTurno` y `guardar`):** Permite que la creación del objeto `Turno` (que requiere objetos completos, no solo IDs) esté desacoplada de la lógica de persistencia.
- **Verificación de disponibilidad antes de persistir:** Se aplica el principio de "fail fast" — el sistema rechaza la operación lo antes posible.
- **Uso de Stream en lugar de `for`:** La verificación `anyMatch()` es más expresiva y permite agregar condiciones adicionales sin modificar la estructura del bucle.

---

## 4. Diagrama de Secuencia 2 — Búsqueda de Paciente por DNI

### 4.1 Descripción del flujo

Este diagrama modela un caso de uso más simple pero igualmente representativo: la búsqueda de un paciente utilizando su número de DNI como clave.

El flujo comienza con la selección de la opción correspondiente en el menú. La vista solicita el DNI al usuario (como entero) y lo entrega al controlador. El controlador llama a `PacienteServiceImpl.buscarPorDni(dni)`, que a su vez delega en el repositorio usando:

```java
pacientes.stream()
         .filter(p -> p.getDni().equals(dni))
         .findFirst()
         .orElse(null);
```

El diagrama bifurca en dos alternativas (`alt`):

- **Paciente encontrado:** El repositorio retorna el objeto `Paciente`, el servicio lo propaga al controlador, y la vista muestra los datos del paciente.
- **Paciente no encontrado:** El repositorio retorna `null`, el servicio lanza `PacienteNoEncontradoException` con el mensaje `"No se encontró un paciente con DNI: X"`, el controlador la captura y la vista muestra el error.

En ambos casos, el flujo finaliza con una pausa (`"Presione ENTER para continuar..."`) antes de volver al menú.

### 4.2 Relación con la Stream API

La búsqueda por DNI ejemplifica el uso de Streams para operaciones de consulta sin efectos secundarios. El uso de `findFirst().orElse(null)` en lugar de un bucle `for` con variable temporal hace explícita la intención: encontrar como máximo un resultado. La decisión de retornar `null` (en lugar del `Optional`) fue una elección de estilo para mantener consistencia con el patrón del resto del repositorio.

---

## 5. Otros Conceptos Aplicados

### 5.1 `Comparable<T>` en `Paciente`

Se implementó `Comparable<Paciente>` para permitir ordenamiento natural de la lista de pacientes. El criterio es: ordenar por apellido (ignorando mayúsculas/minúsculas) y, en caso de empate, por nombre. Esto habilita el uso de `stream().sorted()` sin pasar un `Comparator` explícito, simplificando el código del repositorio.

### 5.2 Persistencia CSV

Los tres repositorios cuentan con clases espejo en el paquete `io/`:

- `PersistenciaPaciente` — guarda y carga `pacientes.csv`
- `PersistenciaOdontologo` — guarda y carga `odontologos.csv`
- `PersistenciaTurno` — guarda y carga `turnos.csv`

El formato usa `;` como separador de campos. La carga se realiza al iniciar la aplicación y el guardado se realiza siempre al cerrar, mediante un bloque `try-finally` en `Main.java` que garantiza la persistencia incluso ante errores inesperados.

Un aspecto crítico fue el **orden de carga**: los turnos referencian pacientes y odontólogos por ID, por lo que estos deben estar cargados primero. Además, se implementó un método `cargarEntidad()` en cada repositorio para restaurar los IDs originales desde el CSV sin que el auto-incremento los sobreescriba.

---

## 6. Estructura del Proyecto

| Paquete       | Responsabilidad                                                       |
|---------------|-----------------------------------------------------------------------|
| `model`       | Entidades del dominio (Paciente, Odontologo, Turno, HistorialClinico) |
| `repository`  | Acceso a datos en memoria con Stream API                              |
| `service`     | Lógica de negocio y validaciones; lanza excepciones específicas       |
| `exception`   | Jerarquía de excepciones chequeadas                                   |
| `ui`          | Vista (entrada/salida en consola) y controlador principal             |
| `io`          | Lectura y escritura de archivos CSV                                   |
| `main`        | Punto de entrada; configura e inyecta todas las dependencias          |

---

## 7. Conclusión

La tercera entrega consolida el proyecto con mecanismos de manejo de errores robusto, capacidad de persistencia entre ejecuciones y búsquedas avanzadas. La jerarquía de excepciones chequeadas garantiza que ningún error de negocio pase silenciosamente: cada caso de falla tiene una clase que lo representa, un mensaje que lo describe y un punto de captura que lo comunica al usuario. Los diagramas de secuencia documentan visualmente los flujos más importantes, evidenciando la separación de responsabilidades entre capas que caracteriza a la arquitectura implementada.
