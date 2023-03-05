package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Menu.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Menu implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "creado")
    private Instant creado;

    @Column(name = "actualizado")
    private Instant actualizado;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "menu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "menu" }, allowSetters = true)
    private Set<Venta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Menu id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Menu nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Menu descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return this.precio;
    }

    public Menu precio(Float precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getUrlImagen() {
        return this.urlImagen;
    }

    public Menu urlImagen(String urlImagen) {
        this.setUrlImagen(urlImagen);
        return this;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Menu activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Instant getCreado() {
        return this.creado;
    }

    public Menu creado(Instant creado) {
        this.setCreado(creado);
        return this;
    }

    public void setCreado(Instant creado) {
        this.creado = creado;
    }

    public Instant getActualizado() {
        return this.actualizado;
    }

    public Menu actualizado(Instant actualizado) {
        this.setActualizado(actualizado);
        return this;
    }

    public void setActualizado(Instant actualizado) {
        this.actualizado = actualizado;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Menu setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<Venta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<Venta> ventas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.setMenu(null));
        }
        if (ventas != null) {
            ventas.forEach(i -> i.setMenu(this));
        }
        this.ventas = ventas;
    }

    public Menu ventas(Set<Venta> ventas) {
        this.setVentas(ventas);
        return this;
    }

    public Menu addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.setMenu(this);
        return this;
    }

    public Menu removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.setMenu(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio=" + getPrecio() +
            ", urlImagen='" + getUrlImagen() + "'" +
            ", activo='" + getActivo() + "'" +
            ", creado='" + getCreado() + "'" +
            ", actualizado='" + getActualizado() + "'" +
            "}";
    }
}
