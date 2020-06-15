package com.rct.myapp.repository;

import com.rct.myapp.domain.Valeurs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Valeurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValeursRepository extends JpaRepository<Valeurs, Long> {

}
