package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_venta")
    private String idVenta;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "menu")
    private Integer menu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVenta() {
        return this.idVenta;
    }

    public Venta idVenta(String idVenta) {
        this.setIdVenta(idVenta);
        return this;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Venta fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Float getPrecio() {
        return this.precio;
    }

    public Venta precio(Float precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getMenu() {
        return this.menu;
    }

    public Venta menu(Integer menu) {
        this.setMenu(menu);
        return this;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", idVenta='" + getIdVenta() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", precio=" + getPrecio() +
            ", menu=" + getMenu() +
            "}";
    }
}
