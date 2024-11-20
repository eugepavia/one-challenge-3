package com.eugepavia.challenge3.model;

// Clase Enumerate para los distintos idiomas de los libros

public enum Idioma {
    INGLES("en","Inglés"),
    ESPANOL("es","Español"),
    PORTUGUES("pt", "Portugués"),
    FRANCES("fr", "Francés"),
    CHINO("zh", "Chino"),
    ALEMAN("de", "Alemán"),
    ITALIANO("it", "Italiano"),
    DESCONOCIDO("un","Desconocido");

    private String idiomaClave;
    private String idiomaEspanol;

    Idioma(String idiomaClave, String idiomaEspanol) {
        this.idiomaClave = idiomaClave;
        this.idiomaEspanol = idiomaEspanol;
    }

    // Recibe la clave del idioma. Devuelve su valor equivalente en el Enum Idioma
    public static Idioma leeClave(String clave) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaClave.equalsIgnoreCase(clave)) {
                return idioma;
            }
        }
        return Idioma.DESCONOCIDO;
    }

    // Recibe el valor Enum Idioma de un libro. Devuelve su equivalente en español
    public static String devuelveEspanol(Idioma idioma) {
        return idioma.idiomaEspanol;
    }


}


