# Entrega 3 — Clínica Odontológica "Sonrisa Feliz"
### Programación Orientada a Objetos — UADE FAIN
### Profesor: Lic. Claudio Godio | Ciclo Lectivo 2026

---

## ¿De qué trata este proyecto?

Estamos construyendo el sistema de software de una clínica odontológica llamada **"Sonrisa Feliz"**. El sistema permite gestionar pacientes, odontólogos y turnos desde la consola (la pantalla de texto negra de la computadora).

Este documento explica todo lo que se construyó en la **Entrega 3**, que es la tercera y última etapa del proyecto.

---

## ¿Qué había antes de la Entrega 3?

El proyecto ya tenía dos entregas anteriores:

### Entrega 1 — Los personajes del sistema
Se crearon las **clases base**: las "fichas" que representan las cosas del mundo real.

- **Paciente**: tiene nombre, apellido, DNI, email, fecha de ingreso, domicilio y obra social.
- **Odontólogo**: tiene nombre, apellido y matrícula profesional. Puede ser *Ortodoncista* o *Endodoncista*, cada uno con duración de consulta diferente.
- **Turno**: tiene fecha, hora, estado (pendiente/confirmado/cancelado/completado) y vincula a un paciente con un odontólogo.
- **Domicilio**: calle, número, localidad, provincia.

### Entrega 2 — La lógica del sistema
Se agregó la **capa de servicios**: las reglas de negocio y el menú por consola.

- **Servicios**: clases que validan los datos antes de guardarlos (por ejemplo, que el DNI tenga entre 7 y 8 dígitos, que el email tenga formato válido, que el horario del turno sea entre 08:00 y 19:59).
- **Repositorios**: "cajones" donde se guardan los datos en la memoria de la computadora mientras el programa está corriendo.
- **Interfaz por consola**: un menú de opciones numeradas para que el usuario pueda registrar, buscar, modificar y eliminar pacientes, odontólogos y turnos.

**Problema de la Entrega 2**: cuando se cerraba el programa, todos los datos se perdían. La próxima vez que se abría, había que cargar todo de nuevo.

---

## ¿Qué se hizo en la Entrega 3?

La Entrega 3 agrega tres grandes mejoras:

1. **Los datos ya no se pierden** al cerrar el programa (persistencia en archivos CSV).
2. **Los errores se manejan con gracia** mediante excepciones personalizadas.
3. **Se pueden hacer búsquedas avanzadas** (por fecha, por odontólogo, por paciente, ordenados alfabéticamente).

---

## Cómo está organizado el código

El código está dividido en **paquetes** (carpetas). Cada paquete tiene una responsabilidad específica:

```
src/
├── model/        → Las "fichas" del sistema (Paciente, Odontólogo, Turno, etc.)
├── repository/   → Los "cajones" donde se guardan los datos en memoria
├── service/      → Las reglas de negocio y validaciones
├── exception/    → Los errores personalizados del sistema
├── ui/           → Lo que ve y usa el usuario (menús, formularios)
├── io/           → La lectura y escritura de archivos CSV
└── main/         → El punto de entrada del programa
```

Esta estructura se llama **arquitectura en capas** y es como una empresa bien organizada: cada departamento hace su trabajo y no se mete en el de los demás.

---

## Parte 1: Excepciones Personalizadas

### ¿Qué es una excepción?

Imaginá que pedís un producto en un negocio y el vendedor te dice "no tenemos stock". En programación, eso se llama una **excepción**: algo que salió mal y que el sistema debe comunicar de forma clara.

Antes de esta entrega, cuando algo salía mal, el programa simplemente mostraba un mensaje como `[ERROR] No se encontró...` y devolvía un resultado vacío. Ahora el programa **lanza una excepción** que viaja por el sistema hasta llegar a alguien que la pueda manejar.

### Las excepciones que creamos

Creamos una jerarquía (como un árbol de familia):

```
ClinicaException  ← la "madre" de todas las excepciones del sistema
    ├── PacienteNoEncontradoException
    ├── OdontologoNoEncontradoException
    ├── TurnoYaReservadoException
    └── DatoInvalidoException
```

#### `ClinicaException`
Es la clase base. Todas las excepciones del sistema heredan de ella. Esto permite al menú capturar **cualquier** error del sistema con un solo bloque de código.

#### `PacienteNoEncontradoException`
Se lanza cuando se busca un paciente por ID o DNI y no existe en el sistema.

*Ejemplo real:* El usuario ingresa "Buscar paciente por DNI: 12345678" y ese DNI no está registrado → el sistema lanza esta excepción → el menú muestra: `[ERROR] No se encontró un paciente con DNI: 12345678`

#### `OdontologoNoEncontradoException`
Igual que la anterior, pero para odontólogos.

#### `TurnoYaReservadoException`
Se lanza cuando se intenta crear un turno en una fecha y hora en la que el odontólogo ya tiene otro turno.

*Ejemplo real:* El Dr. García ya tiene turno el lunes a las 10:00. Alguien intenta registrar otro turno para él a la misma hora → excepción → `[ERROR] El odontólogo ya tiene un turno asignado el 2026-06-01 a las 10:00.`

#### `DatoInvalidoException`
Se lanza cuando un dato ingresado no cumple las reglas de validación.

*Ejemplos:* nombre vacío, DNI con menos de 7 dígitos, email sin "@", hora de turno a las 23:00 (fuera del horario de atención).

### ¿Cómo se usa en el código?

**Antes (Entrega 2):**
```java
// El servicio devolvía null si no encontraba al paciente
Paciente paciente = servicio.buscarPorId(id);
if (paciente != null) {
    mostrar(paciente);
} else {
    System.out.println("[ERROR] No encontrado");
}
```

**Después (Entrega 3):**
```java
// El servicio lanza una excepción si no encuentra al paciente
try {
    Paciente paciente = servicio.buscarPorId(id);
    mostrar(paciente);
} catch (ClinicaException e) {
    vista.mostrarError(e.getMessage());
}
```

La ventaja es que el error **no puede ser ignorado accidentalmente** — Java obliga a manejarlo.

---

## Parte 2: Colecciones Avanzadas y Stream API

### ¿Qué es un Stream?

Imaginá que tenés una lista de 100 turnos y querés encontrar solo los del mes de junio. Antes tenías que escribir un ciclo `for` que recorriera todos los turnos y los fuera filtrando uno a uno. Con **Stream API**, podés expresar eso de forma mucho más directa y legible.

### ¿Qué implementamos?

#### Comparable en Paciente
Hicimos que la clase `Paciente` sepa **compararse** con otro paciente. La regla es: primero ordena por apellido (A→Z), y si hay empate, por nombre.

```java
// Paciente ahora implementa Comparable<Paciente>
public int compareTo(Paciente otro) {
    int comparacionApellido = this.apellido.compareToIgnoreCase(otro.apellido);
    if (comparacionApellido != 0) return comparacionApellido;
    return this.nombre.compareToIgnoreCase(otro.nombre);
}
```

Esto permite usar `Collections.sort()` directamente en una lista de pacientes.

#### Búsquedas con Stream en los repositorios

**Buscar paciente por DNI:**
```java
return pacientes.stream()
    .filter(p -> p.getDni().equals(dni))
    .findFirst()
    .orElse(null);
```
*Traducción:* "Recorrí la lista, quedate solo con los que tengan este DNI, dame el primero que encuentres, y si no hay ninguno devolvé null."

**Listar pacientes ordenados por apellido:**
```java
return pacientes.stream()
    .sorted()   // usa el compareTo que implementamos arriba
    .collect(Collectors.toList());
```

**Buscar turnos por rango de fechas:**
```java
return turnos.stream()
    .filter(t -> !t.getFecha().isBefore(inicio) && !t.getFecha().isAfter(fin))
    .collect(Collectors.toList());
```
*Traducción:* "Dame todos los turnos cuya fecha esté entre la fecha inicio y la fecha fin."

**Verificar si el odontólogo ya tiene turno:**
```java
return turnos.stream()
    .anyMatch(t -> t.getOdontologo().getId() == idOdontologo
            && t.getFecha().equals(fecha)
            && t.getHora().equals(hora));
```
*Traducción:* "¿Existe al menos un turno de este odontólogo en exactamente esta fecha y hora?"

### Nuevas opciones en el menú

Gracias a todo esto, el menú ahora tiene opciones nuevas:

**Menú de Pacientes:**
- Opción 7: Buscar paciente por DNI
- Opción 8: Listar pacientes ordenados por apellido

**Menú de Turnos:**
- Opción 9: Buscar turnos por rango de fechas
- Opción 10: Filtrar turnos por odontólogo
- Opción 11: Filtrar turnos por paciente

---

## Parte 3: Persistencia — Los datos ya no se pierden

### El problema

Antes de esta entrega, los datos vivían únicamente en la **memoria RAM** de la computadora. La memoria RAM es como un pizarrón: cuando apagás la computadora (o cerrás el programa), se borra todo.

### La solución: archivos CSV

Un archivo **CSV** (Comma-Separated Values, o en nuestro caso con punto y coma como separador) es un archivo de texto simple que cualquiera puede abrir con Excel o el Bloc de Notas. Cada línea es un registro, y los campos están separados por `;`.

**Ejemplo de `pacientes.csv`:**
```
1;Juan;Perez;30123456;juan@email.com;2024-03-15;Corrientes;1234;Buenos Aires;CABA;OSDE
2;Maria;Garcia;28456789;maria@email.com;2023-11-20;Rivadavia;567;Córdoba;Córdoba;
```

**Ejemplo de `odontologos.csv`:**
```
1;Carlos;Lopez;MAT-001;1
2;Ana;Fernandez;MAT-002;2
```
*(El último campo: 1=Ortodoncista, 2=Endodoncista)*

**Ejemplo de `turnos.csv`:**
```
1;1;2;2026-06-10;09:00;PENDIENTE
2;2;1;2026-06-11;14:30;CONFIRMADO
```
*(Los IDs 1;2 refieren al paciente #1 y al odontólogo #2)*

### Las clases que hacen esto

Creamos tres clases en el paquete `io/`:

- **`PersistenciaPaciente`**: sabe cómo leer y escribir `pacientes.csv`
- **`PersistenciaOdontologo`**: sabe cómo leer y escribir `odontologos.csv`
- **`PersistenciaTurno`**: sabe cómo leer y escribir `turnos.csv`. Cuando carga un turno, busca al paciente y al odontólogo por su ID para reconstruir el objeto completo.

### El ciclo de vida de los datos

```
INICIO DEL PROGRAMA
        ↓
Lee pacientes.csv  →  carga Pacientes en memoria
Lee odontologos.csv →  carga Odontólogos en memoria
Lee turnos.csv     →  carga Turnos en memoria (con referencias a Paciente y Odontólogo)
        ↓
El usuario usa el sistema normalmente...
        ↓
FIN DEL PROGRAMA (o crash inesperado)
        ↓
Escribe pacientes.csv  ←  guarda todos los Pacientes
Escribe odontologos.csv ←  guarda todos los Odontólogos
Escribe turnos.csv     ←  guarda todos los Turnos
```

El guardado está dentro de un bloque `finally`, lo que garantiza que **siempre se ejecuta**, incluso si el programa falla de forma inesperada.

```java
try {
    controller.run();  // todo el ciclo de vida del programa
} finally {
    // esto se ejecuta SIEMPRE, pase lo que pase
    PersistenciaPaciente.guardar(repoPaciente.listarTodos());
    PersistenciaOdontologo.guardar(repoOdontologo.listarTodos());
    PersistenciaTurno.guardar(repoTurno.listarTodos());
}
```

---

## Parte 4: Diagramas de Secuencia

Los diagramas de secuencia muestran **cómo se comunican los objetos del sistema** para realizar una tarea. Se leen de arriba hacia abajo, como una conversación.

### Diagrama 1: Alta de un nuevo turno

Este diagrama muestra todo lo que pasa cuando el usuario elige registrar un nuevo turno:

1. El usuario selecciona "Registrar turno" en el menú.
2. El sistema le pide los datos: ID del paciente, ID del odontólogo, fecha y hora.
3. El `TurnoServiceImpl` busca al paciente por ID → si no existe, lanza `PacienteNoEncontradoException`.
4. Busca al odontólogo por ID → si no existe, lanza `OdontologoNoEncontradoException`.
5. Valida los datos del turno (fecha, hora, horario de atención) → si son inválidos, lanza `DatoInvalidoException`.
6. Verifica que el odontólogo no tenga otro turno en esa fecha y hora → si está ocupado, lanza `TurnoYaReservadoException`.
7. Si todo está bien, guarda el turno y muestra "[OK] Turno registrado correctamente."

En cada punto donde puede fallar, el `ClinicaController` captura la excepción y muestra el mensaje de error al usuario sin que el programa se caiga.

### Diagrama 2: Búsqueda de paciente por DNI

Este diagrama muestra qué pasa cuando el usuario busca un paciente por su número de DNI:

1. El usuario selecciona "Buscar paciente por DNI".
2. El sistema le pide el DNI.
3. El `PacienteServiceImpl` le pide al `RepositorioPaciente` que busque con Stream (`.filter().findFirst()`).
4. **Caso exitoso**: el repositorio encuentra al paciente → el servicio lo devuelve → el controlador lo muestra.
5. **Caso error**: el repositorio devuelve null → el servicio lanza `PacienteNoEncontradoException` → el controlador muestra "[ERROR] No se encontró un paciente con DNI: X".

---

## Resumen de todos los archivos del proyecto

### Paquete `model/` — Las entidades del dominio
| Archivo | Descripción |
|---|---|
| `Paciente.java` | Representa a un paciente. Implementa `Comparable` para ordenarse por apellido. |
| `Odontologo.java` | Clase abstracta. Define la estructura base de un odontólogo. |
| `OdontologoOrtodoncista.java` | Subclase de Odontólogo. Consultas de 60 minutos. |
| `OdontologoEndodoncista.java` | Subclase de Odontólogo. Consultas de 90 minutos. |
| `Turno.java` | Representa un turno, vinculando paciente, odontólogo, fecha y hora. |
| `Domicilio.java` | Dirección postal de un paciente. |
| `Consultorio.java` | Consultorio donde se realiza el turno. |
| `HistorialClinico.java` | Historial de turnos de un paciente. |
| `EstadoTurno.java` | Enumeración: PENDIENTE, CONFIRMADO, CANCELADO, COMPLETADO. |
| `Especialidad.java` | Enumeración de especialidades odontológicas. |

### Paquete `repository/` — Los repositorios (memoria)
| Archivo | Descripción |
|---|---|
| `iRepository.java` | Interfaz genérica con los 5 métodos básicos: guardar, buscar, eliminar, actualizar, listar. |
| `RepositorioPaciente.java` | Almacena pacientes en una `List`. Incluye búsqueda por DNI y listado ordenado. |
| `RepositorioOdontologo.java` | Almacena odontólogos. Incluye búsqueda por matrícula. |
| `RepositorioTurno.java` | Almacena turnos. Incluye filtros por rango de fechas, odontólogo y paciente. |

### Paquete `service/` — Las reglas de negocio
| Archivo | Descripción |
|---|---|
| `iService.java` | Interfaz genérica de servicios. Declara que sus métodos pueden lanzar `ClinicaException`. |
| `PacienteServiceImpl.java` | Valida y gestiona pacientes. Lanza `DatoInvalidoException` o `PacienteNoEncontradoException`. |
| `OdontologoServiceImpl.java` | Valida y gestiona odontólogos. Distingue entre Ortodoncista y Endodoncista. |
| `TurnoServiceImpl.java` | Valida turnos, verifica disponibilidad del odontólogo, gestiona estados. |

### Paquete `exception/` — Los errores personalizados
| Archivo | Cuándo se lanza |
|---|---|
| `ClinicaException.java` | Clase base. Nunca se lanza directamente. |
| `PacienteNoEncontradoException.java` | Al buscar un paciente que no existe. |
| `OdontologoNoEncontradoException.java` | Al buscar un odontólogo que no existe. |
| `TurnoYaReservadoException.java` | Al crear un turno en horario ya ocupado por el odontólogo. |
| `DatoInvalidoException.java` | Al ingresar datos que no pasan las validaciones. |

### Paquete `ui/` — La interfaz de usuario
| Archivo | Descripción |
|---|---|
| `VistaClinica.java` | Muestra menús y captura lo que escribe el usuario. |
| `ClinicaController.java` | Orquesta la aplicación. Llama a los servicios y captura las excepciones para mostrarlas. |
| `Dato.java` | Clase base para los formularios de entrada. |
| `DatoPaciente.java` | Formulario con los campos de un paciente. |
| `DatoOdontologo.java` | Formulario con los campos de un odontólogo. |
| `DatoTurno.java` | Formulario con los campos de un turno. |

### Paquete `io/` — La persistencia en archivos
| Archivo | Descripción |
|---|---|
| `PersistenciaPaciente.java` | Lee y escribe `pacientes.csv` con `BufferedReader`/`BufferedWriter`. |
| `PersistenciaOdontologo.java` | Lee y escribe `odontologos.csv`. Detecta el tipo (Ortodoncista/Endodoncista). |
| `PersistenciaTurno.java` | Lee y escribe `turnos.csv`. Al cargar, reconstruye las referencias a Paciente y Odontólogo. |

### Paquete `main/` — El punto de entrada
| Archivo | Descripción |
|---|---|
| `Main.java` | Crea los repositorios, carga los CSVs, crea los servicios, inicia el sistema y garantiza el guardado al cerrar. |

---

## Conceptos teóricos aplicados

| Concepto | Dónde se aplicó |
|---|---|
| **Excepciones checked** | `ClinicaException extends Exception` → el compilador obliga a manejarlas |
| **Jerarquía de excepciones** | 4 excepciones específicas heredan de `ClinicaException` |
| **try-catch-finally** | En el `ClinicaController` (catch) y en `Main` (finally para garantizar el guardado) |
| **Stream API** | `.filter()`, `.sorted()`, `.findFirst()`, `.anyMatch()`, `.collect()` en repositorios y servicios |
| **Comparable** | `Paciente implements Comparable<Paciente>` para ordenar por apellido |
| **Comparator** | `Comparator.comparing(Paciente::getApellido)` como alternativa en servicios |
| **BufferedReader/Writer** | En las 3 clases del paquete `io/` para lectura y escritura de archivos CSV |
| **Inyección de dependencias** | `Main` crea los repositorios, los pasa a los servicios, y los servicios al controller |
| **Diagrama de secuencia UML** | Diagrama 1 (alta de turno) y Diagrama 2 (búsqueda por DNI) |
| **Patrón Repository** | Separación entre la lógica de negocio y el almacenamiento de datos |
| **Principio SRP** | Cada clase tiene una sola responsabilidad (modelo, repositorio, servicio, IO, UI) |

---

## Flujo completo del sistema al ejecutar

```
1. Se ejecuta Main.java
2. Se crean 3 repositorios vacíos (Paciente, Odontólogo, Turno)
3. Se leen los archivos CSV y se llenan los repositorios con los datos guardados
4. Se crean los 3 servicios, recibiendo los repositorios ya cargados
5. Se crea la vista (VistaClinica) y el controller (ClinicaController)
6. El controller entra en el ciclo principal: muestra menú → recibe opción → ejecuta acción
7. Cada acción del usuario llama a un servicio → el servicio valida y ejecuta → el resultado vuelve al controller → se muestra al usuario
8. Si algo falla, el servicio lanza una excepción → el controller la captura → muestra el error → el programa sigue funcionando
9. Cuando el usuario elige "Salir" (opción 0), el controller termina su ciclo
10. El bloque `finally` de Main guarda todos los datos en los archivos CSV
11. El programa cierra
```

---

## Notas de implementación importantes

- **Separador CSV**: se usa `;` en lugar de `,` para evitar problemas con nombres o direcciones que contengan comas.
- **Orden de carga**: los turnos se cargan **después** de pacientes y odontólogos, porque los turnos necesitan referenciar a esas entidades por su ID.
- **Merge conflict resuelto**: la Entrega 2 tenía un conflicto de fusión de Git en `PacienteServiceImpl.java` (líneas con `<<<<<<< HEAD`) que impedía la compilación. Se resolvió al iniciar la Entrega 3.
- **Repositorios conectados a la interfaz**: en la Entrega 2, `RepositorioPaciente`, `RepositorioOdontologo` y `RepositorioTurno` tenían los métodos de `iRepository<T>` pero no declaraban `implements iRepository<T>`. Se corrigió en esta entrega.
- **Paquetes renombrados**: la Entrega 2 usaba `Entity/`, `Controller/`, `View/`. La consigna de la Entrega 3 requería `model/`, `ui/`. El renombre fue automático con herramientas de sistema.
