package com.mycompany.myapp.web.rest;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.myapp.domain.ReporteRecurrente;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.VentaRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.TaskScheduler;

@Component
public class PostingReportesRecurrentes {
    @Autowired
    private VentaRepository ventaRepository;
    private ReporteRecurrente reporteRecurrente;

    @Autowired
    private UtilsJulia utilsJulia;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    private Instant startTime;
    private Instant finishTime;
    private Duration intervalDuration;

    public PostingReportesRecurrentes() {
        this.startTime = Instant.now();
    }

public void start(ReporteRecurrente reporteRecurrente) {
    System.out.println("Number of ventas = " + ventaRepository.count());
    Instant now = Instant.now();
    System.out.println("START   " + now);
    this.reporteRecurrente = reporteRecurrente;
    this.startTime = reporteRecurrente.getFechaInicio();
    this.finishTime = reporteRecurrente.getFechaFin();
    this.intervalDuration = Duration.parse(reporteRecurrente.getIntervalo());
    long initialDelay = Duration.between(Instant.now(), startTime).toMillis();
    long intervalMillis = intervalDuration.toMillis();
    this.taskScheduler.scheduleAtFixedRate(() -> {
        try {
            executeAction();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }, initialDelay);
    System.out.println("START TIME  " + startTime);

    // Print the difference between now and start time
    Duration timeElapsed = Duration.between(startTime, Instant.now());
}
private void executeAction() throws JsonProcessingException {
    if (this.reporteRecurrente != null) {
        System.out.println("Executing action from scheduled task... " + Instant.now());
    }

    // Calculate the interval start and end times based on the scheduled start time and interval duration
    Instant intervalStart = startTime;
    Instant intervalEnd = intervalStart.plus(intervalDuration);

    // Filter ventas based on the interval
    List<Venta> ventas = ventaRepository.findByFechaBetween(intervalStart, intervalEnd);

    int ventasSize = ventas.size();
    System.out.println("cantidad de ventas en este intervalo: " + ventasSize);

    // Only execute the loop if there are ventas in the interval
    if (ventasSize > 0) {
        String urlString = "http://10.101.102.1:8080/api/reporte/datos";
        String bearertokenString = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9jYW11eiIsImF1dGgiOiIiLCJleHAiOjE5OTIyNjIzMjV9.AP2Ckng1LLAkbtDp3af8NaP9FAE0zSvm5Dd18hgNmuKEwRkFLKgHVfz_M-xFsLYZGtIbAByPhE0AbzzmTeoXqw";

        for (Venta venta : ventas) {
            // Construct the JSON object to be sent in the request body
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("accion", "respuesta_reporte");
            List<Map<String, Object>> datosList = new ArrayList<>();
            Map<String, Object> ventaMap = new HashMap<>();
            ventaMap.put("id", venta.getId());
            ventaMap.put("fecha", venta.getFecha().toString());
            ventaMap.put("menu", venta.getMenu());
            ventaMap.put("precio", venta.getPrecio());
            datosList.add(ventaMap);
            requestBodyMap.put("datos", datosList);

            // Convert the JSON object to a string and send an HTTP POST request to the specified URL
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBodyString = objectMapper.writeValueAsString(requestBodyMap);
            System.out.println(utilsJulia.sendHttpPostRequest(urlString, requestBodyString, bearertokenString));
        }
    }

    // Check if finish time has been reached
    if (Instant.now().isAfter(finishTime)) {
        // Stop scheduling further executions of this method
        this.taskScheduler.shutdown();
        System.out.println("FINISH AT   " + Instant.now());
    }
}




}