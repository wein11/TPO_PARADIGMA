# 🎯 Objetivo de la Entrega

En esta primera entrega se espera que cada equipo modele el dominio del problema y lo implemente en código Java. El objetivo es demostrar la capacidad de identificar las entidades principales del sistema, definir sus atributos y métodos, y representarlas tanto en un diagrama UML de clases como en código fuente compilable y ejecutable.

Esta entrega sienta las bases del proyecto: todo lo que construyan en las entregas siguientes partirá de este modelo. Por eso es fundamental que el diseño sea sólido, claro y bien pensado.

---

## 📚 Conceptos Teóricos a Aplicar

- **Clases y Objetos:** definición de clases como plantillas, instanciación de objetos.
- **Encapsulamiento:** atributos privados, acceso controlado mediante getters y setters.
- **Constructores:** inicialización correcta de objetos (constructor con parámetros y por defecto).
- **Relaciones entre clases:** asociación (Paciente tiene un Domicilio), composición/agregación según corresponda.
- **Diagrama UML de clases:** notación estándar, visibilidad, cardinalidad, relaciones. **FORMATO SVG**

---

## 📋 Entregables Específicos

### 1. Diagrama UML de Clases Completo

- Clases: `Paciente`, `Domicilio`, `Odontólogo`, `Turno` (mínimo).
- Para cada clase: atributos con tipo de dato y visibilidad, métodos principales.
- Relaciones entre clases con cardinalidad (`1..1`, `1..*`, etc.).
- Herramienta sugerida: StarUML, Lucidchart, draw.io o PlantUML.

### 2. Implementación de Clases de Dominio en Java

- **Clase Paciente:** `id`, `nombre`, `apellido`, `dni`, `fechaAlta`, `domicilio` (asociación).
- **Clase Domicilio:** `calle`, `número`, `localidad`, `provincia`.
- **Clase Odontólogo:** `id`, `nombre`, `apellido`, `matrícula`.
- **Clase Turno:** `id`, `paciente`, `odontólogo`, `fecha`, `hora`.

Requisitos de implementación:
- Todos los atributos deben ser `private` con getters y setters públicos.
- Al menos un constructor con parámetros y uno por defecto por clase.
- Método `toString()` en todas las clases para impresión legible.
- Métodos y atributos mínimos.

### 3. Main de Prueba

- Una clase `Main` que cree instancias de cada clase.
- Demostrar la relación `Paciente ↔ Domicilio`.
- Imprimir por consola los objetos creados.
- El código debe compilar sin errores y ejecutarse correctamente.

### 4. Informe Breve (1-2 páginas)

- Descripción general del modelo de dominio.
- Justificación de las decisiones de diseño.
- Explicación de las relaciones entre clases.
- Exportar el diagrama en SVG.

> **TODO EN UN MISMO ARCHIVO ZIP**

---

## ⚙️ Funcionalidades Requeridas en esta Etapa

- Crear objetos de las clases `Paciente`, `Domicilio`, `Odontólogo` y `Turno`.
- Asociar un `Domicilio` a un `Paciente`.
- Asociar un `Paciente` y un `Odontólogo` a un `Turno`.
- Imprimir información de los objetos por consola (`toString`).

---

## ✅ Criterios de Evaluación Específicos

| Criterio | Peso |
|---|---|
| **Funcionalidad:** las clases se instancian, las relaciones funcionan, el main ejecuta sin errores | 40% |
| **Diseño OO:** correcto uso de encapsulamiento, relaciones bien definidas, UML coherente con el código | 30% |
| **Calidad de código:** nombres descriptivos, convenciones Java (camelCase), código organizado | 20% |
| **Documentación:** UML completo, informe breve entregado | 10% |

---

## 📂 Formato de Entrega

- Código fuente Java (`.java`) en una carpeta organizada por paquetes (ej: `model`, `main`).
- Diagrama UML en formato PDF o imagen (PNG/JPG).
- Informe en formato PDF (1-2 páginas).
- Extensión sugerida del informe: 1 a 2 páginas.
- Todo comprimido en un archivo `.zip` con nombre: `Entrega1_NombreEquipo.zip`.

---

## 💡 Recomendaciones

- Comiencen por el diagrama UML: pensar antes de codificar ahorra tiempo.
- Usen un IDE como IntelliJ IDEA o Eclipse para aprovechar el autocompletado y la detección de errores.
- Versionen su código con Git desde el inicio — les será útil en todas las entregas.
- Revisen que el código compile limpiamente antes de entregar (`javac` sin errores).
- No incluyan lógica de negocio compleja en esta entrega: solo el modelo de dominio.
- Consulten los apuntes de las Clases 1 a 4 para repasar los conceptos.
