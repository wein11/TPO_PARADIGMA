# 🎯 Objetivo de la Entrega 2

Incorporar la capa de servicios y repositorios en memoria, aplicando patrones GRASP y principios SOLID. Agregar herencia y polimorfismo al modelo si se identifican oportunidades de generalización. El sistema debe tener una interfaz por consola funcional para realizar operaciones CRUD.

---

## 📋 Entregables

### Capa de Servicios
- `ServicioPaciente`, `ServicioOdontologo`, `ServicioTurno` con lógica de negocio y validaciones básicas.

### Capa de Repositorios en Memoria
- `RepositorioPaciente` → `HashMap<Long, Paciente>`
- `RepositorioOdontologo` → `HashMap<Long, Odontologo>`
- `RepositorioTurno` → `ArrayList<Turno>` o `HashMap`

### Interfaz Genérica
- `IRepositorio<T>` con métodos CRUD genéricos.

### Patrones GRASP aplicados
- **Controller:** los servicios orquestan las operaciones.
- **Expert:** la entidad que tiene la información responde por ella.
- **Creator:** quien tiene los datos crea el objeto.

### Principios SOLID aplicados
- **SRP** (Single Responsibility): cada clase tiene una única responsabilidad.
- **OCP** (Open/Closed): extensible vía interfaces sin modificar código existente.

### (Opcional) Herencia
- Jerarquía de `Odontólogo` con especialidades: `Ortodoncista`, `Endodoncista`.
- Jerarquía de `Turno`: `TurnoUrgente`, `TurnoControl`.

### (Opcional) Polimorfismo
- Comportamiento diferenciado según el tipo (ej: `calcularDuracion()` según especialidad).

### Interfaz por Consola
- Menú principal con submenús para Pacientes, Odontólogos y Turnos.
- Operaciones CRUD completas accesibles desde consola.

---

## ✅ Conceptos Evaluados

Herencia · Polimorfismo · Interfaces · Colecciones (`HashMap`, `ArrayList`) · Patrones GRASP · Principios SOLID · Arquitectura en capas.

---

## 📂 Formato de Entrega

- Código fuente del proyecto Java actualizado.
- Archivo PDF con diagrama UML de clases actualizado (incluyendo servicios, repositorios, interfaces).
- Informe (2–3 páginas):
  - Patrones GRASP aplicados (con ejemplos concretos).
  - Principios SOLID demostrados.
  - Decisiones de herencia/polimorfismo.
