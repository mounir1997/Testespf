package com.rct.myapp.repository;

import com.rct.myapp.domain.ValeurChamp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ValeurChamp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValeurChampRepository extends JpaRepository<ValeurChamp, Long> {

}
