package com.eugepavia.challenge3.principal;

import com.eugepavia.challenge3.dto.LibroDTO;
import com.eugepavia.challenge3.dto.ResultadosDTO;
import com.eugepavia.challenge3.model.Libro;
import com.eugepavia.challenge3.service.ConsumoAPI;
import com.eugepavia.challenge3.service.ConversionDatos;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    ConsumoAPI api = new ConsumoAPI();
    ConversionDatos conversor = new ConversionDatos();
    private String url = "https://gutendex.com/books/?";
    private String menu = """
            ********** CATÁLOGO LITERALURA **********
            Elija una opción del menú:
            1 - Buscar libro por título (o parte del título)
            2 - Listar libros registrados
            3 - Listar autores registrados
            4 - Listar autores vivos en un determinado año
            5 - Listar libros por idioma (en, es, fr, pt)
            
            0 - Salir
            *****************************************""";

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

    private void buscaTitulo() {
        System.out.println("Nombre del libro:");
        var titulo = teclado.nextLine();
        String json = api.obtenerDatos(url+"search="+titulo.toLowerCase().replace(" ","+"));

        ResultadosDTO datos = conversor.convierteDatos(json, ResultadosDTO.class);

        Optional<Libro> libro = datos.resultados().stream()
                .map(l -> new Libro(l))
                .filter(l -> l.getTitulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();
        libro.ifPresentOrElse(System.out::println,() -> System.out.println("No se hallaron coincidencias"));
    }





}
