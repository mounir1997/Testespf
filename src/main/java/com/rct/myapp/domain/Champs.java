package com.rct.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Champs.
 */
@Entity
@Table(name = "champs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Champs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Column(name = "longueur", nullable = false)
    private String longueur;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Champs code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPosition() {
        return position;
    }

    public Champs position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLongueur() {
        return longueur;
    }

    public Champs longueur(String longueur) {
        this.longueur = longueur;
        return this;
    }

    public void setLongueur(String longueur) {
        this.longueur = longueur;
    }

    public String getType() {
        return type;
    }

    public Champs type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Champs champs = (Champs) o;
        if (champs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), champs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Champs{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", position='" + getPosition() + "'" +
            ", longueur='" + getLongueur() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
