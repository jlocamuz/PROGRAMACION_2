package com.mycompany.myapp.franquicia;

import org.springframework.http.HttpHeaders;

import java.time.Instant;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.repository.MenuRepository;

@Service
public class MyScheduledTasks {

    @Autowired
    private SaveMenus saveMenus;
 
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsJulia utilsJulia;


    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CrearReporte crearReporte;
    
    String urlStringAccionString = "http://10.101.102.1:8080/api/accion";
    String requestBodyAccionString = "{\"accion\": \"consulta\",\"franquiciaID\": \"56b7688c-57c3-4f6f-95eb-39e568aa40e9\"}";
    String bearertokenString = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9jYW11eiIsImF1dGgiOiIiLCJleHAiOjE5OTIyNjIzMjV9.AP2Ckng1LLAkbtDp3af8NaP9FAE0zSvm5Dd18hgNmuKEwRkFLKgHVfz_M-xFsLYZGtIbAByPhE0AbzzmTeoXqw";
    
    @Scheduled(fixedRate = 15000)
    public void run() throws Exception {
        System.out.println("FROM MyScheduledTasks.java");
        String accionJson = utilsJulia.sendHttpPostRequest(); 
        processResponse(accionJson);
    
    }


    public void processResponse(String responseBody) throws Exception {
        if (menuRepository.count() == 0) {
            String urlString = "http://10.101.102.1:8080/api/cambiar-menu";
            String requestBodyString = "{\"accion\": \"activar-estado-menu\",\"franquiciaID\": \"56b7688c-57c3-4f6f-95eb-39e568aa40e9\"}";
            String bearertokenString = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9jYW11eiIsImF1dGgiOiIiLCJleHAiOjE5OTIyNjIzMjV9.AP2Ckng1LLAkbtDp3af8NaP9FAE0zSvm5Dd18hgNmuKEwRkFLKgHVfz_M-xFsLYZGtIbAByPhE0AbzzmTeoXqw";
            System.out.println(utilsJulia.sendHttpPostRequest(urlString, requestBodyString, bearertokenString));
    
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        String accion = rootNode.get("accion").asText();
        System.out.println("accion: " + accion);
        try {
            if (accion == null) {
                // unknown action
            } else if (accion.equals("nada")) {
            } else if (accion.equals("reporte")) {
                JsonNode accion1 = rootNode.get("accion");
                JsonNode franquiciaUUID = rootNode.get("franquiciaUUID");
    
                JsonNode tipo = rootNode.get("tipo");
                JsonNode fechaInicio = rootNode.get("fechaInicio");
                JsonNode fechaFin = rootNode.get("fechaFin");
                JsonNode intervalo = rootNode.get("intervalo");
                if (tipo.toString() == "recurrente") {
                    crearReporte.crearReporteRecurrente(accion1, franquiciaUUID, tipo, fechaInicio,fechaFin, intervalo);
                } else if(tipo.toString() == "historico") {
                    crearReporte.crearReporteHistorico(accion1, franquiciaUUID, tipo, fechaInicio,fechaFin);
    
                }
    
                // Access the reporte field and do something with it
    
            } else if (accion.equals("menu")) {
                saveMenus.saveMenus();
            }else if (accion.equals("estado-menu-cambiado")) {
                saveMenus.saveMenus();
            }
        } catch (NullPointerException e) {
            System.out.println("Null Pointer");
        }
    }
    
}