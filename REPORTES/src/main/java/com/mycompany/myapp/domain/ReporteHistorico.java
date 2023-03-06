package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReporteHistorico.
 */
@Entity
@Table(name = "reporte_historico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReporteHistorico implements Serializable {

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReporteHistorico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccion() {
        return this.accion;
    }

    public ReporteHistorico accion(String accion) {
        this.setAccion(accion);
        return this;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFranquiciaUUID() {
        return this.franquiciaUUID;
    }

    public ReporteHistorico franquiciaUUID(String franquiciaUUID) {
        this.setFranquiciaUUID(franquiciaUUID);
        return this;
    }

    public void setFranquiciaUUID(String franquiciaUUID) {
        this.franquiciaUUID = franquiciaUUID;
    }

    public String getTipo() {
        return this.tipo;
    }

    public ReporteHistorico tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public ReporteHistorico fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return this.fechaFin;
    }

    public ReporteHistorico fechaFin(Instant fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReporteHistorico)) {
            return false;
        }
        return id != null && id.equals(((ReporteHistorico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReporteHistorico{" +
            "id=" + getId() +
            ", accion='" + getAccion() + "'" +
            ", franquiciaUUID='" + getFranquiciaUUID() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
