---
title: Laboratorio de Programación Orientada a Objetos
author: Mateo, Gaston y Lucas
---

El enunciado del laboratorio se encuentra
en [este link](https://docs.google.com/document/d/1wLhuEOjhdLwgZ4rlW0AftgKD4QIPPx37Dzs--P1gIU4/edit#heading=h.xe9t6iq9fo58).

# 1. Tareas

Pueden usar esta checklist para indicar el avance.

## Verificación de que pueden hacer las cosas.

- [V] Java 17 instalado. Deben poder compilar con `make` y correr con `make run` para obtener el mensaje de ayuda del
  programa.

## 1.1. Interfaz de usuario

- [V] Estructurar opciones
- [V] Construir el objeto de clase `Config`

## 1.2. FeedParser

- [V] `class Article`
    - [V] Atributos
    - [V] Constructor
    - [V] Método `print`
    - [V] _Accessors_
- [V] `parseXML`

## 1.3. Entidades nombradas

- [V] Pensar estructura y validarla con el docente
- [V] Implementarla
- [V] Extracción
    - [V] Implementación de heurísticas
- [V] Clasificación
    - [V] Por tópicos
    - [V] Por categorías
- Estadísticas
    - [V] Por tópicos
    - [V] Por categorías
    - [V] Impresión de estadísticas

## 1.4 Limpieza de código

- [V] Pasar un formateador de código
- [V] Revisar TODOs

# 2. Experiencia

Encontramos dos momentos importantes dentro del lab. El primero es la inclusión y comprensión de conceptos nuevos como
entidades nombradas y heurísticas. Herramientas de interpretacion de problemas que exceden lo computacional. Y en
segundo lugar encontrar la relación y posterior puesta en práctica de ello con el paradigma de Programación Orientado a
Objeto.
Un momento interesante es vivenciar como las ideas que propone un paradigma orienta la resolución de los problemas de
manera distina y te predispone a poner atención en algunas caracteristicas particulares.

# 3. Preguntas

1. Explicar brevemente la estructura de datos elegida para las entidades nombradas.

La estructura de datos elegida para representar las entidades nombradas es una Clase que contiene los atributos
de nombre (Cadena de texto), categoria (Cadena de texto), topicos(Lista de String) y ocurrencia (tipo entero). Los
atributos son privados y solo se puede acceder y modificar sus valores a través de métodos de la clase. Para ello cada atributo cuenta con un método get y set.
Otra cosa a tener en cuenta es que en la consigna se pide que las entidades nombradas de diferentes categorías deben tener al menos una característica propia de la categoría, sin preocuparnos de cómo obtenerlas. Para esto es que hicimos las clases Person, Location, Organization y Other que heredan de entidad nombrada, representándolas con sus respectivos atributos (características).

2. Explicar brevemente cómo se implementaron las heurísticas de extracción.
   Las nuevas heurísticas de extracción implementadas son: una que extrae los acrónimos del texto (por ejemplo UCR), y
   otra que extrae las palabras que macheen con alguna de las keywords de las entidades nombradas dadas en
   dictionary.json.
   La heurística de acrónimos crea un patrón a partir de una expresión regular de la forma "[A-Z]+[A-Z]" para compararlo
   con las palabras del texto. En esta expresión regular, la notación "[A-Z]" indica que allí debe ir una letra
   mayúscula cualquiera de la A a la Z. El símbolo "+" indica que el caracter que le precede debe aparecer al menos una
   vez (osea tiene que aparecer al menos una vez alguna letra mayúscula de la A a la Z). Pero si dejamos esto solamente,
   nos daría también letras individuales mayúsculas, por lo que le agregamos que seguido de esto debe haber más letras
   mayúscula (por lo tanto machearía con expresiones de al menos 2 mayúsculas).
   La heurística que busca las coincidencias de las keywords del diccionario consta de una parte que carga las keywords
   del objeto Json y las pasa a formato de expresión regular. Y en segunda parte se pasa la expresión regular como
   argumento para armar el patrón de búsqueda y se comprueba la coincidencia.

# 4. Extras
