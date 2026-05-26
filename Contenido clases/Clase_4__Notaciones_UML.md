Programación Orientada
a Objetos

Notacion UML

Programar sin modelado previo

● Dificultad  en  la  comunicación  entre

equipos.

● Ambigüedades  en  los  requisitos  del

sistema.

● Mayor  tiempo  de  desarrollo  y  costos

elevados.
● Aumento

de
implementación.

errores

en

la

La concepción del UML

● Creado  por  Grady  Booch,  James  Rumbaugh  e  Ivar
Jacobson, conocidos como "Los tres amigos".
● Cada  uno  desarrolló  su  propia  metodología  de
análisis y diseño orientado a objetos en los años 80
y 90.

● Sus  metodologías  destacaron  sobre  las  de  la

competencia.

● A  mediados  de  los  90,  unieron  sus  ideas  para

trabajar en conjunto.

¿Qué es UML?

El UML es una herramienta fundamental en el desarrollo
de sistemas, ya que permite a los profesionales plasmar
sus  diseños  de  manera  clara,  estandarizada  y  fácil  de
entender.  Su  importancia  radica  en  su  capacidad  para
representar  visualmente  ideas  complejas,  facilitando  la
comunicación entre equipos técnicos y stakeholders.

Introducción: ¿Por qué es importante?

El  desarrollo  de  software  no  es  solo  escribir
código;  es  un  proceso  estructurado  que  inicia
con  una  idea  y  debe  transformarse  en  un
sistema funcional. Sin una comunicación clara
entre
y
analistas,
desarrolladores,  es  fácil  que  los  requisitos  se
malinterpreten,  generando  problemas  en
la
implementación.

diseñadores

clientes,

Aquí  es  donde  surge  la  necesidad  de  un
lenguaje  estándar  que  permita  representar
sistemas de manera visual y estructurada:  UML
(Unified  Modeling  Language,  Lenguaje
Unificado de Modelado).

 Un puente entre la idea y la implementación

de

proyecto

cualquier

En
actores:
software,
  ✅  Clientes  y  usuarios  finales  →  Expresan  necesidades  y  expectativas.
 ✅ Analistas y diseñadores → Modelan el sistema para convertir esas necesidades
en
solución.
una
 ✅ Desarrolladores → Implementan el sistema basado en el modelo creado.

intervienen

diferentes

UML  actúa  como  un  puente  de  comunicación  entre  todos  estos  actores,
permitiendo representar gráficamente:

●
●

●

Cómo interactúan los usuarios con el sistema (Casos de Uso).
Cómo  se  organizan  los  datos  y  funciones  del  sistema  (Diagramas  de
Clases, Diagramas de Objetos).
Cómo  fluye  la  información  dentro  del  sistema  (Diagramas  de  Secuencia,
etc.).
de

Actividades,

Gracias a UML, podemos  detectar errores  en  la fase  de diseño,  antes  de  escribir
una sola línea de código, lo que ahorra tiempo y costos en el desarrollo.

Beneficios de aprender UML

📌 Lenguaje estándar en la industria → Utilizado en el mundo real
para desarrollar software de calidad.

📌 Claridad en el diseño → Representa de forma estructurada los
elementos del sistema antes de la implementación.

📌 Mejora la comunicación → Facilita el trabajo colaborativo entre
equipos técnicos y no técnicos.

📌  Reducción  de  errores  y  costos  →  Permite  validar  el  sistema
antes de su desarrollo, evitando problemas posteriores.

📌  Facilita  la  documentación  y  el  mantenimiento  →  Un  sistema
bien modelado es más fácil de actualizar y escalar.

Tipos de Diagramas UML

Diagrama de Clases

● Pensemos  en

las  cosas  que  nos  rodean.  Es
tengan
probable  que  muchas  de  esas  cosas
atributos (propiedades) y que realicen determinadas
acciones.  Podríamos  imaginar  cada  una  de  esas
acciones como un conjunto de tareas.
● Tambien  nos  encontramos  con  que
se

las  cosas
naturalmente
categorías
tales
(automóviles,  mobiliario,
categorías las llamaremos clases. Una clase es una
categoría  o  grupo  de  cosas  que  tienen  atributos  y
acciones  similares.  He  aquí  un  ejemplo:  cualquier
cosa  dentro  de  la  clase  Lavadoras  tiene  atributos
como son la marca, el modelo, el número de serie y
la  capacidad.  Entre  las  acciones…”Agregar  ropa,
detergente, lavar, centrifugar, etc”

en
lavadoras...).  A

albergan

Ejemplo

¿Qué objetivo tiene pensar en las clases,
así como sus atributos y acciones?

Para  interactuar  con  nuestro  complejo  mundo,  la
mayoría  del  software  moderno  simula  algún  aspecto
del  mundo.  Décadas  de  experiencia  sugieren  que  es
más  sencillo  desarrollar  aplicaciones  que  simulen
algún  aspecto  del  mundo  cuando  el  software
representa clases de cosas reales. Los diagramas de
clases  facilitan  las  representaciones  a  partir  de  las
cuales los desarrolladores podrán trabajar.
A  su  vez,  los  diagramas  de  clases  colaboran  en  lo
referente  al  análisis.  Permiten  al  analista  hablarle  a
los  clientes  en  su  propia  terminología,  lo  cual  hace
posible que los clientes indiquen importantes detalles
de los problemas que requieren ser resueltos.

Diagrama de Objetos

Un  objeto  es  una  instancia  de  clase  (una
entidad  que  tiene  valores  específicos  de  los
atributos  y  acciones).  Su
lavadora,  por
ejemplo,  podría  tener  la  marca  ElectroLux,  el
modelo  Luxor,  el  número  de  serie  GL57774  y
una capacidad de 7 Kg.

La  figura  1.2  le  muestra  la  forma  en  que  el
UML  representa  a  un  objeto.  Vea  que  el
símbolo es un rectángulo, como en una clase,
pero el nombre está subrayado. El nombre de
la  instancia  específica  se  encuentra  a  la
izquierda de los dos puntos (:), y el nombre de
la clase a la derecha.

¿Qué relaciones hay entre clases?

Diagrama de Caso de Uso

Un  caso  de  uso  es  una  descripción  de
las  acciones  de  un  sistema  desde  el
punto  de  vista  del  usuario.  Para  los
desarrolladores,  esta  herramienta  es
valiosa porque:

●

●

Funciona  como  técnica  iterativa
(aciertos y errores) para capturar
requerimientos del usuario.

Es  clave  para  diseñar  sistemas
usuarios
para
accesibles
generales  (no  solo  expertos  en
computación).

1

¿Cómo analizar?

Analizar un caso requiere de tomarse
un tiempo para comprender el
contexto, tomar nota de los detalles
más relevantes y considerar sus
soluciones.

Un mundo lleno de objetos

Hay  soluciones  que  pueden  amoldarse  más  a  ser  eﬁcientes
que  eﬁcaces.  En  este  análisis,  nos  vamos  a    acotar  a  lo  que
les iremos proponiendo mediante distintas consignas.

Dentro del análisis de los detalles es bueno lograr diferenciar
ciertos ítems:

1)

Cosas  que  realizan  acciones  (personas,  entidades,
máquinas, etc)
Características (sustantivo)

2)
3) Acciones (verbos)
4) Detalles o requerimientos especiales

2

Ejemplo: Veterinaria

Ejemplo: Veterinaria
Analizando el texto donde María detalla cómo sería el proceso de un cliente en
la veterinaria podemos resaltar las palabras que nos ayudarán luego para
sintetizar los requerimientos del sistema.

“Son las 11 a.m. y entra a la veterinaria por primera vez Juana con su mascota
Picha.  Las  registramos  a  ambas,  anoto  el  nombre  y  apellido  del  cliente  y
también qué mascota tiene. De la mascota voy a anotar la raza.

Juana había pedido previamente un turno con uno de los veterinarios. Juana
deja a la perra en la veterinaria para que sea atendida. El veterinario atiende
a  Picha,  le  realiza  un  diagnóstico  y  lo  guarda  en  el  historial  de  diagnósticos.
Más  tarde,  Juana  pasa  a  buscar  a  Picha.  Los  diagnósticos  son  guardados
según la fecha con una descripción y la mascota asociada. De los veterinario
— empleados— se conoce el nombre, apellido y matrícula.”

María  nos  aclara  que  la  forma  de  agendar  turnos
todavía no la tiene deﬁnida.

Le  comentamos  que  el  sistema  pronto  le  va  a
mostrar  el  diseño  para  ver  si  se  había  entendido
todo bien.

Para  empezar  con  la  actividad,  destinar  5
minutos  con  el  objetivo  de  analizar  el  texto  del
relato y trata de identiﬁcar los ítems con los colores
propuestos  anteriormente.  En  la  siguiente  sección
proponemos  una  posible
resolución  —no
avancemos todavía, no al spoiler—.

3

Primera solución

Segundo: las palabras
Son las 11 a.m. y entra a la veterinaria por primera vez Juana con su mascota

Picha. Las registramos a ambas, anoto el nombre y apellido del  cliente y

también que mascota tiene. De la mascota voy a anotar la raza. Juana había

pedido previamente un turno con uno de los veterinarios. Juana deja a la perra

en la veterinaria para que sea atendida. El veterinario atiende a Picha, le realiza

un diagnóstico y lo guarda en el historial de diagnósticos. Más tarde, Juana pasa

a retirar a Picha. Los diagnósticos son guardados según la fecha con una

descripción y la mascota asociada. De los veterinarios —empleados— se

conoce el nombre, apellido y matrícula. Maria nos aclara que la forma de

agendar turnos todavía no la tiene deﬁnida.

Analizando un caso

Extrayendo las palabras resaltadas obtenemos que:

Cliente tiene nombre y apellido, deja a la perra y pasa a retirar.

Veterinario realiza un diagnóstico y lo guarda en historial de diagnósticos, del

mismo se conoce nombre, apellido y matrícula.

Diagnósticos tiene fecha, descripción y mascota asociada.

Analizando un caso

¿Cómo analizar desde el paradigma de objetos?

Tomando  las  bases  de  este  paradigma
podemos  realizarnos  preguntas  que  nos
ayudan como guia:

● ¿Qué objetos participan?

● ¿Cuáles son sus atributos?

● ¿Qué responsabilidades tienen?

En base al punto anterior una buena solución sería:

¿Qué objetos participan?

1. Cliente

2. Veterinario

3. Diagnóstico

4. Historial de diagnósticos

  Veamos al veterinario

  ¿Cuáles son sus atributos?

  Veterinario:

● Nombre

● Apellido

● Matrícula

¿Qué responsabilidades tiene?

         Veterinario: Realiza diagnóstico de las mascota

Recordemos que su responsabilidad
es en este contexto

1

Atributos

Deﬁnamos formalmente Atributos de
un objeto

Atributos de un objeto

En  la  programación  orientada  a  objetos,  vamos  a  llamar  atributos  a  las  características
distintivas  de  un  determinado  objeto,  es  decir  que  aquellas  que  nos  permiten  darle
signiﬁcado.

Por lo general, son valores que permiten deﬁnir el estado del objeto, dicho de otra forma,
son  las  especiﬁcaciones  que  deﬁnen  las  propiedades  de  un  objeto,  y  por  ende,  su
estructura al momento de ser modelado.

Habitualmente están formados por un nombre y un valor, siendo este último asignado al
momento de crear un objeto.

Atributos de un objeto: Veterinario

Nos  encontramos  en  una    clínica  con
veterinarios  que  atienden  a  diferentes
mascotas.

El  objeto  veterinario  podría  tener
siguientes atributos:
● nombre
● apellido
● matrícula

los

2

Comportamientos

Ahora que ya sabemos las
propiedades del objeto, veamos sus
responsabilidades.

Comportamiento de un objeto

ellos,

dependiendo

Permiten  establecer  cómo  van  a
responder cuando interactuemos
con
el
contexto. Cada método especiﬁca
la  operación  o  comportamiento
que  a  su  vez  puede  acceder  a  la
estructura
interna  del  objeto
como así también interactuar con
otros objetos.

verbos

Por  lo  general,  los  encontramos
como
las
acciones  que  puede  realizar  el
objeto.

indicando

Comportamientos de un objeto: Veterinario
El objeto Veterinario podría tener los siguientes
métodos:
● recibirMascota
● curar
● hacerDiagnostico

4

Ejemplos

De ahora en más, vamos a diagramar o
modelizar nuestros objetos

Ejemplos de un objeto con sus atributos y
métodos.

Nuestro contexto es la veterinaria.

Ahora  vamos  a  modelar  a  nuestro  objeto
Veterinario mediante un diagrama.

Aquí podemos apreciar como modelamos
los objetos mediante el Lenguaje Uniﬁcado
de Modelado, por sus siglas en inglés UML

Ejemplos de un objeto con sus atributos y
métodos.

Cambiemos  de  contexto,  ahora  nos
encontramos  analizando  el  sistema
contable de la clínica veterinaria.

Vamos  a  modelar  nuestro  objeto
Veterinario para el nuevo contexto.

¿Ves que ahora hay otros atributos y
responsabilidades diferentes?.

Ejemplos de un objeto con sus atributos y
métodos.

Ambos  son  Veterinarios,  y  en  cada  caso  (o  contexto)  vas  a
modelar los atributos y responsabilidades que sean necesarios
para lograr la solución de ese problema en particular.

Siempre tenés que tener
en cuenta el contexto de
tu análisis.

Actividad - Caso Práctico

Caso: Un sistema de reservas de citas médicas.

● ¿Qué interacciones básicas realiza un

usuario?

● Identificar los actores principales.
● Dibujar un Diagrama de Casos de Uso

básico.

● Diagrama de uso
● Diagrama de clase y Objetos.

