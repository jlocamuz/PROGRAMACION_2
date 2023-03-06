package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ReporteHistorico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReporteHistorico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReporteHistoricoRepository extends JpaRepository<ReporteHistorico, Long> {}
