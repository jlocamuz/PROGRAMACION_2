package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ReporteHistorico;
import com.mycompany.myapp.repository.ReporteHistoricoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ReporteHistorico}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReporteHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(ReporteHistoricoResource.class);

    private static final String ENTITY_NAME = "reporteHistorico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReporteHistoricoRepository reporteHistoricoRepository;

    public ReporteHistoricoResource(ReporteHistoricoRepository reporteHistoricoRepository) {
        this.reporteHistoricoRepository = reporteHistoricoRepository;
    }

    /**
     * {@code POST  /reporte-historicos} : Create a new reporteHistorico.
     *
     * @param reporteHistorico the reporteHistorico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reporteHistorico, or with status {@code 400 (Bad Request)} if the reporteHistorico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reporte-historicos")
    public ResponseEntity<ReporteHistorico> createReporteHistorico(@RequestBody ReporteHistorico reporteHistorico)
        throws URISyntaxException {
        log.debug("REST request to save ReporteHistorico : {}", reporteHistorico);
        if (reporteHistorico.getId() != null) {
            throw new BadRequestAlertException("A new reporteHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReporteHistorico result = reporteHistoricoRepository.save(reporteHistorico);
        return ResponseEntity
            .created(new URI("/api/reporte-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reporte-historicos/:id} : Updates an existing reporteHistorico.
     *
     * @param id the id of the reporteHistorico to save.
     * @param reporteHistorico the reporteHistorico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reporteHistorico,
     * or with status {@code 400 (Bad Request)} if the reporteHistorico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reporteHistorico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reporte-historicos/{id}")
    public ResponseEntity<ReporteHistorico> updateReporteHistorico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReporteHistorico reporteHistorico
    ) throws URISyntaxException {
        log.debug("REST request to update ReporteHistorico : {}, {}", id, reporteHistorico);
        if (reporteHistorico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reporteHistorico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reporteHistoricoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReporteHistorico result = reporteHistoricoRepository.save(reporteHistorico);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reporteHistorico.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reporte-historicos/:id} : Partial updates given fields of an existing reporteHistorico, field will ignore if it is null
     *
     * @param id the id of the reporteHistorico to save.
     * @param reporteHistorico the reporteHistorico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reporteHistorico,
     * or with status {@code 400 (Bad Request)} if the reporteHistorico is not valid,
     * or with status {@code 404 (Not Found)} if the reporteHistorico is not found,
     * or with status {@code 500 (Internal Server Error)} if the reporteHistorico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reporte-historicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReporteHistorico> partialUpdateReporteHistorico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReporteHistorico reporteHistorico
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReporteHistorico partially : {}, {}", id, reporteHistorico);
        if (reporteHistorico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reporteHistorico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reporteHistoricoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReporteHistorico> result = reporteHistoricoRepository
            .findById(reporteHistorico.getId())
            .map(existingReporteHistorico -> {
                if (reporteHistorico.getAccion() != null) {
                    existingReporteHistorico.setAccion(reporteHistorico.getAccion());
                }
                if (reporteHistorico.getFranquiciaUUID() != null) {
                    existingReporteHistorico.setFranquiciaUUID(reporteHistorico.getFranquiciaUUID());
                }
                if (reporteHistorico.getTipo() != null) {
                    existingReporteHistorico.setTipo(reporteHistorico.getTipo());
                }
                if (reporteHistorico.getFechaInicio() != null) {
                    existingReporteHistorico.setFechaInicio(reporteHistorico.getFechaInicio());
                }
                if (reporteHistorico.getFechaFin() != null) {
                    existingReporteHistorico.setFechaFin(reporteHistorico.getFechaFin());
                }

                return existingReporteHistorico;
            })
            .map(reporteHistoricoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reporteHistorico.getId().toString())
        );
    }

    /**
     * {@code GET  /reporte-historicos} : get all the reporteHistoricos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reporteHistoricos in body.
     */
    @GetMapping("/reporte-historicos")
    public List<ReporteHistorico> getAllReporteHistoricos() {
        log.debug("REST request to get all ReporteHistoricos");
        return reporteHistoricoRepository.findAll();
    }

    /**
     * {@code GET  /reporte-historicos/:id} : get the "id" reporteHistorico.
     *
     * @param id the id of the reporteHistorico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reporteHistorico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reporte-historicos/{id}")
    public ResponseEntity<ReporteHistorico> getReporteHistorico(@PathVariable Long id) {
        log.debug("REST request to get ReporteHistorico : {}", id);
        Optional<ReporteHistorico> reporteHistorico = reporteHistoricoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reporteHistorico);
    }

    /**
     * {@code DELETE  /reporte-historicos/:id} : delete the "id" reporteHistorico.
     *
     * @param id the id of the reporteHistorico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reporte-historicos/{id}")
    public ResponseEntity<Void> deleteReporteHistorico(@PathVariable Long id) {
        log.debug("REST request to delete ReporteHistorico : {}", id);
        reporteHistoricoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
