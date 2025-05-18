package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Thuetn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Thuetn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThuetnRepository extends JpaRepository<Thuetn, Long> {}
