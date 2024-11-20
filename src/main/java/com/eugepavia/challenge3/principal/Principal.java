package com.eugepavia.challenge3.principal;

import com.eugepavia.challenge3.dto.ResultadosDTO;
import com.eugepavia.challenge3.model.Autor;
import com.eugepavia.challenge3.model.Libro;
import com.eugepavia.challenge3.repository.AutorRepository;
import com.eugepavia.challenge3.repository.LibroRepository;
import com.eugepavia.challenge3.service.ConsumoAPI;
import com.eugepavia.challenge3.service.ConversionDatos;
import jakarta.transaction.Transactional;

import java.util.*;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    ConsumoAPI api = new ConsumoAPI();
    ConversionDatos conversor = new ConversionDatos();
    LibroRepository repositorioLibro;
    AutorRepository repositorioAutor;
    private String url = "https://gutendex.com/books/?";
    private String menu = """
            ********** CATÁLOGO LITERALURA **********
            Elija una opción del menú:
            1 - Buscar y registrar libro por título
            2 - Mostrar lista de libros registrados
            3 - Mostrar lista de autores registrados
            4 - Mostrar lista de autores vivos en un determinado año
            5 - Listar libros por idioma (en, es, fr, pt)
            6 - Eliminar libro
            
            0 - Salir
            *****************************************""";

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
                        buscaTitulo();
                        break;
                    case 2:
                        muestraListaTitulos();
                        break;
                    case 3:
                        muestraListaAutores();
                        break;
                    case 4:
                        muestraAutoresPorAnio();
                        break;
                    case 6:
                        eliminaTitulo();
                        break;
                    case 0:
                        System.out.println("Finalizando programa");
                        break;
                    default:
                        System.out.println("Opción no válida. Elija una alternativa del menú");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Sólo se aceptan números");
                opcion = -1;
                teclado.nextLine();
            }

        }
    }


    // MÉTODOS

    // OPCIÓN 1
    private void buscaTitulo() {
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
            System.out.println("No se hallaron coincidencias");
        }
    }

    private void registraLibro(Libro libro) {
        if (repositorioLibro.findByTituloContainsIgnoreCase(libro.getTitulo()).isPresent()) {
            System.out.println("Libro registrado anteriormente. No puede repetirse su registro");
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

    // OPCIÓN 2
    private void muestraListaTitulos() {
        List<Libro> lista = repositorioLibro.findAll();
        lista.forEach(System.out::println);
    }

    // OPCIÓN 3
    private void muestraListaAutores() {
        List<Autor> lista = repositorioAutor.findAll();
        stringAutores(lista);
    }

    private void stringAutores(List<Autor> lista) {
        String texto;
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

    // OPCIÓN 4
    private void muestraAutoresPorAnio() {
        System.out.println("Ingrese el año a partir del cual desea buscar:");
        var anio = teclado.nextDouble();
        teclado.nextLine();
        List<Autor> lista = repositorioAutor.buscaAutorPorAnio(anio);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año");
        } else {
            stringAutores(lista);
        }
    }

    // OPCIÓN 6
    private void eliminaTitulo() {
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
            System.out.println("Libro eliminado del registro");
        } else {
            System.out.println("El libro no se encuentra registrado");
        }

    }





}
