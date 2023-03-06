package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ReporteRecurrente;
import com.mycompany.myapp.repository.ReporteRecurrenteRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ReporteRecurrente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReporteRecurrenteResource {

    private final Logger log = LoggerFactory.getLogger(ReporteRecurrenteResource.class);

    private static final String ENTITY_NAME = "reporteRecurrente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReporteRecurrenteRepository reporteRecurrenteRepository;

    public ReporteRecurrenteResource(ReporteRecurrenteRepository reporteRecurrenteRepository) {
        this.reporteRecurrenteRepository = reporteRecurrenteRepository;
    }

    /**
     * {@code POST  /reporte-recurrentes} : Create a new reporteRecurrente.
     *
     * @param reporteRecurrente the reporteRecurrente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reporteRecurrente, or with status {@code 400 (Bad Request)} if the reporteRecurrente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reporte-recurrentes")
    public ResponseEntity<ReporteRecurrente> createReporteRecurrente(@RequestBody ReporteRecurrente reporteRecurrente)
        throws URISyntaxException {
        log.debug("REST request to save ReporteRecurrente : {}", reporteRecurrente);
        if (reporteRecurrente.getId() != null) {
            throw new BadRequestAlertException("A new reporteRecurrente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReporteRecurrente result = reporteRecurrenteRepository.save(reporteRecurrente);
        return ResponseEntity
            .created(new URI("/api/reporte-recurrentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reporte-recurrentes/:id} : Updates an existing reporteRecurrente.
     *
     * @param id the id of the reporteRecurrente to save.
     * @param reporteRecurrente the reporteRecurrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reporteRecurrente,
     * or with status {@code 400 (Bad Request)} if the reporteRecurrente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reporteRecurrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reporte-recurrentes/{id}")
    public ResponseEntity<ReporteRecurrente> updateReporteRecurrente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReporteRecurrente reporteRecurrente
    ) throws URISyntaxException {
        log.debug("REST request to update ReporteRecurrente : {}, {}", id, reporteRecurrente);
        if (reporteRecurrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reporteRecurrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reporteRecurrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReporteRecurrente result = reporteRecurrenteRepository.save(reporteRecurrente);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reporteRecurrente.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reporte-recurrentes/:id} : Partial updates given fields of an existing reporteRecurrente, field will ignore if it is null
     *
     * @param id the id of the reporteRecurrente to save.
     * @param reporteRecurrente the reporteRecurrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reporteRecurrente,
     * or with status {@code 400 (Bad Request)} if the reporteRecurrente is not valid,
     * or with status {@code 404 (Not Found)} if the reporteRecurrente is not found,
     * or with status {@code 500 (Internal Server Error)} if the reporteRecurrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reporte-recurrentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReporteRecurrente> partialUpdateReporteRecurrente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReporteRecurrente reporteRecurrente
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReporteRecurrente partially : {}, {}", id, reporteRecurrente);
        if (reporteRecurrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reporteRecurrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reporteRecurrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReporteRecurrente> result = reporteRecurrenteRepository
            .findById(reporteRecurrente.getId())
            .map(existingReporteRecurrente -> {
                if (reporteRecurrente.getAccion() != null) {
                    existingReporteRecurrente.setAccion(reporteRecurrente.getAccion());
                }
                if (reporteRecurrente.getFranquiciaUUID() != null) {
                    existingReporteRecurrente.setFranquiciaUUID(reporteRecurrente.getFranquiciaUUID());
                }
                if (reporteRecurrente.getTipo() != null) {
                    existingReporteRecurrente.setTipo(reporteRecurrente.getTipo());
                }
                if (reporteRecurrente.getFechaInicio() != null) {
                    existingReporteRecurrente.setFechaInicio(reporteRecurrente.getFechaInicio());
                }
                if (reporteRecurrente.getFechaFin() != null) {
                    existingReporteRecurrente.setFechaFin(reporteRecurrente.getFechaFin());
                }
                if (reporteRecurrente.getIntervalo() != null) {
                    existingReporteRecurrente.setIntervalo(reporteRecurrente.getIntervalo());
                }

                return existingReporteRecurrente;
            })
            .map(reporteRecurrenteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reporteRecurrente.getId().toString())
        );
    }

    /**
     * {@code GET  /reporte-recurrentes} : get all the reporteRecurrentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reporteRecurrentes in body.
     */
    @GetMapping("/reporte-recurrentes")
    public List<ReporteRecurrente> getAllReporteRecurrentes() {
        log.debug("REST request to get all ReporteRecurrentes");
        return reporteRecurrenteRepository.findAll();
    }

    /**
     * {@code GET  /reporte-recurrentes/:id} : get the "id" reporteRecurrente.
     *
     * @param id the id of the reporteRecurrente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reporteRecurrente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reporte-recurrentes/{id}")
    public ResponseEntity<ReporteRecurrente> getReporteRecurrente(@PathVariable Long id) {
        log.debug("REST request to get ReporteRecurrente : {}", id);
        Optional<ReporteRecurrente> reporteRecurrente = reporteRecurrenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reporteRecurrente);
    }

    /**
     * {@code DELETE  /reporte-recurrentes/:id} : delete the "id" reporteRecurrente.
     *
     * @param id the id of the reporteRecurrente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reporte-recurrentes/{id}")
    public ResponseEntity<Void> deleteReporteRecurrente(@PathVariable Long id) {
        log.debug("REST request to delete ReporteRecurrente : {}", id);
        reporteRecurrenteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
