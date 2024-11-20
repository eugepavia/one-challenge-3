package com.eugepavia.challenge3.principal;

import com.eugepavia.challenge3.dto.ResultadosDTO;
import com.eugepavia.challenge3.model.Autor;
import com.eugepavia.challenge3.model.Idioma;
import com.eugepavia.challenge3.model.Libro;
import com.eugepavia.challenge3.repository.AutorRepository;
import com.eugepavia.challenge3.repository.LibroRepository;
import com.eugepavia.challenge3.service.ConsumoAPI;
import com.eugepavia.challenge3.service.ConversionDatos;

import java.util.*;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    ConsumoAPI api = new ConsumoAPI();
    ConversionDatos conversor = new ConversionDatos();
    LibroRepository repositorioLibro;
    AutorRepository repositorioAutor;
    private String url = "https://gutendex.com/books/?";

    private String menu = """
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
            *********************************************************""";

    // CONSTRUCTOR
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }


    // SELECTOR DE OPCIONES DEL MENU
    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            try {
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        registraBusqueda();
                        break;
                    case 2:
                        eliminaBusqueda();
                        break;
                    case 3:
                        buscaLibroDB();
                        break;
                    case 4:
                        muestraListaTitulos();
                        break;
                    case 5:
                        muestraLibrosPorIdioma();
                        break;
                    case 6:
                        buscaAutorDB();
                        break;
                    case 7:
                        muestraListaAutores();
                        break;
                    case 8:
                        muestraAutoresPorAnio();
                        break;
                    case 9:
                        muestraStats();
                        break;
                    case 10:
                        top10Libros();
                        break;
                    case 0:
                        System.out.println("Finalizando programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Elija una alternativa del menú.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Sólo se aceptan números.");
                opcion = -1;
                teclado.nextLine();
            }

        }
    }


    // MÉTODOS

    // OPCIÓN 1
    private void registraBusqueda() {
        System.out.println("Nombre del libro:");
        var titulo = teclado.nextLine();
        String json = api.obtenerDatos(url+"search="+titulo.toLowerCase().replace(" ","+"));

        ResultadosDTO datos = conversor.convierteDatos(json, ResultadosDTO.class);

        Optional<Libro> libro = datos.resultados().stream()
                .map(l -> new Libro(l))
                .filter(l -> l.getTitulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();

        if (libro.isPresent()) {
            registraLibro(libro.get());
        } else {
            System.out.println("No se hallaron coincidencias.");
        }
    }

    // OPCIÓN 2
    private void eliminaBusqueda() {
        muestraListaTitulos();
        System.out.println("Nombre del libro a eliminar:");
        var titulo = teclado.nextLine();
        Optional<Libro> libro = repositorioLibro.findByTituloContainsIgnoreCase(titulo);
        if (libro.isPresent()) {
            Autor autor = libro.get().getAutor();
            autor.removeLibro(libro.get());
            if (autor.getLibros().isEmpty()) {
                repositorioAutor.delete(autor);
            } else {
                repositorioAutor.save(autor);
            }
            System.out.println("Libro eliminado del registro.");
        } else {
            System.out.println("El libro no se encuentra registrado.");
        }

    }

    // OPCIÓN 3
    private void buscaLibroDB() {
        System.out.println("Nombre del libro:");
        var titulo = teclado.nextLine();
        List<Libro> resultado = repositorioLibro.buscaLibroDB(titulo);
        if (resultado.isEmpty()) {
            System.out.println("No se hallaron coincidencias.");
        } else {
            stringLibros(resultado);
        }
    }

    // OPCIÓN 4
    private void muestraListaTitulos() {
        List<Libro> lista = repositorioLibro.findAll();
        stringLibros(lista);
    }

    // OPCIÓN 5
    private void muestraLibrosPorIdioma() {
        String texto = """
                IDIOMAS DISPONIBLES
                   de - Alemán
                   zh - Chino
                   es - Español
                   fr - Francés
                   en - Inglés
                   it - Italiano
                   pt - Portugués
                """;
        System.out.println(texto);
        System.out.println("Ingrese la clave del idioma del cual desea buscar libros:");
        var clave = teclado.nextLine();
        Idioma idioma = Idioma.leeClave(clave);
        if (idioma == Idioma.DESCONOCIDO) {
            System.out.println("Clave no reconocida.");
        } else {
            List<Libro> lista = repositorioLibro.buscaLibroPorIdioma(idioma);
            if (lista.isEmpty()) {
                System.out.println("No hay libros registrados en ese idioma.");
            } else {
                stringLibros(lista);
            }
        }
    }

    // OPCIÓN 6
    private void buscaAutorDB() {
        System.out.println("Nombre del autor:");
        var nombre = teclado.nextLine();
        List<Autor> resultado = repositorioAutor.buscaAutorDB(nombre);
        if (resultado.isEmpty()) {
            System.out.println("No se hallaron coincidencias.");
        } else {
            stringAutores(resultado);
        }
    }

    // OPCIÓN 7
    private void muestraListaAutores() {
        List<Autor> lista = repositorioAutor.findAll();
        stringAutores(lista);
    }

    // OPCIÓN 8
    private void muestraAutoresPorAnio() {
        System.out.println("Ingrese el año a partir del cual desea buscar:");
        try {
            var anio = teclado.nextDouble();
            teclado.nextLine();
            List<Autor> lista = repositorioAutor.buscaAutorPorAnio(anio);
            if (lista.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                stringAutores(lista);
            }
        } catch (InputMismatchException e) {
            System.out.println("Año no válido. Sólo se aceptan números enteros.");
        }
    }

    // OPCIÓN 9
    public void muestraStats() {
        DoubleSummaryStatistics stats = repositorioLibro.findAll().stream()
                .mapToDouble(Libro::getDescargas)
                .summaryStatistics();

        Optional<Libro> maximo = repositorioLibro.findAll().stream()
                .filter(l -> l.getDescargas() == stats.getMax())
                .findFirst();

        Optional<Libro> minimo = repositorioLibro.findAll().stream()
                .filter(l -> l.getDescargas() == stats.getMin())
                .findFirst();

        String texto = """
                ESTADÍSTICAS DE LIBROS REGISTRADOS
                Cantidad de registros: %d
                Libro más popular: %s (%.0f descargas)
                Libro menos popular: %s (%.0f descargas)
                """.formatted(stats.getCount(),maximo.get().getTitulo(),stats.getMax(),minimo.get().getTitulo(),stats.getMin());

        System.out.println(texto);
    }

    // OPCIÓN 10
    public void top10Libros() {
        List<Libro> lista = repositorioLibro.top10Libros();
        String texto;
        if (lista.isEmpty()) {
            System.out.println("Aun no se registran libros.");
        } else {
            System.out.println("TOP 10 LIBROS");
            for (int i = 0; i < lista.size(); i++) {
                Libro elemento = lista.get(i);
                // texto = (i+1) + " - " + elemento.getTitulo() + " (" + elemento.getDescargas() + " descargas)";
                texto = """
                        %d - %s (%.0f descargas)""".formatted(i+1,elemento.getTitulo(),elemento.getDescargas());
                System.out.println(texto);
            }
        }
    }


    // MÉTODOS AUXILIARES

    private void registraLibro(Libro libro) {
        if (repositorioLibro.findByTituloContainsIgnoreCase(libro.getTitulo()).isPresent()) {
            System.out.println("Libro registrado anteriormente. No puede repetirse su registro.");
        } else {
            registraAutor(libro.getAutor());
            Autor autor = repositorioAutor.buscaAutorPorNombre(libro.getAutor().getNombre());
            autor.addLibro(libro);
            repositorioAutor.save(autor);

            System.out.println(libro);
            System.out.println("¡Libro registrado con éxito!");
        }
    }

    private void registraAutor(Autor autor) {
        if (repositorioAutor.findByNombreEquals(autor.getNombre()).isEmpty()) {
            repositorioAutor.save(autor);
        }
    }

    private void stringLibros(List<Libro> lista) {
        lista.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void stringAutores(List<Autor> lista) {
        String texto;
        int posicion = -1;
        int n = 0;

        // Ordena por letra, deja a autores desconocidos al final
        lista.stream().sorted(Comparator.comparing(Autor::getNombre));
        for (Autor autor : lista) {
            if (autor.getNombre().equalsIgnoreCase("Desconocido")) {
                posicion = n;
                break;
            }
            n ++;
        }
        if (posicion != -1) {
            Autor desconocido = lista.remove(posicion);
            lista.add(desconocido);
        }

        // Imprime a cada autor con todas sus obras
        for (Autor autor : lista) {
            List<String> obras = new ArrayList<>();
            for (Libro libro : autor.getLibros()) {
                obras.add(libro.getTitulo());
            }
            if (autor.getNombre().equalsIgnoreCase("Desconocido")) {
                texto = """
                ---------------------------------------
                Libros con autores desconocidos:
                %s
                ---------------------------------------""".formatted(obras.toString());
            } else {
                texto = """
                ---------------------------------------
                Autor: %s
                Año de nacimiento: %.0f
                Año de fallecimiento: %.0f
                Libros escritos: %s
                ---------------------------------------""".formatted(autor.getNombre(),autor.getFechaNacimiento(),autor.getFechaFallecimiento(),obras.toString());
            }
            System.out.println(texto);
        }

    }



}
