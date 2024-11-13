package com.eugepavia.challenge3.model;

public enum Idioma {
    INGLES("en","Inglés"),
    ESPANOL("es","Español"),
    PORTUGUES("pt", "Portugués"),
    FRANCES("fr", "Francés"),
    CHINO("zh", "Chino"),
    ALEMAN("de", "Alemán"),
    ITALIANO("it", "Italiano");

    private String idiomaClave;
    private String idiomaEspanol;

    Idioma(String idiomaClave, String idiomaEspanol) {
        this.idiomaClave = idiomaClave;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idioma leeClave(String clave) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaClave.equalsIgnoreCase(clave)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + clave);
    }

    public static String devuelveEspanol(Idioma idioma) {
        return idioma.idiomaEspanol;
    }


}


