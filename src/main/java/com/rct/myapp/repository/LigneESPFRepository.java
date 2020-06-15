package com.rct.myapp.repository;

import com.rct.myapp.domain.LigneESPF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LigneESPF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneESPFRepository extends JpaRepository<LigneESPF, Long> {

}
