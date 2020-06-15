package com.rct.myapp.repository;

import com.rct.myapp.domain.PivotSociete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PivotSociete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PivotSocieteRepository extends JpaRepository<PivotSociete, Long> {

}
