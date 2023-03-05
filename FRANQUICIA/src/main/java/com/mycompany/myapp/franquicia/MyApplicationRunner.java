package com.mycompany.myapp.franquicia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UtilsJulia utilsJulia;
    
    String urlStringAccionString = "http://10.101.102.1:8080/api/accion";
    String requestBodyAccionString = "{\"accion\": \"consulta\",\"franquiciaID\": \"56b7688c-57c3-4f6f-95eb-39e568aa40e9\"}";
    String bearertokenString = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9jYW11eiIsImF1dGgiOiIiLCJleHAiOjE5OTIyNjIzMjV9.AP2Ckng1LLAkbtDp3af8NaP9FAE0zSvm5Dd18hgNmuKEwRkFLKgHVfz_M-xFsLYZGtIbAByPhE0AbzzmTeoXqw";
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("POST from MyApplicationRunner");
        utilsJulia.sendHttpPostRequest(urlStringAccionString, requestBodyAccionString, bearertokenString); 
    
    
    }}