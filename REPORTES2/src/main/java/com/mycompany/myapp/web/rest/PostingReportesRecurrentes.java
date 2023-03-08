package com.mycompany.myapp.web.rest;

import java.time.Duration;
import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.ReporteRecurrente;

@Component
public class PostingReportesRecurrentes {
    private ReporteRecurrente reporteRecurrente;
    public void executeAction() {
        if(this.reporteRecurrente != null){
            Instant startTime = this.reporteRecurrente.getFechaInicio();
            Instant endTime = this.reporteRecurrente.getFechaFin();
            Duration interval = Duration.parse(this.reporteRecurrente.getIntervalo());
            
            System.out.println("Starting scheduled task at " + startTime);
            System.out.println("Ending scheduled task at " + endTime);
    
            while (Instant.now().isBefore(endTime)) {
                // Execute action for current interval
                System.out.println("Executing action at " + Instant.now());
    
                try {
                    Thread.sleep(interval.toMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        // Do something here with the reporteRecurrente object
    }
    

    public void setReporteRecurrente(ReporteRecurrente reporteRecurrente) {
        this.reporteRecurrente = reporteRecurrente;
    }
}
