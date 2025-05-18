package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Thuetn.
 */
@Entity
@Table(name = "thuetn")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Thuetn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bacthue")
    private String bacthue;

    @Column(name = "tu")
    private Integer tu;

    @Column(name = "den")
    private Integer den;

    @Column(name = "thuesuat")
    private Float thuesuat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Thuetn id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBacthue() {
        return this.bacthue;
    }

    public Thuetn bacthue(String bacthue) {
        this.setBacthue(bacthue);
        return this;
    }

    public void setBacthue(String bacthue) {
        this.bacthue = bacthue;
    }

    public Integer getTu() {
        return this.tu;
    }

    public Thuetn tu(Integer tu) {
        this.setTu(tu);
        return this;
    }

    public void setTu(Integer tu) {
        this.tu = tu;
    }

    public Integer getDen() {
        return this.den;
    }

    public Thuetn den(Integer den) {
        this.setDen(den);
        return this;
    }

    public void setDen(Integer den) {
        this.den = den;
    }

    public Float getThuesuat() {
        return this.thuesuat;
    }

    public Thuetn thuesuat(Float thuesuat) {
        this.setThuesuat(thuesuat);
        return this;
    }

    public void setThuesuat(Float thuesuat) {
        this.thuesuat = thuesuat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thuetn)) {
            return false;
        }
        return getId() != null && getId().equals(((Thuetn) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thuetn{" +
            "id=" + getId() +
            ", bacthue='" + getBacthue() + "'" +
            ", tu=" + getTu() +
            ", den=" + getDen() +
            ", thuesuat=" + getThuesuat() +
            "}";
    }
}
