package com.rct.myapp.repository;

import com.rct.myapp.domain.Champs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Champs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChampsRepository extends JpaRepository<Champs, Long> {

}
