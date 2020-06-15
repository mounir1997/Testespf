package com.rct.myapp.repository;

import com.rct.myapp.domain.PivotModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PivotModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PivotModelRepository extends JpaRepository<PivotModel, Long> {

}
