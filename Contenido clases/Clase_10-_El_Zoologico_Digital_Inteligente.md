**3“El Zoológico Digital Inteligente”**

![](data:image/png;base64...)Un zoológico moderno quiere digitalizar la gestión de sus animales y cuidadores. La directora les ha encargado desarrollar un prototipo en Java que cumpla con altos estándares de diseño orientado a objetos, ya que el sistema crecerá con nuevas especies en el futuro. Para ello, deben aplicar herencia, polimorfismo, interfaces y los principios SOLID/GRASP. El prototipo debe ser fácil de extender (Open/Closed), fácil de mantener (Single Responsibility) y debe evitar acoplamientos rígidos (Dependency Inversion).

**Requerimientos funcionales**

1. Existen tres grandes grupos de animales: **Mamíferos**, **Aves** y **Reptiles**.
   * Todos comparten atributos: nombre, edad, nivelHambre (int del 0 al 100).
   * Cada uno emite un sonido característico (emitirSonido()).
   * Algunos animales pueden **volar** (interfaz Volador) y otros **nadar** (interfaz Nadador).
2. Existe la clase Cuidador, con nombre y especialidad.
3. Los cuidadores deben poder **alimentar** a cualquier animal (método alimentar(Animal animal)), lo que reduce su nivel de hambre en 30 puntos.
4. Los cuidadores deben poder  **revisar la salud**  de cualquier animal, mostrando su información y sonido.
5. El Zoo debe poder listar todos los animales que alberga y ejecutar acciones polimórficas (ej. hacer que todos los que puedan volar ejecuten volar () ).
6. Se debe generar un **reporte** sencillo con los datos de cada animal (responsabilidad del propio animal, no de una clase externa).

**Requerimientos de diseño (lo importante para la revisión)**

* Utilizar **clase abstracta**Animal para compartir estructura y forzar emitirSonido().
* Crear **interfaces** Volador y Nadador con métodos volar() y nadar().
* Aplicar **polimorfismo** en una colección de Animal en el Zoo.
* Cumplir **SOLID**:
  + **S**: cada clase tiene una única responsabilidad (Animal maneja sus datos, Cuidador las acciones de cuidado, Zoo gestiona la colección).
  + **O**: si mañana se agrega Anfibio, el código de Zoo y Cuidador no necesita modificarse (usan Animal e interfaces).
  + **L**: cualquier subclase de Animal debe poder usarse donde se espera un Animal sin alterar el funcionamiento (Liskov).
  + **I**: las interfaces son pequeñas (Volador, Nadador), ningún animal está obligado a implementar métodos que no necesita.
  + **D**: los métodos de alto nivel (alimentar, revisarSalud) dependen de la abstracción Animal, no de clases concretas.
* Aplicar **GRASP**:
  + **Experto en información**: cada animal sabe cómo mostrar su ficha (toString() o método mostrarInfo()).
  + **Creador**: el Zoo es responsable de crear y almacenar instancias de animales (o un Factory simple).
  + **Controlador**: una clase ZooController (opcional) coordina la lógica entre interfaz y modelo.
  + **Bajo acoplamiento / Alta cohesión**: separar claramente dominio (modelo) de la clase principal con main.

**Segunda parte – Presentación del diagrama y código (30-50 min)**

Cada Estudiante expone en 5-7 minutos:

1. **Diagrama UML completo**  usar solo Draw IO
2. **Explicación de la aplicación de SOLID y GRASP**:
   * Mostrar fragmentos de código concretos que evidencien, por ejemplo:
     + *Single Responsibility*: Cuidador no conoce los detalles de cómo un animal se alimenta; solo le ordena reducirHambre().
     + *Open/Closed*: Si añadimos Anfibio, no cambiamos Zoo ni Cuidador.
     + *Liskov*: Un Aguila sustituye perfectamente a Animal en alimentar().
     + *Interface Segregation*: Pinguino solo implementa Nadador, no Volador.
     + *Dependency Inversion*: Zoo usa List<Animal> y las acciones del cuidador dependen de Animal, no de Leon o Delfin.
   * **GRASP**:
     + *Experto*: cada animal sabe su propia información (método mostrarInfo()).
     + *Creador*: Zoo crea y agrega animales (o una clase AnimalFactory).
     + *Controlador*: Main actúa como controlador simple de la aplicación.
3. **Demostración del código funcionando**

(pueden ejecutar el main en IntelliJ y mostrar la salida).
Sería excelente que narraran un caso: “El cuidador alimenta a Simba el león, Simba ruge y su hambre baja a 20…”, etc.

**Preguntas de reflexión**

* “¿Qué interfaz implementarías para un murciélago que vuela y amamanta? ¿Cómo respetarías ISP?”
* “Si quisiéramos agregar un tipo de animal que puede trepar, ¿qué principio nos ayuda a no modificar el código existente?”
* “Dibuja un fragmento UML del diseño resultante.”