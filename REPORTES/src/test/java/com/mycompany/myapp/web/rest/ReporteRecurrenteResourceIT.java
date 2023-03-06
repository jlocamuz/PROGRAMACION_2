package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ReporteRecurrente;
import com.mycompany.myapp.repository.ReporteRecurrenteRepository;
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
 * Integration tests for the {@link ReporteRecurrenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReporteRecurrenteResourceIT {

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

    private static final String DEFAULT_INTERVALO = "AAAAAAAAAA";
    private static final String UPDATED_INTERVALO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reporte-recurrentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReporteRecurrenteRepository reporteRecurrenteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReporteRecurrenteMockMvc;

    private ReporteRecurrente reporteRecurrente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReporteRecurrente createEntity(EntityManager em) {
        ReporteRecurrente reporteRecurrente = new ReporteRecurrente()
            .accion(DEFAULT_ACCION)
            .franquiciaUUID(DEFAULT_FRANQUICIA_UUID)
            .tipo(DEFAULT_TIPO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .intervalo(DEFAULT_INTERVALO);
        return reporteRecurrente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReporteRecurrente createUpdatedEntity(EntityManager em) {
        ReporteRecurrente reporteRecurrente = new ReporteRecurrente()
            .accion(UPDATED_ACCION)
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .intervalo(UPDATED_INTERVALO);
        return reporteRecurrente;
    }

    @BeforeEach
    public void initTest() {
        reporteRecurrente = createEntity(em);
    }

    @Test
    @Transactional
    void createReporteRecurrente() throws Exception {
        int databaseSizeBeforeCreate = reporteRecurrenteRepository.findAll().size();
        // Create the ReporteRecurrente
        restReporteRecurrenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isCreated());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeCreate + 1);
        ReporteRecurrente testReporteRecurrente = reporteRecurrenteList.get(reporteRecurrenteList.size() - 1);
        assertThat(testReporteRecurrente.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testReporteRecurrente.getFranquiciaUUID()).isEqualTo(DEFAULT_FRANQUICIA_UUID);
        assertThat(testReporteRecurrente.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testReporteRecurrente.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testReporteRecurrente.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testReporteRecurrente.getIntervalo()).isEqualTo(DEFAULT_INTERVALO);
    }

    @Test
    @Transactional
    void createReporteRecurrenteWithExistingId() throws Exception {
        // Create the ReporteRecurrente with an existing ID
        reporteRecurrente.setId(1L);

        int databaseSizeBeforeCreate = reporteRecurrenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReporteRecurrenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReporteRecurrentes() throws Exception {
        // Initialize the database
        reporteRecurrenteRepository.saveAndFlush(reporteRecurrente);

        // Get all the reporteRecurrenteList
        restReporteRecurrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reporteRecurrente.getId().intValue())))
            .andExpect(jsonPath("$.[*].accion").value(hasItem(DEFAULT_ACCION)))
            .andExpect(jsonPath("$.[*].franquiciaUUID").value(hasItem(DEFAULT_FRANQUICIA_UUID)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].intervalo").value(hasItem(DEFAULT_INTERVALO)));
    }

    @Test
    @Transactional
    void getReporteRecurrente() throws Exception {
        // Initialize the database
        reporteRecurrenteRepository.saveAndFlush(reporteRecurrente);

        // Get the reporteRecurrente
        restReporteRecurrenteMockMvc
            .perform(get(ENTITY_API_URL_ID, reporteRecurrente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reporteRecurrente.getId().intValue()))
            .andExpect(jsonPath("$.accion").value(DEFAULT_ACCION))
            .andExpect(jsonPath("$.franquiciaUUID").value(DEFAULT_FRANQUICIA_UUID))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.intervalo").value(DEFAULT_INTERVALO));
    }

    @Test
    @Transactional
    void getNonExistingReporteRecurrente() throws Exception {
        // Get the reporteRecurrente
        restReporteRecurrenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReporteRecurrente() throws Exception {
        // Initialize the database
        reporteRecurrenteRepository.saveAndFlush(reporteRecurrente);

        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();

        // Update the reporteRecurrente
        ReporteRecurrente updatedReporteRecurrente = reporteRecurrenteRepository.findById(reporteRecurrente.getId()).get();
        // Disconnect from session so that the updates on updatedReporteRecurrente are not directly saved in db
        em.detach(updatedReporteRecurrente);
        updatedReporteRecurrente
            .accion(UPDATED_ACCION)
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .intervalo(UPDATED_INTERVALO);

        restReporteRecurrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReporteRecurrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReporteRecurrente))
            )
            .andExpect(status().isOk());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
        ReporteRecurrente testReporteRecurrente = reporteRecurrenteList.get(reporteRecurrenteList.size() - 1);
        assertThat(testReporteRecurrente.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testReporteRecurrente.getFranquiciaUUID()).isEqualTo(UPDATED_FRANQUICIA_UUID);
        assertThat(testReporteRecurrente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporteRecurrente.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReporteRecurrente.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReporteRecurrente.getIntervalo()).isEqualTo(UPDATED_INTERVALO);
    }

    @Test
    @Transactional
    void putNonExistingReporteRecurrente() throws Exception {
        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();
        reporteRecurrente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReporteRecurrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reporteRecurrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReporteRecurrente() throws Exception {
        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();
        reporteRecurrente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteRecurrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReporteRecurrente() throws Exception {
        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();
        reporteRecurrente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteRecurrenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReporteRecurrenteWithPatch() throws Exception {
        // Initialize the database
        reporteRecurrenteRepository.saveAndFlush(reporteRecurrente);

        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();

        // Update the reporteRecurrente using partial update
        ReporteRecurrente partialUpdatedReporteRecurrente = new ReporteRecurrente();
        partialUpdatedReporteRecurrente.setId(reporteRecurrente.getId());

        partialUpdatedReporteRecurrente
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .intervalo(UPDATED_INTERVALO);

        restReporteRecurrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReporteRecurrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReporteRecurrente))
            )
            .andExpect(status().isOk());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
        ReporteRecurrente testReporteRecurrente = reporteRecurrenteList.get(reporteRecurrenteList.size() - 1);
        assertThat(testReporteRecurrente.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testReporteRecurrente.getFranquiciaUUID()).isEqualTo(UPDATED_FRANQUICIA_UUID);
        assertThat(testReporteRecurrente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporteRecurrente.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReporteRecurrente.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReporteRecurrente.getIntervalo()).isEqualTo(UPDATED_INTERVALO);
    }

    @Test
    @Transactional
    void fullUpdateReporteRecurrenteWithPatch() throws Exception {
        // Initialize the database
        reporteRecurrenteRepository.saveAndFlush(reporteRecurrente);

        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();

        // Update the reporteRecurrente using partial update
        ReporteRecurrente partialUpdatedReporteRecurrente = new ReporteRecurrente();
        partialUpdatedReporteRecurrente.setId(reporteRecurrente.getId());

        partialUpdatedReporteRecurrente
            .accion(UPDATED_ACCION)
            .franquiciaUUID(UPDATED_FRANQUICIA_UUID)
            .tipo(UPDATED_TIPO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .intervalo(UPDATED_INTERVALO);

        restReporteRecurrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReporteRecurrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReporteRecurrente))
            )
            .andExpect(status().isOk());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
        ReporteRecurrente testReporteRecurrente = reporteRecurrenteList.get(reporteRecurrenteList.size() - 1);
        assertThat(testReporteRecurrente.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testReporteRecurrente.getFranquiciaUUID()).isEqualTo(UPDATED_FRANQUICIA_UUID);
        assertThat(testReporteRecurrente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testReporteRecurrente.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testReporteRecurrente.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testReporteRecurrente.getIntervalo()).isEqualTo(UPDATED_INTERVALO);
    }

    @Test
    @Transactional
    void patchNonExistingReporteRecurrente() throws Exception {
        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();
        reporteRecurrente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReporteRecurrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reporteRecurrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReporteRecurrente() throws Exception {
        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();
        reporteRecurrente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteRecurrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReporteRecurrente() throws Exception {
        int databaseSizeBeforeUpdate = reporteRecurrenteRepository.findAll().size();
        reporteRecurrente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReporteRecurrenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reporteRecurrente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReporteRecurrente in the database
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReporteRecurrente() throws Exception {
        // Initialize the database
        reporteRecurrenteRepository.saveAndFlush(reporteRecurrente);

        int databaseSizeBeforeDelete = reporteRecurrenteRepository.findAll().size();

        // Delete the reporteRecurrente
        restReporteRecurrenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, reporteRecurrente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReporteRecurrente> reporteRecurrenteList = reporteRecurrenteRepository.findAll();
        assertThat(reporteRecurrenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
