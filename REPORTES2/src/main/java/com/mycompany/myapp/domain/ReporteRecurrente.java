package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReporteRecurrente.
 */
@Entity
@Table(name = "reporte_recurrente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReporteRecurrente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "accion")
    private String accion;

    @Column(name = "franquicia_uuid")
    private String franquiciaUUID;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_fin")
    private Instant fechaFin;

    @Column(name = "intervalo")
    private String intervalo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReporteRecurrente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccion() {
        return this.accion;
    }

    public ReporteRecurrente accion(String accion) {
        this.setAccion(accion);
        return this;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFranquiciaUUID() {
        return this.franquiciaUUID;
    }

    public ReporteRecurrente franquiciaUUID(String franquiciaUUID) {
        this.setFranquiciaUUID(franquiciaUUID);
        return this;
    }

    public void setFranquiciaUUID(String franquiciaUUID) {
        this.franquiciaUUID = franquiciaUUID;
    }

    public String getTipo() {
        return this.tipo;
    }

    public ReporteRecurrente tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public ReporteRecurrente fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return this.fechaFin;
    }

    public ReporteRecurrente fechaFin(Instant fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIntervalo() {
        return this.intervalo;
    }

    public ReporteRecurrente intervalo(String intervalo) {
        this.setIntervalo(intervalo);
        return this;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReporteRecurrente)) {
            return false;
        }
        return id != null && id.equals(((ReporteRecurrente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReporteRecurrente{" +
            "id=" + getId() +
            ", accion='" + getAccion() + "'" +
            ", franquiciaUUID='" + getFranquiciaUUID() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", intervalo='" + getIntervalo() + "'" +
            "}";
    }
}
