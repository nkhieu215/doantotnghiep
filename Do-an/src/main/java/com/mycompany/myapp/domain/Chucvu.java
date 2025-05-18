package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Chucvu.
 */
@Entity
@Table(name = "chucvu")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Chucvu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "macv")
    private String macv;

    @Column(name = "tencv")
    private String tencv;

    @Column(name = "hcpccv")
    private Integer hcpccv;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chucvu")
    @JsonIgnoreProperties(value = { "chucvu", "phongban", "bangchamcongs", "tanggiamtls", "thamsotl" }, allowSetters = true)
    private Set<Nhanvien> nhanviens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Chucvu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMacv() {
        return this.macv;
    }

    public Chucvu macv(String macv) {
        this.setMacv(macv);
        return this;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getTencv() {
        return this.tencv;
    }

    public Chucvu tencv(String tencv) {
        this.setTencv(tencv);
        return this;
    }

    public void setTencv(String tencv) {
        this.tencv = tencv;
    }

    public Integer getHcpccv() {
        return this.hcpccv;
    }

    public Chucvu hcpccv(Integer hcpccv) {
        this.setHcpccv(hcpccv);
        return this;
    }

    public void setHcpccv(Integer hcpccv) {
        this.hcpccv = hcpccv;
    }

    public Set<Nhanvien> getNhanviens() {
        return this.nhanviens;
    }

    public void setNhanviens(Set<Nhanvien> nhanviens) {
        if (this.nhanviens != null) {
            this.nhanviens.forEach(i -> i.setChucvu(null));
        }
        if (nhanviens != null) {
            nhanviens.forEach(i -> i.setChucvu(this));
        }
        this.nhanviens = nhanviens;
    }

    public Chucvu nhanviens(Set<Nhanvien> nhanviens) {
        this.setNhanviens(nhanviens);
        return this;
    }

    public Chucvu addNhanvien(Nhanvien nhanvien) {
        this.nhanviens.add(nhanvien);
        nhanvien.setChucvu(this);
        return this;
    }

    public Chucvu removeNhanvien(Nhanvien nhanvien) {
        this.nhanviens.remove(nhanvien);
        nhanvien.setChucvu(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chucvu)) {
            return false;
        }
        return getId() != null && getId().equals(((Chucvu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chucvu{" +
            "id=" + getId() +
            ", macv='" + getMacv() + "'" +
            ", tencv='" + getTencv() + "'" +
            ", hcpccv=" + getHcpccv() +
            "}";
    }
}
