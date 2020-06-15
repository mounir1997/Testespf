package com.rct.myapp.repository;

import com.rct.myapp.domain.ESPF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ESPF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ESPFRepository extends JpaRepository<ESPF, Long> {

}
