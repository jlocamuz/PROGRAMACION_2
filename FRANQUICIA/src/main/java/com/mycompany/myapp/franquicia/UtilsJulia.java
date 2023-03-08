package com.mycompany.myapp.franquicia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UtilsJulia {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public String sendHttpPostRequest(String urlString, String requestBodyString, String bearertokenString) {
        String url = urlString;
        String requestBody = requestBodyString;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", bearertokenString);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        String responseBody = response.getBody();
        System.out.println(responseBody);
        return responseBody;


    }
    public String sendHttpPostRequest() {
        String urlString = "http://10.101.102.1:8080/api/accion";
        String requestBodyString = "{\"accion\": \"consulta\",\"franquiciaID\": \"56b7688c-57c3-4f6f-95eb-39e568aa40e9\"}";
        String bearertokenString = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9jYW11eiIsImF1dGgiOiIiLCJleHAiOjE5OTIyNjIzMjV9.AP2Ckng1LLAkbtDp3af8NaP9FAE0zSvm5Dd18hgNmuKEwRkFLKgHVfz_M-xFsLYZGtIbAByPhE0AbzzmTeoXqw";
        return sendHttpPostRequest(urlString, requestBodyString, bearertokenString);
    }

}
