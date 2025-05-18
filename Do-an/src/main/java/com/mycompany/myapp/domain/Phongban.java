package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Phongban.
 */
@Entity
@Table(name = "phongban")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Phongban implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mapb")
    private String mapb;

    @Column(name = "tenpb")
    private String tenpb;

    @Column(name = "sdt")
    private Integer sdt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phongban")
    @JsonIgnoreProperties(value = { "chucvu", "phongban", "bangchamcongs", "tanggiamtls", "thamsotl" }, allowSetters = true)
    private Set<Nhanvien> nhanviens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Phongban id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMapb() {
        return this.mapb;
    }

    public Phongban mapb(String mapb) {
        this.setMapb(mapb);
        return this;
    }

    public void setMapb(String mapb) {
        this.mapb = mapb;
    }

    public String getTenpb() {
        return this.tenpb;
    }

    public Phongban tenpb(String tenpb) {
        this.setTenpb(tenpb);
        return this;
    }

    public void setTenpb(String tenpb) {
        this.tenpb = tenpb;
    }

    public Integer getSdt() {
        return this.sdt;
    }

    public Phongban sdt(Integer sdt) {
        this.setSdt(sdt);
        return this;
    }

    public void setSdt(Integer sdt) {
        this.sdt = sdt;
    }

    public Set<Nhanvien> getNhanviens() {
        return this.nhanviens;
    }

    public void setNhanviens(Set<Nhanvien> nhanviens) {
        if (this.nhanviens != null) {
            this.nhanviens.forEach(i -> i.setPhongban(null));
        }
        if (nhanviens != null) {
            nhanviens.forEach(i -> i.setPhongban(this));
        }
        this.nhanviens = nhanviens;
    }

    public Phongban nhanviens(Set<Nhanvien> nhanviens) {
        this.setNhanviens(nhanviens);
        return this;
    }

    public Phongban addNhanvien(Nhanvien nhanvien) {
        this.nhanviens.add(nhanvien);
        nhanvien.setPhongban(this);
        return this;
    }

    public Phongban removeNhanvien(Nhanvien nhanvien) {
        this.nhanviens.remove(nhanvien);
        nhanvien.setPhongban(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phongban)) {
            return false;
        }
        return getId() != null && getId().equals(((Phongban) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phongban{" +
            "id=" + getId() +
            ", mapb='" + getMapb() + "'" +
            ", tenpb='" + getTenpb() + "'" +
            ", sdt=" + getSdt() +
            "}";
    }
}
