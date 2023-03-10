package com.mycompany.myapp.web.rest;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private ThreadPoolTaskScheduler taskScheduler;

    private Instant startTime;
    private Instant finishTime;
    private Duration intervalDuration;

    public PostingReportesRecurrentes() {
        this.startTime = Instant.now();
    }

public void start(ReporteRecurrente reporteRecurrente) {
    Instant now = Instant.now();
    System.out.println(now);
    this.reporteRecurrente = reporteRecurrente;
    this.startTime = reporteRecurrente.getFechaInicio();
    this.finishTime = reporteRecurrente.getFechaFin();
    this.intervalDuration = Duration.parse(reporteRecurrente.getIntervalo());
    long initialDelay = Duration.between(Instant.now(), startTime).toMillis();
    long intervalMillis = intervalDuration.toMillis();
    this.taskScheduler.scheduleAtFixedRate(this::executeAction, initialDelay);
    
    // Print the difference between now and start time
    Duration timeElapsed = Duration.between(startTime, Instant.now());
}

    private void executeAction() {
        if(this.reporteRecurrente != null){
            System.out.println(this.reporteRecurrente.getIntervalo());
            System.out.println(this.reporteRecurrente);
            
        }

        // Do something here with the reporteRecurrente object
        // ...

        // Check if finish time has been reached
        if (Instant.now().isAfter(finishTime)) {
            // Stop scheduling further executions of this method
            this.taskScheduler.shutdown();
            System.out.println("stop");
        }
    }
}