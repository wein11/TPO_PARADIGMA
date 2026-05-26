<!-- Slide number: 1 -->

![Icono Descripción generada automáticamente](Imagen3.jpg)
Clase III
Lic. Claudio Godio - FAIN
1

### Notes:

<!-- Slide number: 2 -->
UML
2
Lenguaje gráfico para visualizar, especificar, construir y documentar un sistema.
El más utilizado en la actualidad para sistemas de software.
Respaldado por el Object Management Group (OMG).
Describe un "plano" del sistema,  incluyendo aspectos conceptuales y concretos.
Cuenta con varios tipos de diagramas, para diferentes aspectos de los estados representados.
Lic. Claudio Godio - FAIN

<!-- Slide number: 3 -->
UML
3
Existen dos tipos de diagramas: diagramas estructurales (estáticos) y diagramas de comportamiento (dinámicos).
Algunos representan diferentes aspectos de las mismas interacciones.

![](Gráfico8.jpg)
Lic. Claudio Godio - FAIN

<!-- Slide number: 4 -->
UML – Diagrama de Secuencia
4
Es un diagrama de Interacción.

Muestran cómo los objetos interactúan entre sí y el orden en que se producen esas interacciones.
Las interacciones representadas son para un escenario en particular.
Los diagramas de secuencia de UML forman parte de un modelo UML y solo existen dentro de los proyectos de modelado UML.
Lic. Claudio Godio - FAIN

<!-- Slide number: 5 -->
UML – Diagrama de Clase
5
Es un diagrama Estructural

Diagrama UML más utilizado.
Bloque de construcción principal de cualquier solución orientada a objetos.
Muestra las clases en un sistema, atributos y operaciones de cada clase y la relación entre cada clase.
Las relaciones entre las clases se muestran por líneas con diferentes tipos de flechas.
Es fuente de generación de código.
Se usan para modelar un juego de conceptos o entidades.
Lic. Claudio Godio - FAIN

<!-- Slide number: 6 -->
6
# Partes del diagrama de clases
Atributos: describe las características de una clase de objetos.
Operaciones: define el comportamiento de una clase de objetos (métodos)
Estereotipos: ayuda a entender un tipo de objeto en el contexto de otras clases de objetos con roles similares dentro del diseño del sistema.
Asociación: es un término formal para un tipo de relación.
Herencia: permite organizar las definiciones de la clase para simplificar y facilitar su implementación.
Lic. Claudio Godio - FAIN

<!-- Slide number: 7 -->
Atributos
7
Son descripciones de características, se usan para modelar información asociada con una entidad

La multiplicidad es opcional e indica el número de atributos por instancia de la clase.
Nombre_atributo[multiplicidad]:Tipo = Valor_inicial
Lic. Claudio Godio - FAIN

<!-- Slide number: 8 -->
Operaciones
8
Son descripciones del comportamiento, se usan para modelar los servicios u operaciones asociados con una entidad, esto es, lo que una entidad puede hacer.

identificador[parámetro:tipo] : retorno : tipo
Lic. Claudio Godio - FAIN

<!-- Slide number: 9 -->
# Ejemplo
9
La siguiente figura muestra un vuelo de una aerolínea modelado como una clase UML.

![](Picture7.jpg)
Nombre

Atributos

Operaciones

Atributo: tipo de dato

Operación(parámetros:
Tipo de dato):valor de
retorno
Lic. Claudio Godio - FAIN

<!-- Slide number: 10 -->
# Modelando un atributo
10
Un atributo describe una pieza de información que un objeto tiene o conoce de sí mismo. Para poder usar esta información se debe asignar un nombre y especificar el tipo de dato.

El tipo de dato puede ser primitivo o tipo de dato abstracto (definido)

Cada atributo puede tener reglas que limiten los valores asignados a éste.  Se puede usar un valor de default para protegerlo.
Lic. Claudio Godio - FAIN

<!-- Slide number: 11 -->
# Visibilidad de un atributo
11
La definición de un atributo debe especificar que otros objetos los pueden ver, a esto se lo denomina visibilidad:

Public (+) permite el acceso a objetos de las otras clases.
Private (-) limita el acceso a la clase, solo operaciones de la clase tienen acceso.
Protected (#) permite el acceso a subclases.  En el caso de generalización (herencia), las subclases deben tener acceso a los atributos y operaciones de la superclase, sino no pueden heredar.
Package (~) permite el acceso a los otros objetos en el mismo paquete.
Lic. Claudio Godio - FAIN

<!-- Slide number: 12 -->
# Modelando una Operación
12
Los objetos tienen comportamientos, cosas que puedan hacer y que se les puedan dar a éstos.

Las operaciones requieren un nombre, argumentos y a veces un valor de retorno.

Las reglas de privacidad se aplican en la misma forma que para los atributos: Private, Public, Protected y Package.
Lic. Claudio Godio - FAIN

<!-- Slide number: 13 -->
Asociaciones
13
El propósito de la asociación puede expresarse en un nombre, verbo o frase que describa como los objetos de un tipo (clase) se relacionan con objetos de otro tipo (clase). Por ejemplo:

Una persona tiene un coche

Una persona maneja un coche

Multiplicidad: cuantos objetos van a participar en la relación
Lic. Claudio Godio - FAIN

<!-- Slide number: 14 -->
14
Asociaciones

![](Picture9.jpg)

Se  indica el rol y la multiplicidad.
Un micro está asociado con varios asientos.
Es bidireccional
Lic. Claudio Godio - FAIN

<!-- Slide number: 15 -->
15
# Dirección

La dirección en las flechas de la asociación determina en que dirección puede recorrerse una asociación en el momento de la ejecución.

Una asociación sin flechas significa que se puede ir de un objeto a otro y viceversa.

Por ejemplo, la siguiente el tipo de flecha en la asociación implica que desde el objeto Micro se puede recuperar (dirigir hacia) el objeto Asiento.
Lic. Claudio Godio - FAIN

<!-- Slide number: 16 -->
# Dirección (cont…)
16

![](Picture15.jpg)
Supongamos que los requerimientos para un sistema de venta de pasajes se necesiten saber desde un micro cuales asientos están libres para ofrecer disponibilidad.
Lic. Claudio Godio - FAIN

<!-- Slide number: 17 -->
17
# Clase de Asociación
Cuando se modela una asociación entre clases, a veces es necesario incluir otra clase que contiene información valiosa acerca de la relación.

Se representa como una clase normal solo que la línea que la une con la línea que conecta las asociaciones primarias es punteada.

La siguiente figura muestra una clase asociación para carreras de autos.
Lic. Claudio Godio - FAIN

<!-- Slide number: 18 -->
Clase de Asociación
18

![](Picture3.jpg)
La asociación entre la clase Circuito y Piloto es a través de la clase Posición.

Esto significa que debe haber una instancia en esta clase cuando alguna instancia de la clase Piloto se asocie con una instancia de la clase Circuito
Lic. Claudio Godio - FAIN

<!-- Slide number: 19 -->
Asociación Reflexiva
19
Una clase puede asociarse con sí misma. Una clase Empleado puede relacionarse con sí misma a través del rol gerente/dirige.
No significa que una instancia está relacionada consigo misma, sino que una instancia de la clase está relacionada con otra instancia de la misma clase.

![](Picture6.jpg)
Lic. Claudio Godio - FAIN

<!-- Slide number: 20 -->
20
Agregación y Composición
Cada agregación es un tipo de asociación.
Cada composición es una forma de agregación.

Asociación

Agregación

Composición
Lic. Claudio Godio - FAIN

<!-- Slide number: 21 -->
21
Agregación
Es un tipo especial de asociación utilizado para modelar una relación  “Todo como la suma de las partes”.
Por ejemplo, Coche es una entidad “todo” y Llanta es una “parte” del Coche.
Una asociación con una agregación indica que una clase es parte de otra clase.
En este tipo de asociación, la clase parte puede sobrevivir sin su clase todo.
En general las partes conforman una instancia del todo.

Lic. Claudio Godio - FAIN

<!-- Slide number: 22 -->
22
Agregación (cont…)
Para representar una relación de agregación, se dibuja una línea sólida de la clase todo a la clase parte, y con un diamante en el lado de la clase padre.
Una rueda puede existir sin automóvil

![](Picture6.jpg)

Lic. Claudio Godio - FAIN

<!-- Slide number: 23 -->
Composición
23
En este caso el ciclo de vida de una instancia de la clase parte está en función del ciclo de vida de una instancia de la clase todo.
A diferencia de la agregación básica, para representarla el diamante esta relleno.
Una instancia de la clase Empresa debe tener al menos una instancia en la clase Departamento.
En este tipo de relaciones, si una la instancia Empresa se elimina, automáticamente la instancia de la clase Departamento también se elimina.

Lic. Claudio Godio - FAIN

<!-- Slide number: 24 -->
24
Composición (cont…)
Otra característica importante es que las partes solo puede relacionarse con una instancia del todo

![](Picture4.jpg)
Lic. Claudio Godio - FAIN

<!-- Slide number: 25 -->
25

![Un dibujo de un animal con la boca abierta Descripción generada automáticamente con confianza baja](Imagen6.jpg)
Lic. Claudio Godio - FAIN
¿Preguntas?