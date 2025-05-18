package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Chucvu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Chucvu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChucvuRepository extends JpaRepository<Chucvu, Long> {}
