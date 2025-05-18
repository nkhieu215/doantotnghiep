package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Bangchamcong;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bangchamcong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BangchamcongRepository extends JpaRepository<Bangchamcong, Long> {}
