package com.mycompany.myapp.web.rest;
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

}
