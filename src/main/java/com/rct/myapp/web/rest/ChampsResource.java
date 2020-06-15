package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.Champs;
import com.rct.myapp.repository.ChampsRepository;
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
 * REST controller for managing Champs.
 */
@RestController
@RequestMapping("/api")
public class ChampsResource {

    private final Logger log = LoggerFactory.getLogger(ChampsResource.class);

    private static final String ENTITY_NAME = "champs";

    private final ChampsRepository champsRepository;

    public ChampsResource(ChampsRepository champsRepository) {
        this.champsRepository = champsRepository;
    }

    /**
     * POST  /champs : Create a new champs.
     *
     * @param champs the champs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new champs, or with status 400 (Bad Request) if the champs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/champs")
    @Timed
    public ResponseEntity<Champs> createChamps(@Valid @RequestBody Champs champs) throws URISyntaxException {
        log.debug("REST request to save Champs : {}", champs);
        if (champs.getId() != null) {
            throw new BadRequestAlertException("A new champs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Champs result = champsRepository.save(champs);
        return ResponseEntity.created(new URI("/api/champs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /champs : Updates an existing champs.
     *
     * @param champs the champs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated champs,
     * or with status 400 (Bad Request) if the champs is not valid,
     * or with status 500 (Internal Server Error) if the champs couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/champs")
    @Timed
    public ResponseEntity<Champs> updateChamps(@Valid @RequestBody Champs champs) throws URISyntaxException {
        log.debug("REST request to update Champs : {}", champs);
        if (champs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Champs result = champsRepository.save(champs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, champs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /champs : get all the champs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of champs in body
     */
    @GetMapping("/champs")
    @Timed
    public List<Champs> getAllChamps() {
        log.debug("REST request to get all Champs");
        return champsRepository.findAll();
    }

    /**
     * GET  /champs/:id : get the "id" champs.
     *
     * @param id the id of the champs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the champs, or with status 404 (Not Found)
     */
    @GetMapping("/champs/{id}")
    @Timed
    public ResponseEntity<Champs> getChamps(@PathVariable Long id) {
        log.debug("REST request to get Champs : {}", id);
        Optional<Champs> champs = champsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(champs);
    }

    /**
     * DELETE  /champs/:id : delete the "id" champs.
     *
     * @param id the id of the champs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/champs/{id}")
    @Timed
    public ResponseEntity<Void> deleteChamps(@PathVariable Long id) {
        log.debug("REST request to delete Champs : {}", id);

        champsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
