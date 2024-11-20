# Catálogo de libros

![Created](https://img.shields.io/badge/Created-Nov_2024-248f24)
![Version](https://img.shields.io/badge/Version-1.0-c42121)
![Made with](https://img.shields.io/badge/Made_with-Java-255074)



## :memo: Descripción

Aplicación de consola para el registro y consulta de libros en una base de datos PostrgreSQL.

> Este trabajo fue elaborado como parte de la formación Java y Spring Framework (Challenge) de Oracle Next Education (ONE).

## :dart: Características

El programa cuenta con las siguientes opciones:

    ****************** CATÁLOGO LITERALURA ******************
    Elija una opción del menú:

    1 - Registrar libro por título
    2 - Eliminar libro por título
    
    3 - Buscar libro en el registro
    4 - Mostrar lista de libros registrados
    5 - Mostrar lista de libros por idioma
    
    6 - Buscar autor en el registro
    7 - Mostrar lista de autores registrados
    8 - Mostrar lista de autores vivos en un determinado año
    
    9 - Mostrar estadísticas de libros registrados
    10 - Top 10 libros registrados más populares
    
    0 - Salir
    *********************************************************

1. Consulta un libro específico en la API a partir de su título y lo registra en la base de datos.
   - No se permite el registro de libros duplicados.
2. Elimina un libro específico de la base de datos a partir de su título.
3. Consulta un libro específico en la base de datos. Devuelve todas las coincidencias.
4. Devuelve una lista con todos los libros guardados en la base de datos.
5. Filtra los libros de la base de datos por el idioma de escritura.
   - Requiere ingresar la clave del idioma deseado.
6. Consulta un autor específico en la base de datos. Devuelve todas las coincidencias.
7. Devuelve una lista con todos los autores guardados en la base de datos.
8. Filtra los autores de la base de datos por el año en que estuvieron vivos.
   - Si se ingresa 1980, devuelve una lista con todos los autores registrados que se encontraban vivos durante 1980.
9. Devuelve la cantidad total de libros registrados, así como el libro más y menos popular según la cantidad de descargas.
10. Devuelve una lista de los 10 libros con más descargas en la base de datos.

### Consideraciones
- La información de los libros se extrae de la API *Gutendex*: <https://gutendex.com/>
- Las dependencias utilizadas en el programa son:
  - Jackson Databind (ver 2.18.1)
  - Spring Data JPA
  - Postgres SQL Driver
- Los idiomas que soporta la aplicación en el registro de libros son alemán, chino, español, francés, inglés, italiano y portugués.
En caso de requerirse registrar más idiomas, se debe editar la clase `Enum Idioma` en el paquete `model`.

## :white_check_mark: Requisitos
- Base de datos PostgreSQL
- Edición del archivo `application.properties`, en los apartados siguientes según los datos de quien lo ejecute:
  - spring.datasource.url
  - spring.datasource.username
  - spring.datasource.password


## :wave: Sobre mí
¡Hola! Me llamo Eugenia. Soy una entusiasta del aprendizaje con gusto en la ciencia, matemáticas y tecnología.

Actualmente soy parte del programa Oracle Next Education, con el propósito de expandir mis conocimientos y desarrollarme profesionalmente en el mundo de la programación Back-End.