package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Nhanvien.
 */
@Entity
@Table(name = "nhanvien")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nhanvien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "manv")
    private String manv;

    @Column(name = "hoten")
    private String hoten;

    @Column(name = "ngaysinh")
    private ZonedDateTime ngaysinh;

    @Column(name = "gioitinh")
    private String gioitinh;

    @Column(name = "quequan")
    private String quequan;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "hsluong")
    private Float hsluong;

    @Column(name = "msthue")
    private Integer msthue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "nhanviens" }, allowSetters = true)
    private Chucvu chucvu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "nhanviens" }, allowSetters = true)
    private Phongban phongban;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nhanvien")
    @JsonIgnoreProperties(value = { "nhanvien" }, allowSetters = true)
    private Set<Bangchamcong> bangchamcongs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nhanvien")
    @JsonIgnoreProperties(value = { "nhanvien" }, allowSetters = true)
    private Set<Tanggiamtl> tanggiamtls = new HashSet<>();

    @JsonIgnoreProperties(value = { "nhanvien" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nhanvien")
    private Thamsotl thamsotl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nhanvien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManv() {
        return this.manv;
    }

    public Nhanvien manv(String manv) {
        this.setManv(manv);
        return this;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getHoten() {
        return this.hoten;
    }

    public Nhanvien hoten(String hoten) {
        this.setHoten(hoten);
        return this;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public ZonedDateTime getNgaysinh() {
        return this.ngaysinh;
    }

    public Nhanvien ngaysinh(ZonedDateTime ngaysinh) {
        this.setNgaysinh(ngaysinh);
        return this;
    }

    public void setNgaysinh(ZonedDateTime ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return this.gioitinh;
    }

    public Nhanvien gioitinh(String gioitinh) {
        this.setGioitinh(gioitinh);
        return this;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getQuequan() {
        return this.quequan;
    }

    public Nhanvien quequan(String quequan) {
        this.setQuequan(quequan);
        return this;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getDiachi() {
        return this.diachi;
    }

    public Nhanvien diachi(String diachi) {
        this.setDiachi(diachi);
        return this;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Float getHsluong() {
        return this.hsluong;
    }

    public Nhanvien hsluong(Float hsluong) {
        this.setHsluong(hsluong);
        return this;
    }

    public void setHsluong(Float hsluong) {
        this.hsluong = hsluong;
    }

    public Integer getMsthue() {
        return this.msthue;
    }

    public Nhanvien msthue(Integer msthue) {
        this.setMsthue(msthue);
        return this;
    }

    public void setMsthue(Integer msthue) {
        this.msthue = msthue;
    }

    public Chucvu getChucvu() {
        return this.chucvu;
    }

    public void setChucvu(Chucvu chucvu) {
        this.chucvu = chucvu;
    }

    public Nhanvien chucvu(Chucvu chucvu) {
        this.setChucvu(chucvu);
        return this;
    }

    public Phongban getPhongban() {
        return this.phongban;
    }

    public void setPhongban(Phongban phongban) {
        this.phongban = phongban;
    }

    public Nhanvien phongban(Phongban phongban) {
        this.setPhongban(phongban);
        return this;
    }

    public Set<Bangchamcong> getBangchamcongs() {
        return this.bangchamcongs;
    }

    public void setBangchamcongs(Set<Bangchamcong> bangchamcongs) {
        if (this.bangchamcongs != null) {
            this.bangchamcongs.forEach(i -> i.setNhanvien(null));
        }
        if (bangchamcongs != null) {
            bangchamcongs.forEach(i -> i.setNhanvien(this));
        }
        this.bangchamcongs = bangchamcongs;
    }

    public Nhanvien bangchamcongs(Set<Bangchamcong> bangchamcongs) {
        this.setBangchamcongs(bangchamcongs);
        return this;
    }

    public Nhanvien addBangchamcong(Bangchamcong bangchamcong) {
        this.bangchamcongs.add(bangchamcong);
        bangchamcong.setNhanvien(this);
        return this;
    }

    public Nhanvien removeBangchamcong(Bangchamcong bangchamcong) {
        this.bangchamcongs.remove(bangchamcong);
        bangchamcong.setNhanvien(null);
        return this;
    }

    public Set<Tanggiamtl> getTanggiamtls() {
        return this.tanggiamtls;
    }

    public void setTanggiamtls(Set<Tanggiamtl> tanggiamtls) {
        if (this.tanggiamtls != null) {
            this.tanggiamtls.forEach(i -> i.setNhanvien(null));
        }
        if (tanggiamtls != null) {
            tanggiamtls.forEach(i -> i.setNhanvien(this));
        }
        this.tanggiamtls = tanggiamtls;
    }

    public Nhanvien tanggiamtls(Set<Tanggiamtl> tanggiamtls) {
        this.setTanggiamtls(tanggiamtls);
        return this;
    }

    public Nhanvien addTanggiamtl(Tanggiamtl tanggiamtl) {
        this.tanggiamtls.add(tanggiamtl);
        tanggiamtl.setNhanvien(this);
        return this;
    }

    public Nhanvien removeTanggiamtl(Tanggiamtl tanggiamtl) {
        this.tanggiamtls.remove(tanggiamtl);
        tanggiamtl.setNhanvien(null);
        return this;
    }

    public Thamsotl getThamsotl() {
        return this.thamsotl;
    }

    public void setThamsotl(Thamsotl thamsotl) {
        if (this.thamsotl != null) {
            this.thamsotl.setNhanvien(null);
        }
        if (thamsotl != null) {
            thamsotl.setNhanvien(this);
        }
        this.thamsotl = thamsotl;
    }

    public Nhanvien thamsotl(Thamsotl thamsotl) {
        this.setThamsotl(thamsotl);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nhanvien)) {
            return false;
        }
        return getId() != null && getId().equals(((Nhanvien) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nhanvien{" +
            "id=" + getId() +
            ", manv='" + getManv() + "'" +
            ", hoten='" + getHoten() + "'" +
            ", ngaysinh='" + getNgaysinh() + "'" +
            ", gioitinh='" + getGioitinh() + "'" +
            ", quequan='" + getQuequan() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", hsluong=" + getHsluong() +
            ", msthue=" + getMsthue() +
            "}";
    }
}
