package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.LigneESPF;
import com.rct.myapp.repository.LigneESPFRepository;
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
 * REST controller for managing LigneESPF.
 */
@RestController
@RequestMapping("/api")
public class LigneESPFResource {

    private final Logger log = LoggerFactory.getLogger(LigneESPFResource.class);

    private static final String ENTITY_NAME = "ligneESPF";

    private final LigneESPFRepository ligneESPFRepository;

    public LigneESPFResource(LigneESPFRepository ligneESPFRepository) {
        this.ligneESPFRepository = ligneESPFRepository;
    }

    /**
     * POST  /ligne-espfs : Create a new ligneESPF.
     *
     * @param ligneESPF the ligneESPF to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ligneESPF, or with status 400 (Bad Request) if the ligneESPF has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ligne-espfs")
    @Timed
    public ResponseEntity<LigneESPF> createLigneESPF(@Valid @RequestBody LigneESPF ligneESPF) throws URISyntaxException {
        log.debug("REST request to save LigneESPF : {}", ligneESPF);
        if (ligneESPF.getId() != null) {
            throw new BadRequestAlertException("A new ligneESPF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneESPF result = ligneESPFRepository.save(ligneESPF);
        return ResponseEntity.created(new URI("/api/ligne-espfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ligne-espfs : Updates an existing ligneESPF.
     *
     * @param ligneESPF the ligneESPF to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ligneESPF,
     * or with status 400 (Bad Request) if the ligneESPF is not valid,
     * or with status 500 (Internal Server Error) if the ligneESPF couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ligne-espfs")
    @Timed
    public ResponseEntity<LigneESPF> updateLigneESPF(@Valid @RequestBody LigneESPF ligneESPF) throws URISyntaxException {
        log.debug("REST request to update LigneESPF : {}", ligneESPF);
        if (ligneESPF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LigneESPF result = ligneESPFRepository.save(ligneESPF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ligneESPF.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ligne-espfs : get all the ligneESPFS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ligneESPFS in body
     */
    @GetMapping("/ligne-espfs")
    @Timed
    public List<LigneESPF> getAllLigneESPFS() {
        log.debug("REST request to get all LigneESPFS");
        return ligneESPFRepository.findAll();
    }

    /**
     * GET  /ligne-espfs/:id : get the "id" ligneESPF.
     *
     * @param id the id of the ligneESPF to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ligneESPF, or with status 404 (Not Found)
     */
    @GetMapping("/ligne-espfs/{id}")
    @Timed
    public ResponseEntity<LigneESPF> getLigneESPF(@PathVariable Long id) {
        log.debug("REST request to get LigneESPF : {}", id);
        Optional<LigneESPF> ligneESPF = ligneESPFRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ligneESPF);
    }

    /**
     * DELETE  /ligne-espfs/:id : delete the "id" ligneESPF.
     *
     * @param id the id of the ligneESPF to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ligne-espfs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLigneESPF(@PathVariable Long id) {
        log.debug("REST request to delete LigneESPF : {}", id);

        ligneESPFRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
