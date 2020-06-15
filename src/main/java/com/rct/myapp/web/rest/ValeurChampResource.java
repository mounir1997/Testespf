package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.ValeurChamp;
import com.rct.myapp.repository.ValeurChampRepository;
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
 * REST controller for managing ValeurChamp.
 */
@RestController
@RequestMapping("/api")
public class ValeurChampResource {

    private final Logger log = LoggerFactory.getLogger(ValeurChampResource.class);

    private static final String ENTITY_NAME = "valeurChamp";

    private final ValeurChampRepository valeurChampRepository;

    public ValeurChampResource(ValeurChampRepository valeurChampRepository) {
        this.valeurChampRepository = valeurChampRepository;
    }

    /**
     * POST  /valeur-champs : Create a new valeurChamp.
     *
     * @param valeurChamp the valeurChamp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valeurChamp, or with status 400 (Bad Request) if the valeurChamp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valeur-champs")
    @Timed
    public ResponseEntity<ValeurChamp> createValeurChamp(@Valid @RequestBody ValeurChamp valeurChamp) throws URISyntaxException {
        log.debug("REST request to save ValeurChamp : {}", valeurChamp);
        if (valeurChamp.getId() != null) {
            throw new BadRequestAlertException("A new valeurChamp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValeurChamp result = valeurChampRepository.save(valeurChamp);
        return ResponseEntity.created(new URI("/api/valeur-champs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valeur-champs : Updates an existing valeurChamp.
     *
     * @param valeurChamp the valeurChamp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valeurChamp,
     * or with status 400 (Bad Request) if the valeurChamp is not valid,
     * or with status 500 (Internal Server Error) if the valeurChamp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valeur-champs")
    @Timed
    public ResponseEntity<ValeurChamp> updateValeurChamp(@Valid @RequestBody ValeurChamp valeurChamp) throws URISyntaxException {
        log.debug("REST request to update ValeurChamp : {}", valeurChamp);
        if (valeurChamp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ValeurChamp result = valeurChampRepository.save(valeurChamp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valeurChamp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valeur-champs : get all the valeurChamps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valeurChamps in body
     */
    @GetMapping("/valeur-champs")
    @Timed
    public List<ValeurChamp> getAllValeurChamps() {
        log.debug("REST request to get all ValeurChamps");
        return valeurChampRepository.findAll();
    }

    /**
     * GET  /valeur-champs/:id : get the "id" valeurChamp.
     *
     * @param id the id of the valeurChamp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valeurChamp, or with status 404 (Not Found)
     */
    @GetMapping("/valeur-champs/{id}")
    @Timed
    public ResponseEntity<ValeurChamp> getValeurChamp(@PathVariable Long id) {
        log.debug("REST request to get ValeurChamp : {}", id);
        Optional<ValeurChamp> valeurChamp = valeurChampRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(valeurChamp);
    }

    /**
     * DELETE  /valeur-champs/:id : delete the "id" valeurChamp.
     *
     * @param id the id of the valeurChamp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valeur-champs/{id}")
    @Timed
    public ResponseEntity<Void> deleteValeurChamp(@PathVariable Long id) {
        log.debug("REST request to delete ValeurChamp : {}", id);

        valeurChampRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
