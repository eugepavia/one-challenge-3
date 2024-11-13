package com.eugepavia.challenge3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionDatos {
    private ObjectMapper objectMapper = new ObjectMapper();
    public <T> T convierteDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir datos JSON a Java");
        }
    }


}
