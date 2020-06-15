package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.PivotModel;
import com.rct.myapp.repository.PivotModelRepository;
import com.rct.myapp.web.rest.errors.BadRequestAlertException;
import com.rct.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PivotModel.
 */
@RestController
@RequestMapping("/api")
public class PivotModelResource {

    private final Logger log = LoggerFactory.getLogger(PivotModelResource.class);

    private static final String ENTITY_NAME = "pivotModel";

    private final PivotModelRepository pivotModelRepository;

    public PivotModelResource(PivotModelRepository pivotModelRepository) {
        this.pivotModelRepository = pivotModelRepository;
    }

    /**
     * POST  /pivot-models : Create a new pivotModel.
     *
     * @param pivotModel the pivotModel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pivotModel, or with status 400 (Bad Request) if the pivotModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pivot-models")
    @Timed
    public ResponseEntity<PivotModel> createPivotModel(@Valid @RequestBody PivotModel pivotModel) throws URISyntaxException {
        log.debug("REST request to save PivotModel : {}", pivotModel);
        if (pivotModel.getId() != null) {
            throw new BadRequestAlertException("A new pivotModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PivotModel result = pivotModelRepository.save(pivotModel);
        return ResponseEntity.created(new URI("/api/pivot-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pivot-models : Updates an existing pivotModel.
     *
     * @param pivotModel the pivotModel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pivotModel,
     * or with status 400 (Bad Request) if the pivotModel is not valid,
     * or with status 500 (Internal Server Error) if the pivotModel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pivot-models")
    @Timed
    public ResponseEntity<PivotModel> updatePivotModel(@Valid @RequestBody PivotModel pivotModel) throws URISyntaxException {
        log.debug("REST request to update PivotModel : {}", pivotModel);
        if (pivotModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PivotModel result = pivotModelRepository.save(pivotModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pivotModel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pivot-models : get all the pivotModels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pivotModels in body
     */
    @GetMapping("/pivot-models")
    @Timed
    public List<PivotModel> getAllPivotModels() {
        log.debug("REST request to get all PivotModels");
        return pivotModelRepository.findAll();
    }

    /**
     * GET  /pivot-models/:id : get the "id" pivotModel.
     *
     * @param id the id of the pivotModel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pivotModel, or with status 404 (Not Found)
     */
    @GetMapping("/pivot-models/{id}")
    @Timed
    public ResponseEntity<PivotModel> getPivotModel(@PathVariable Long id) {
        log.debug("REST request to get PivotModel : {}", id);
        Optional<PivotModel> pivotModel = pivotModelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pivotModel);
    }

    /**
     * DELETE  /pivot-models/:id : delete the "id" pivotModel.
     *
     * @param id the id of the pivotModel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pivot-models/{id}")
    @Timed
    public ResponseEntity<Void> deletePivotModel(@PathVariable Long id) {
        log.debug("REST request to delete PivotModel : {}", id);

        pivotModelRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
