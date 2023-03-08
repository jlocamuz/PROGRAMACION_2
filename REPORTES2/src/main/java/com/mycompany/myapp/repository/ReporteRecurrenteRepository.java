package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ReporteRecurrente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReporteRecurrente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReporteRecurrenteRepository extends JpaRepository<ReporteRecurrente, Long> {}
