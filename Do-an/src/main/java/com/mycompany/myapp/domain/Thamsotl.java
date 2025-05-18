package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Thamsotl.
 */
@Entity
@Table(name = "thamsotl")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Thamsotl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "thangnam")
    private String thangnam;

    @Column(name = "ncchuan")
    private Integer ncchuan;

    @Column(name = "giocchuan")
    private Integer giocchuan;

    @Column(name = "hsgioth")
    private Float hsgioth;

    @Column(name = "hsgiole")
    private Float hsgiole;

    @Column(name = "pcan")
    private Integer pcan;

    @Column(name = "tlbhxh")
    private Float tlbhxh;

    @Column(name = "tlbhyt")
    private Float tlbhyt;

    @Column(name = "tlbhtn")
    private String tlbhtn;

    @Column(name = "tlkpcd")
    private Float tlkpcd;

    @JsonIgnoreProperties(value = { "chucvu", "phongban", "bangchamcongs", "tanggiamtls", "thamsotl" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Nhanvien nhanvien;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Thamsotl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThangnam() {
        return this.thangnam;
    }

    public Thamsotl thangnam(String thangnam) {
        this.setThangnam(thangnam);
        return this;
    }

    public void setThangnam(String thangnam) {
        this.thangnam = thangnam;
    }

    public Integer getNcchuan() {
        return this.ncchuan;
    }

    public Thamsotl ncchuan(Integer ncchuan) {
        this.setNcchuan(ncchuan);
        return this;
    }

    public void setNcchuan(Integer ncchuan) {
        this.ncchuan = ncchuan;
    }

    public Integer getGiocchuan() {
        return this.giocchuan;
    }

    public Thamsotl giocchuan(Integer giocchuan) {
        this.setGiocchuan(giocchuan);
        return this;
    }

    public void setGiocchuan(Integer giocchuan) {
        this.giocchuan = giocchuan;
    }

    public Float getHsgioth() {
        return this.hsgioth;
    }

    public Thamsotl hsgioth(Float hsgioth) {
        this.setHsgioth(hsgioth);
        return this;
    }

    public void setHsgioth(Float hsgioth) {
        this.hsgioth = hsgioth;
    }

    public Float getHsgiole() {
        return this.hsgiole;
    }

    public Thamsotl hsgiole(Float hsgiole) {
        this.setHsgiole(hsgiole);
        return this;
    }

    public void setHsgiole(Float hsgiole) {
        this.hsgiole = hsgiole;
    }

    public Integer getPcan() {
        return this.pcan;
    }

    public Thamsotl pcan(Integer pcan) {
        this.setPcan(pcan);
        return this;
    }

    public void setPcan(Integer pcan) {
        this.pcan = pcan;
    }

    public Float getTlbhxh() {
        return this.tlbhxh;
    }

    public Thamsotl tlbhxh(Float tlbhxh) {
        this.setTlbhxh(tlbhxh);
        return this;
    }

    public void setTlbhxh(Float tlbhxh) {
        this.tlbhxh = tlbhxh;
    }

    public Float getTlbhyt() {
        return this.tlbhyt;
    }

    public Thamsotl tlbhyt(Float tlbhyt) {
        this.setTlbhyt(tlbhyt);
        return this;
    }

    public void setTlbhyt(Float tlbhyt) {
        this.tlbhyt = tlbhyt;
    }

    public String getTlbhtn() {
        return this.tlbhtn;
    }

    public Thamsotl tlbhtn(String tlbhtn) {
        this.setTlbhtn(tlbhtn);
        return this;
    }

    public void setTlbhtn(String tlbhtn) {
        this.tlbhtn = tlbhtn;
    }

    public Float getTlkpcd() {
        return this.tlkpcd;
    }

    public Thamsotl tlkpcd(Float tlkpcd) {
        this.setTlkpcd(tlkpcd);
        return this;
    }

    public void setTlkpcd(Float tlkpcd) {
        this.tlkpcd = tlkpcd;
    }

    public Nhanvien getNhanvien() {
        return this.nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    public Thamsotl nhanvien(Nhanvien nhanvien) {
        this.setNhanvien(nhanvien);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thamsotl)) {
            return false;
        }
        return getId() != null && getId().equals(((Thamsotl) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thamsotl{" +
            "id=" + getId() +
            ", thangnam='" + getThangnam() + "'" +
            ", ncchuan=" + getNcchuan() +
            ", giocchuan=" + getGiocchuan() +
            ", hsgioth=" + getHsgioth() +
            ", hsgiole=" + getHsgiole() +
            ", pcan=" + getPcan() +
            ", tlbhxh=" + getTlbhxh() +
            ", tlbhyt=" + getTlbhyt() +
            ", tlbhtn='" + getTlbhtn() + "'" +
            ", tlkpcd=" + getTlkpcd() +
            "}";
    }
}
