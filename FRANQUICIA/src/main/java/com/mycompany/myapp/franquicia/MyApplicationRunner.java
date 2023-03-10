package com.mycompany.myapp.franquicia;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.springframework.http.HttpEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.repository.MenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private SaveMenus saveMenus;
 
    @Autowired
    private CrearReporte crearReporte;

    @Autowired
    private UtilsJulia utilsJulia;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("FROM MyApplicationRunner.java");
        
        System.out.println(menuRepository.count());
        // podria crear una venta. 

        // creando menu

        crearReporte.crearReporteRecurrenteDePrueba();

        
    }
        }
    

