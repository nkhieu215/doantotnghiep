package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Tanggiamtl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tanggiamtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TanggiamtlRepository extends JpaRepository<Tanggiamtl, Long> {}
