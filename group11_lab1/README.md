---
title: Laboratorio de Funcional
authors: Lucas Mateo Acosta, Lucas Oscar Gallegos, Gastón Andrés Ferrero
---
La consigna del laboratorio está en https://tinyurl.com/funcional-2024-famaf

# 1. Tareas
Pueden usar esta checklist para indicar el avance.

## Verificación de que pueden hacer las cosas.
- [x] Haskell instalado y testeos provistos funcionando. (En Install.md están las instrucciones para instalar.)

## 1.1. Lenguaje
- [x] Módulo `Dibujo.hs` con el tipo `Dibujo` y combinadores. Puntos 1 a 3 de la consigna.
- [x] Definición de funciones (esquemas) para la manipulación de dibujos.
- [x] Módulo `Pred.hs`. Punto extra si definen predicados para transformaciones innecesarias (por ejemplo, espejar dos veces es la identidad).

## 1.2. Interpretación geométrica
- [x] Módulo `Interp.hs`.

## 1.3. Expresión artística (Utilizar el lenguaje)
- [x] El dibujo de `Dibujos/Feo.hs` se ve lindo.
- [x] Módulo `Dibujos/Grilla.hs`.
- [x] Módulo `Dibujos/Escher.hs`.
- [x] Listado de dibujos en `Main.hs`.

## 1.4 Tests
- [x] Tests para `Dibujo.hs`.
- [x] Tests para `Pred.hs`.

# 2. Experiencia

Una de los principales complicaciones fue comprender y adaptarnos a los conceptos propios del paradigma funcional tanto para resolver los ejercicios como en la forma de comunicar los problemas que surgian al escribir el código.
Resulta interesante y práctico explorar herramientas que nos permitan que el trabajo en grupo sea más eficiente como el Live Share de Visual Studio Code, el Meet de Google y Git.

# 3. Preguntas
Al responder tranformar cada pregunta en una subsección para que sea más fácil de leer.

1. ¿Por qué están separadas las funcionalidades en los módulos indicados? Explicar detalladamente la responsabilidad de cada módulo.

En un sentido general el programa contiene un lenguaje que permite renderizar imagen. Para poder llevar a cabo la tarea principal se pueden reconocer diversar tareas parciales. En cada módulo se describen distintos hechos que guardan una relación próxima entre sí. Separar estas funcionalidades en módulos es lo que nos permite trabajar en cada una de forma independiente. Así, logramos capas de abstracción con las cuales podemos visualizar un mapa general del funcionamiento del lenguaje y asi tener una lectura e interpretación mas ágil del programa.

Dibujo.hs Contiene la sintaxis del lenguaje, el tipo de dato junto a sus constructores, que son los operadores que luego usamos para crear estructuras de dibujos.

Interp.hs Aqui encontramos las funciones que describen la interpretación geométrica. Una vez que definimos una estructura de dibujo, ésta finalmente se renderiza en pantalla a partir de las funciones de este módulo, mediante la manipulación de coordenadas.

Main.hs Contiene el código que se encarga de dar inicio de ejecución del programa. Aquí es donde se buscan los dibujos disponibles y se ejecutan, siendo finalmente mostrados por pantalla.

FloatingPic.hs Se define la estructura de datos que se usa para tratar las coordenadas que ubican las figuras en pantalla.
 
 Directorio ./Dibujos/ Casos de uso del lenguaje

2. ¿Por qué las figuras básicas no están incluidas en la definición del lenguaje, y en vez de eso, es un parámetro del tipo?

Las figuras básicas no estan incluidas en la definición del lenguaje porque esto nos permite hacer uso del polimorfismo para crear dibujos de diferentes tipos. Así logramos que el tipo pueda interactuar con tipos de otros módulos y aquellos importados de librerias. Esto también facilita el hecho de que si ya tenemos una estructura de un dibujo implementada, podemos cambiar las figuras que lo conforman simplemente cambiando el tipo de dato y su interpretación en el plano. Por ejemplo el Escher, que lo hicimos con triángulos en vez de peces.

3. ¿Qué ventaja tiene utilizar una función de `fold` sobre hacer pattern-matching directo?

La ventaja que tiene utilizar una función de fold sobre hacer pattern-matching es que nos ahorramos definir una funcionalidad por casos para cada constructor de la estructura dibujo, por una sola función que va haciendo recursión en la estructura aplicando las funcionalidades a cada constructor.

4. ¿Cuál es la diferencia entre los predicados definidos en Pred.hs y los tests?

En Pred.hs se declaran una serie de funciones. Luego, en los test, se proponen diversos ejemplos de casos de uso para cada función y asi se comprueba que su funcionamiento sea el esperado.

# 4. Extras
Completar si hacen algo.