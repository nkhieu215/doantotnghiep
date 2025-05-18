package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Tanggiamtl.
 */
@Entity
@Table(name = "tanggiamtl")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tanggiamtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ngaythang")
    private String ngaythang;

    @Column(name = "tkn")
    private Integer tkn;

    @Column(name = "tkc")
    private Integer tkc;

    @Column(name = "sotien")
    private Float sotien;

    @Column(name = "diengiai")
    private String diengiai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "chucvu", "phongban", "bangchamcongs", "tanggiamtls", "thamsotl" }, allowSetters = true)
    private Nhanvien nhanvien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tanggiamtl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNgaythang() {
        return this.ngaythang;
    }

    public Tanggiamtl ngaythang(String ngaythang) {
        this.setNgaythang(ngaythang);
        return this;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }

    public Integer getTkn() {
        return this.tkn;
    }

    public Tanggiamtl tkn(Integer tkn) {
        this.setTkn(tkn);
        return this;
    }

    public void setTkn(Integer tkn) {
        this.tkn = tkn;
    }

    public Integer getTkc() {
        return this.tkc;
    }

    public Tanggiamtl tkc(Integer tkc) {
        this.setTkc(tkc);
        return this;
    }

    public void setTkc(Integer tkc) {
        this.tkc = tkc;
    }

    public Float getSotien() {
        return this.sotien;
    }

    public Tanggiamtl sotien(Float sotien) {
        this.setSotien(sotien);
        return this;
    }

    public void setSotien(Float sotien) {
        this.sotien = sotien;
    }

    public String getDiengiai() {
        return this.diengiai;
    }

    public Tanggiamtl diengiai(String diengiai) {
        this.setDiengiai(diengiai);
        return this;
    }

    public void setDiengiai(String diengiai) {
        this.diengiai = diengiai;
    }

    public Nhanvien getNhanvien() {
        return this.nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public Tanggiamtl nhanvien(Nhanvien nhanvien) {
        this.setNhanvien(nhanvien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tanggiamtl)) {
            return false;
        }
        return getId() != null && getId().equals(((Tanggiamtl) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tanggiamtl{" +
            "id=" + getId() +
            ", ngaythang='" + getNgaythang() + "'" +
            ", tkn=" + getTkn() +
            ", tkc=" + getTkc() +
            ", sotien=" + getSotien() +
            ", diengiai='" + getDiengiai() + "'" +
            "}";
    }
}
