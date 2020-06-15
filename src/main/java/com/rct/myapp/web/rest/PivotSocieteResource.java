package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.PivotSociete;
import com.rct.myapp.repository.PivotSocieteRepository;
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
 * REST controller for managing PivotSociete.
 */
@RestController
@RequestMapping("/api")
public class PivotSocieteResource {

    private final Logger log = LoggerFactory.getLogger(PivotSocieteResource.class);

    private static final String ENTITY_NAME = "pivotSociete";

    private final PivotSocieteRepository pivotSocieteRepository;

    public PivotSocieteResource(PivotSocieteRepository pivotSocieteRepository) {
        this.pivotSocieteRepository = pivotSocieteRepository;
    }

    /**
     * POST  /pivot-societes : Create a new pivotSociete.
     *
     * @param pivotSociete the pivotSociete to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pivotSociete, or with status 400 (Bad Request) if the pivotSociete has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pivot-societes")
    @Timed
    public ResponseEntity<PivotSociete> createPivotSociete(@Valid @RequestBody PivotSociete pivotSociete) throws URISyntaxException {
        log.debug("REST request to save PivotSociete : {}", pivotSociete);
        if (pivotSociete.getId() != null) {
            throw new BadRequestAlertException("A new pivotSociete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PivotSociete result = pivotSocieteRepository.save(pivotSociete);
        return ResponseEntity.created(new URI("/api/pivot-societes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pivot-societes : Updates an existing pivotSociete.
     *
     * @param pivotSociete the pivotSociete to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pivotSociete,
     * or with status 400 (Bad Request) if the pivotSociete is not valid,
     * or with status 500 (Internal Server Error) if the pivotSociete couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pivot-societes")
    @Timed
    public ResponseEntity<PivotSociete> updatePivotSociete(@Valid @RequestBody PivotSociete pivotSociete) throws URISyntaxException {
        log.debug("REST request to update PivotSociete : {}", pivotSociete);
        if (pivotSociete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PivotSociete result = pivotSocieteRepository.save(pivotSociete);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pivotSociete.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pivot-societes : get all the pivotSocietes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pivotSocietes in body
     */
    @GetMapping("/pivot-societes")
    @Timed
    public List<PivotSociete> getAllPivotSocietes() {
        log.debug("REST request to get all PivotSocietes");
        return pivotSocieteRepository.findAll();
    }

    /**
     * GET  /pivot-societes/:id : get the "id" pivotSociete.
     *
     * @param id the id of the pivotSociete to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pivotSociete, or with status 404 (Not Found)
     */
    @GetMapping("/pivot-societes/{id}")
    @Timed
    public ResponseEntity<PivotSociete> getPivotSociete(@PathVariable Long id) {
        log.debug("REST request to get PivotSociete : {}", id);
        Optional<PivotSociete> pivotSociete = pivotSocieteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pivotSociete);
    }

    /**
     * DELETE  /pivot-societes/:id : delete the "id" pivotSociete.
     *
     * @param id the id of the pivotSociete to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pivot-societes/{id}")
    @Timed
    public ResponseEntity<Void> deletePivotSociete(@PathVariable Long id) {
        log.debug("REST request to delete PivotSociete : {}", id);

        pivotSocieteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
