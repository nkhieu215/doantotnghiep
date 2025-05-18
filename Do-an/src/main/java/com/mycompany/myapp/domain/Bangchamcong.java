package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bangchamcong.
 */
@Entity
@Table(name = "bangchamcong")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bangchamcong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ncdilam")
    private Integer ncdilam;

    @Column(name = "thangcc")
    private String thangcc;

    @Column(name = "nclephep")
    private Integer nclephep;

    @Column(name = "xeploai")
    private String xeploai;

    @Column(name = "ngayththuong")
    private Integer ngayththuong;

    @Column(name = "ngaythle")
    private Integer ngaythle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "chucvu", "phongban", "bangchamcongs", "tanggiamtls", "thamsotl" }, allowSetters = true)
    private Nhanvien nhanvien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bangchamcong id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNcdilam() {
        return this.ncdilam;
    }

    public Bangchamcong ncdilam(Integer ncdilam) {
        this.setNcdilam(ncdilam);
        return this;
    }

    public void setNcdilam(Integer ncdilam) {
        this.ncdilam = ncdilam;
    }

    public String getThangcc() {
        return this.thangcc;
    }

    public Bangchamcong thangcc(String thangcc) {
        this.setThangcc(thangcc);
        return this;
    }

    public void setThangcc(String thangcc) {
        this.thangcc = thangcc;
    }

    public Integer getNclephep() {
        return this.nclephep;
    }

    public Bangchamcong nclephep(Integer nclephep) {
        this.setNclephep(nclephep);
        return this;
    }

    public void setNclephep(Integer nclephep) {
        this.nclephep = nclephep;
    }

    public String getXeploai() {
        return this.xeploai;
    }

    public Bangchamcong xeploai(String xeploai) {
        this.setXeploai(xeploai);
        return this;
    }

    public void setXeploai(String xeploai) {
        this.xeploai = xeploai;
    }

    public Integer getNgayththuong() {
        return this.ngayththuong;
    }

    public Bangchamcong ngayththuong(Integer ngayththuong) {
        this.setNgayththuong(ngayththuong);
        return this;
    }

    public void setNgayththuong(Integer ngayththuong) {
        this.ngayththuong = ngayththuong;
    }

    public Integer getNgaythle() {
        return this.ngaythle;
    }

    public Bangchamcong ngaythle(Integer ngaythle) {
        this.setNgaythle(ngaythle);
        return this;
    }

    public void setNgaythle(Integer ngaythle) {
        this.ngaythle = ngaythle;
    }

    public Nhanvien getNhanvien() {
        return this.nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public Bangchamcong nhanvien(Nhanvien nhanvien) {
        this.setNhanvien(nhanvien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bangchamcong)) {
            return false;
        }
        return getId() != null && getId().equals(((Bangchamcong) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bangchamcong{" +
            "id=" + getId() +
            ", ncdilam=" + getNcdilam() +
            ", thangcc='" + getThangcc() + "'" +
            ", nclephep=" + getNclephep() +
            ", xeploai='" + getXeploai() + "'" +
            ", ngayththuong=" + getNgayththuong() +
            ", ngaythle=" + getNgaythle() +
            "}";
    }
}
