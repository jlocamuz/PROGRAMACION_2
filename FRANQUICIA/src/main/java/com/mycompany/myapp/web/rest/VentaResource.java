package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.MenuRepository;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Venta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VentaResource {

    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private MenuRepository menuRepository; 
    
    private final Logger log = LoggerFactory.getLogger(VentaResource.class);

    private static final String ENTITY_NAME = "venta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;



    public VentaResource(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    /**
     * {@code POST  /ventas} : Create a new venta.
     *
     * @param venta the venta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venta, or with status {@code 400 (Bad Request)} if the venta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws JsonProcessingException
     */
  
     @PostMapping("/ventas")
     public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) throws URISyntaxException, JsonProcessingException {
       Optional<Menu> optionalMenu = menuRepository.findById(venta.getMenu().getId());
       if (optionalMenu.isPresent() && optionalMenu.get().getActivo()) {
        Menu menu = optionalMenu.get();
        venta.setPrecio(menu.getPrecio());
        Venta result = ventaRepository.save(venta);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("idVenta", venta.getId().toString());
        requestBodyMap.put("fecha", venta.getFecha().toString());
        requestBodyMap.put("precio", venta.getPrecio().toString());
        requestBodyMap.put("menu", menu.getId().toString());
        //requestBodyMap.put("franquiciaID", "56b7688c-57c3-4f6f-95eb-39e568aa40e9");
        String requestBody = objectMapper.writeValueAsString(requestBodyMap);
        String urlString = "http://127.0.1.1:9090/api/ventas";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String username = "admin";
        String password = "admin";
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        
        headers.set("Authorization", "Basic " + encodedCredentials);
    
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlString, request, String.class);
        String responseBody = response.getBody();
        System.out.println(responseBody);
        System.out.println("VENTA CREADA" + requestBody);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
                             .body(result);
      } else {
        return ResponseEntity.badRequest().build();
      }
      
     }

    /**
     * {@code PUT  /ventas/:id} : Updates an existing venta.
     *
     * @param id the id of the venta to save.
     * @param venta the venta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venta,
     * or with status {@code 400 (Bad Request)} if the venta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ventas/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable(value = "id", required = false) final Long id, @RequestBody Venta venta)
        throws URISyntaxException {
        log.debug("REST request to update Venta : {}, {}", id, venta);
        if (venta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, venta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Venta result = ventaRepository.save(venta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ventas/:id} : Partial updates given fields of an existing venta, field will ignore if it is null
     *
     * @param id the id of the venta to save.
     * @param venta the venta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venta,
     * or with status {@code 400 (Bad Request)} if the venta is not valid,
     * or with status {@code 404 (Not Found)} if the venta is not found,
     * or with status {@code 500 (Internal Server Error)} if the venta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ventas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Venta> partialUpdateVenta(@PathVariable(value = "id", required = false) final Long id, @RequestBody Venta venta)
        throws URISyntaxException {
        log.debug("REST request to partial update Venta partially : {}, {}", id, venta);
        if (venta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, venta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Venta> result = ventaRepository
            .findById(venta.getId())
            .map(existingVenta -> {
                if (venta.getFecha() != null) {
                    existingVenta.setFecha(venta.getFecha());
                }
                if (venta.getPrecio() != null) {
                    existingVenta.setPrecio(venta.getPrecio());
                }

                return existingVenta;
            })
            .map(ventaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venta.getId().toString())
        );
    }

    /**
     * {@code GET  /ventas} : get all the ventas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventas in body.
     */
    @GetMapping("/ventas")
    public List<Venta> getAllVentas() {
        log.debug("REST request to get all Ventas");
        return ventaRepository.findAll();
    }

    /**
     * {@code GET  /ventas/:id} : get the "id" venta.
     *
     * @param id the id of the venta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ventas/{id}")
    public ResponseEntity<Venta> getVenta(@PathVariable Long id) {
        log.debug("REST request to get Venta : {}", id);
        Optional<Venta> venta = ventaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(venta);
    }

    /**
     * {@code DELETE  /ventas/:id} : delete the "id" venta.
     *
     * @param id the id of the venta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        log.debug("REST request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
