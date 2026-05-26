# Perfil de estilo del profesor

> Análisis basado en: Clas1-Introduccion, Clase2-Fundamentos, Clase3-UML, Clase4-Biblioteca, Clase6-PatronRepository, Clase9-TrigSoft, y el documento Proyecto_Clinica_Odontologica_POO_Refactorizado.md

---

## 1. Convenciones de nomenclatura

| Elemento | Convención | Ejemplos concretos |
|---|---|---|
| Clases | PascalCase en español | `EmpleadoMonotributo`, `ServicioCliente`, `VistaEmpleado`, `EmpleadoController` |
| Métodos | camelCase en español | `calcularSalario()`, `buscarPorId()`, `mostrarDatosPaciente()`, `obtenerNombreCompleto()` |
| Variables | camelCase en español | `salarioBase`, `cuotaMensual`, `excedeLimitePermitido`, `esAlumnoRegular` |
| **Interfaces** | **"i" minúscula como prefijo** | `iRepository<T>`, `iService<T>`, `iServicio<T>` — patrón consistente en todas las clases avanzadas |
| Enums | PascalCase, valores UPPER_SNAKE o PascalCase | `EstadoPedido { PENDIENTE, CONFIRMADO, EN_PREPARACION }`, `Genero { Masculino, femenino, X }` |
| Paquetes | Minúsculas (evolución visible) | Clase1: `Presencial` → Clase6: `Entity/Repository/Service/view` → Clase9: `modelo/servicio` |

**Observación clave:** el prefijo `i` minúscula en interfaces es una firma del profesor, no una convención Java estándar. Debe respetarse.

---

## 2. Estructura de paquetes

El profesor evoluciona hacia arquitectura en capas. Los proyectos más recientes usan minúsculas:

```
Clase 6 (Empleado):           Clase 9 (TrigSoft):
src/                          src/
  Entity/                       modelo/
  Repository/                   servicio/
  Service/                      TrigSoft.java (main)
  Controller/
  view/
  Main.java
```

El documento del proyecto formaliza la convención final para la clínica:

```
modelo / repositorio / servicio / excepcion / presentacion
```

La consigna de Entrega 3 especifica los paquetes como:
`model`, `service`, `repository`, `exception`, `ui`, `io`, `main`

---

## 3. Patrones de código recurrentes

### a) Constructor vacío + constructor parametrizado (patrón omnipresente)

```java
// EmpleadoMonotributo.java
public EmpleadoMonotributo() { }

public EmpleadoMonotributo(int id, String nombre, String apellido, String email,
                           double salarioBase, String categoria, double cuotaMensual) {
    super(id, nombre, apellido, email, salarioBase);
    this.categoria = categoria;
    this.cuotaMensual = cuotaMensual;
}
```

### b) Atributos `protected` en abstractas, `private` en concretas

```java
// Empleado.java (abstracta)
protected int id;
protected String nombre;
protected double salarioBase;

// EmpleadoMonotributo.java (concreta)
private String categoria;
private double cuotaMensual;
```

### c) Validaciones en el servicio, nunca en el modelo

```java
// EmpleadoServiceImpl.java
public Empleado registrar(Empleado empleado) {
    if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
        System.out.println("Error: El nombre es obligatorio");
        return null;
    }
    if (empleado.getSalarioBase() <= 0) {
        System.out.println("Error: El salario debe ser mayor a 0");
        return null;
    }
}
```

### d) Controller implementa Runnable con loop while + switch

```java
public class EmpleadoController implements Runnable {
    private boolean ejecutando = true;

    @Override
    public void run() {
        while (ejecutando) {
            int opcion = vista.mostrarMenuInicial();
            switch (opcion) {
                case 1: registrarEmpleado(); break;
                case 6: salir(); break;
                default: vista.mostrarMensaje("Opcion invalida");
            }
        }
    }
}
```

### e) Main mínimo — solo instancia y delega

```java
public class Main {
    public static void main(String[] args) {
        VistaEmpleado vista = new VistaEmpleado();
        EmpleadoController controller = new EmpleadoController(vista);
        controller.run();
    }
}
```

### f) Clases internas estáticas para conceptos dependientes

```java
// Cliente.java (Clase 9)
public static class Direccion {
    private String calle;
    private int altura;
    private String pisoDpto;
}

// Pedido.java (Clase 9)
public static class LineaPedido { ... }
public interface EstadoChangeListener { ... }
```

### g) El repositorio más reciente usa Streams (Clase 9), el anterior usa loops (Clase 6)

```java
// Clase 9 — con streams (más moderno)
return clientes.stream().filter(c -> c.getId()==id).findFirst().orElse(null);
clientes.removeIf(c -> c.getId()==id);

// Clase 6 — loops tradicionales
for (Empleado emp : empleados) {
    if (emp.getId() == id) return emp;
}
```

---

## 4. Estilo de comentarios y documentación

- **Sin Javadoc** en ningún archivo (cero `/** */` en todos los materiales).
- Comentarios pedagógicos con separadores visuales en clases tempranas:
  ```java
  // ==================== COMPOSICIÓN ====================
  private FechaNacimiento fechaNacimiento;
  ```
- Comentarios inline cortos y en español:
  ```java
  this.edad = edad; //16
  //clase interna estatica para cada linea del pedido
  ```
- En proyectos avanzados (Clase 6, 9): **muy pocos o ningún comentario** — el código habla solo.
- **Conclusión:** para las entregas, comentarios mínimos, solo donde el propósito no es obvio. Sin Javadoc.

---

## 5. Preferencias de colecciones

| Colección | Uso observado |
|---|---|
| `ArrayList<T>` / `List<T>` | Preferida para listas (Clase 4, 6, 9) |
| `HashMap<K,V>` | Mencionado en el documento del proyecto, no aparece en código de clase |
| Arrays (`T[]`) | Solo en ejemplos pedagógicos básicos (Clase 3) |
| Stream API | Aparece en Clase 9: `.stream().filter().findFirst().orElse()`, `.removeIf()` |

En los servicios de Clase 9, la colección interna siempre es `List`:
```java
private List<Cliente> clientes = new ArrayList<>();
```

---

## 6. Manejo de errores y excepciones

**En código de clase (Clases 1–9):** no hay try-catch en ningún archivo. Las validaciones fallan con `System.out.println("Error: ...")` y retornan `null`.

```java
// EmpleadoServiceImpl.java — estilo actual del profesor
Empleado existente = empleadoRepository.buscarPorId(empleado.getId());
if (existente != null) {
    System.out.println("Error: Ya existe un empleado con ID: " + empleado.getId());
    return null;
}
```

**En el documento del proyecto (Entrega 3):** se requiere jerarquía de excepciones personalizadas. Es un salto explícito de complejidad pedido por la consigna.

---

## 7. Nivel de verbosidad

- **Moderado-alto en entidades:** getters y setters para todos los atributos, `toString()` en casi todas las clases.
- **Bajo en lógica:** métodos cortos, sin lógica compleja inline.
- `toString()` con formato consistente:
  ```java
  @Override
  public String toString() {
      return "Pedido{" +
              "id=" + id +
              ", cliente=" + cliente +
              ", estado=" + estado +
              '}';
  }
  ```

---

## Resumen ejecutivo

| Característica | Decisión del profesor |
|---|---|
| Idioma del código | **Español** (nombres de clases, métodos, variables) |
| Interfaces | Prefijo `i` minúscula (`iServicio`, `iRepository`) |
| Paquetes | Minúsculas, en español |
| Validaciones | En la capa de servicio, con `if + return null` (sin excepciones hasta E3) |
| Comentarios | Mínimos, inline, en español. Cero Javadoc |
| Colecciones | `List<T>` (ArrayList) como base; streams en proyectos avanzados |
| Excepciones | No usadas en ejemplos previos; requeridas en Entrega 3 |
| Main | Mínimo: instancia controller y llama `run()` |
| Controller | Implementa `Runnable`, loop while + switch |
