package com.mycompany.myapp.franquicia;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;

@Service
public class CrearReporte {
    
    @Autowired
    private RestTemplate restTemplate;
    

    public void crearReporteHistorico(String accion, String franquiciaUUID, String tipo, String string, String string2) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBodyMap = new HashMap<>();        
        requestBodyMap.put("accion",accion);
        requestBodyMap.put("franquiciaUUID", franquiciaUUID);
        requestBodyMap.put("tipo", tipo);
        requestBodyMap.put("fechaInicio", string.toString());
        requestBodyMap.put("fechaFin", string2.toString());


        String requestBody = objectMapper.writeValueAsString(requestBodyMap);
        String urlString = "http://127.0.1.1:9090/api/reporte-historicos";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String username = "admin";
        String password = "admin";
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        
        headers.set("Authorization", "Basic " + encodedCredentials);
    
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlString, request, String.class);
        System.out.println(response);

    }
  

    public void crearReporteRecurrente(String accion1, String franquiciaUUID, String tipo, String string, String string2, String intervalo) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBodyMap = new HashMap<>();        
        requestBodyMap.put("accion",accion1);
        requestBodyMap.put("franquiciaUUID", franquiciaUUID);
        requestBodyMap.put("tipo", tipo);
        requestBodyMap.put("fechaInicio", string.toString());
        requestBodyMap.put("fechaFin", string2.toString());
        requestBodyMap.put("intervalo", intervalo);
        System.out.println();

        String requestBody = objectMapper.writeValueAsString(requestBodyMap);
        String urlString = "http://127.0.1.1:9090/api/reporte-recurrentes";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String username = "admin";
        String password = "admin";
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        
        headers.set("Authorization", "Basic " + encodedCredentials);
    
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlString, request, String.class);
        System.out.println(response);

    }


    public void crearReporteRecurrenteDePrueba() throws JsonProcessingException {
        
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        
        Instant instant = zonedDateTime.toInstant();
        System.out.println("NOWWWWWWWWWWWW" + instant);
        Instant finInstant = instant.plus(1, ChronoUnit.HOURS);
        crearReporteRecurrente("accion", "56b7688c-57c3-4f6f-95eb-39e568aa40e9", "recurrente", instant.toString(),finInstant.toString(),  "PT30S");
        crearReporteHistorico("accion", "56b7688c-57c3-4f6f-95eb-39e568aa40e9", "historico", instant.toString(),finInstant.toString());
    }


    public void crearReporteRecurrente(JsonNode accion1, JsonNode franquiciaUUID, JsonNode tipo, JsonNode fechaInicio,
            JsonNode fechaFin, JsonNode intervalo) {
    }


    public void crearReporteHistorico(JsonNode accion1, JsonNode franquiciaUUID, JsonNode tipo, JsonNode fechaInicio,
            JsonNode fechaFin) {
    }
}
