package com.mycompany.myapp.franquicia;

import org.springframework.http.HttpHeaders;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyScheduledTasks {
    
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UtilsJulia utilsJulia;
    
    String urlStringAccionString = "http://10.101.102.1:8080/api/accion";
    String requestBodyAccionString = "{\"accion\": \"consulta\",\"franquiciaID\": \"56b7688c-57c3-4f6f-95eb-39e568aa40e9\"}";
    String bearertokenString = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9jYW11eiIsImF1dGgiOiIiLCJleHAiOjE5OTIyNjIzMjV9.AP2Ckng1LLAkbtDp3af8NaP9FAE0zSvm5Dd18hgNmuKEwRkFLKgHVfz_M-xFsLYZGtIbAByPhE0AbzzmTeoXqw";
    
    @Scheduled(cron = "0 * * * * ?")
    public void run() throws Exception {
        System.out.println("POST from MyScheduledTasks");
        utilsJulia.sendHttpPostRequest(urlStringAccionString, requestBodyAccionString, bearertokenString); 
    
    
    }
    private void processResponse(String responseBody) {
        try (Scanner scanner = new Scanner(responseBody)) {
            scanner.useDelimiter(Pattern.compile("[\\r\\n]+"));
            String accion = null;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("\"accion\"")) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        accion = parts[1].replace("}", "").trim().replaceAll("[\",]+", "");
                    }
                    break;
                }
            }
            System.out.println("accion: " + accion);
            if (accion == null) {
                // unknown action
            } else if (accion.equals("nada")) {
                System.out.println("nothing is happening");
            } else if (accion.equals("reporte")) {
                // generate report
            } else if (accion.equals("menu")) {
                // display menu
            }
        }
    }
}