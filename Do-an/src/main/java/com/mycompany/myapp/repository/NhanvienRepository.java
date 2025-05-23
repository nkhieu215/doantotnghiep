package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Nhanvien;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nhanvien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhanvienRepository extends JpaRepository<Nhanvien, Long> {}
