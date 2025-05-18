package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Phongban;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Phongban entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhongbanRepository extends JpaRepository<Phongban, Long> {}
