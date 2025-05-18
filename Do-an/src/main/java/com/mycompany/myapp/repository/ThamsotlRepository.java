package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Thamsotl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Thamsotl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThamsotlRepository extends JpaRepository<Thamsotl, Long> {}
