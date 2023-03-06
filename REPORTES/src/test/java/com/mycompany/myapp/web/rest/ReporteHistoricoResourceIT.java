package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ReporteHistorico;
import com.mycompany.myapp.repository.ReporteHistoricoRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReporteHistoricoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReporteHistoricoResourceIT {

    private static final String DEFAULT_ACCION = "AAAAAAAAAA";
    private static final String UPDATED_ACCION = "BBBBBBBBBB";

    private static final String DEFAULT_FRANQUICIA_UUID = "AAAAAAAAAA";
    private static final String UPDATED_FRANQUICIA_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/reporte-historicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReporteHistoricoRepository reporteHistoricoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReporteHistoricoMockMvc;

    private ReporteHistorico reporteHistorico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReporteHistorico createEntity(EntityManager em) {
        ReporteHistorico reporteHistorico = new ReporteHistorico()
            .accion(DEFAULT_ACCION)
            .franquiciaUUID(DEFAULT_FRANQUICIA_UUID)
            .tipo(DEFAULT_TIPO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN);
        return reporteHistorico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReporteHistorico createUpdatedEntity(EntityManager em) {
        ReporteHistorico reporteHistorico = new ReporteHistorico()
            .accion(UPDATED_ACCION)
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);
        return reporteHistorico;
    }

    @BeforeEach
    public void initTest() {
        reporteHistorico = createEntity(em);
    }

    @Test
    @Transactional
    void createReporteHistorico() throws Exception {
        int databaseSizeBeforeCreate = reporteHistoricoRepository.findAll().size();
        // Create the ReporteHistorico
        restReporteHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isCreated());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeCreate + 1);
        ReporteHistorico testReporteHistorico = reporteHistoricoList.get(reporteHistoricoList.size() - 1);
        assertThat(testReporteHistorico.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testReporteHistorico.getFranquiciaUUID()).isEqualTo(DEFAULT_FRANQUICIA_UUID);
        assertThat(testReporteHistorico.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testReporteHistorico.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testReporteHistorico.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
    }

    @Test
    @Transactional
    void createReporteHistoricoWithExistingId() throws Exception {
        // Create the ReporteHistorico with an existing ID
        reporteHistorico.setId(1L);

        int databaseSizeBeforeCreate = reporteHistoricoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReporteHistoricoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReporteHistoricos() throws Exception {
        // Initialize the database
        reporteHistoricoRepository.saveAndFlush(reporteHistorico);

        // Get all the reporteHistoricoList
        restReporteHistoricoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reporteHistorico.getId().intValue())))
            .andExpect(jsonPath("$.[*].accion").value(hasItem(DEFAULT_ACCION)))
            .andExpect(jsonPath("$.[*].franquiciaUUID").value(hasItem(DEFAULT_FRANQUICIA_UUID)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
    }

    @Test
    @Transactional
    void getReporteHistorico() throws Exception {
        // Initialize the database
        reporteHistoricoRepository.saveAndFlush(reporteHistorico);

        // Get the reporteHistorico
        restReporteHistoricoMockMvc
            .perform(get(ENTITY_API_URL_ID, reporteHistorico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reporteHistorico.getId().intValue()))
            .andExpect(jsonPath("$.accion").value(DEFAULT_ACCION))
            .andExpect(jsonPath("$.franquiciaUUID").value(DEFAULT_FRANQUICIA_UUID))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReporteHistorico() throws Exception {
        // Get the reporteHistorico
        restReporteHistoricoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReporteHistorico() throws Exception {
        // Initialize the database
        reporteHistoricoRepository.saveAndFlush(reporteHistorico);

        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();

        // Update the reporteHistorico
        ReporteHistorico updatedReporteHistorico = reporteHistoricoRepository.findById(reporteHistorico.getId()).get();
        // Disconnect from session so that the updates on updatedReporteHistorico are not directly saved in db
        em.detach(updatedReporteHistorico);
        updatedReporteHistorico
            .accion(UPDATED_ACCION)
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);

        restReporteHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReporteHistorico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReporteHistorico))
            )
            .andExpect(status().isOk());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
        ReporteHistorico testReporteHistorico = reporteHistoricoList.get(reporteHistoricoList.size() - 1);
        assertThat(testReporteHistorico.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testReporteHistorico.getFranquiciaUUID()).isEqualTo(UPDATED_FRANQUICIA_UUID);
        assertThat(testReporteHistorico.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporteHistorico.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReporteHistorico.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    void putNonExistingReporteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();
        reporteHistorico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReporteHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reporteHistorico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReporteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();
        reporteHistorico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReporteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();
        reporteHistorico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteHistoricoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReporteHistoricoWithPatch() throws Exception {
        // Initialize the database
        reporteHistoricoRepository.saveAndFlush(reporteHistorico);

        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();

        // Update the reporteHistorico using partial update
        ReporteHistorico partialUpdatedReporteHistorico = new ReporteHistorico();
        partialUpdatedReporteHistorico.setId(reporteHistorico.getId());

        partialUpdatedReporteHistorico.tipo(UPDATED_TIPO).fechaFin(UPDATED_FECHA_FIN);

        restReporteHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReporteHistorico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReporteHistorico))
            )
            .andExpect(status().isOk());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
        ReporteHistorico testReporteHistorico = reporteHistoricoList.get(reporteHistoricoList.size() - 1);
        assertThat(testReporteHistorico.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testReporteHistorico.getFranquiciaUUID()).isEqualTo(DEFAULT_FRANQUICIA_UUID);
        assertThat(testReporteHistorico.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporteHistorico.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testReporteHistorico.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    void fullUpdateReporteHistoricoWithPatch() throws Exception {
        // Initialize the database
        reporteHistoricoRepository.saveAndFlush(reporteHistorico);

        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();

        // Update the reporteHistorico using partial update
        ReporteHistorico partialUpdatedReporteHistorico = new ReporteHistorico();
        partialUpdatedReporteHistorico.setId(reporteHistorico.getId());

        partialUpdatedReporteHistorico
            .accion(UPDATED_ACCION)
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN);

        restReporteHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReporteHistorico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReporteHistorico))
            )
            .andExpect(status().isOk());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
        ReporteHistorico testReporteHistorico = reporteHistoricoList.get(reporteHistoricoList.size() - 1);
        assertThat(testReporteHistorico.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testReporteHistorico.getFranquiciaUUID()).isEqualTo(UPDATED_FRANQUICIA_UUID);
        assertThat(testReporteHistorico.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporteHistorico.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReporteHistorico.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingReporteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();
        reporteHistorico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReporteHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reporteHistorico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReporteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();
        reporteHistorico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReporteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = reporteHistoricoRepository.findAll().size();
        reporteHistorico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteHistoricoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporteHistorico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReporteHistorico in the database
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReporteHistorico() throws Exception {
        // Initialize the database
        reporteHistoricoRepository.saveAndFlush(reporteHistorico);

        int databaseSizeBeforeDelete = reporteHistoricoRepository.findAll().size();

        // Delete the reporteHistorico
        restReporteHistoricoMockMvc
            .perform(delete(ENTITY_API_URL_ID, reporteHistorico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReporteHistorico> reporteHistoricoList = reporteHistoricoRepository.findAll();
        assertThat(reporteHistoricoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
