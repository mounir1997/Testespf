package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.Valeurs;
import com.rct.myapp.repository.ValeursRepository;
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
 * REST controller for managing Valeurs.
 */
@RestController
@RequestMapping("/api")
public class ValeursResource {

    private final Logger log = LoggerFactory.getLogger(ValeursResource.class);

    private static final String ENTITY_NAME = "valeurs";

    private final ValeursRepository valeursRepository;

    public ValeursResource(ValeursRepository valeursRepository) {
        this.valeursRepository = valeursRepository;
    }

    /**
     * POST  /valeurs : Create a new valeurs.
     *
     * @param valeurs the valeurs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valeurs, or with status 400 (Bad Request) if the valeurs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valeurs")
    @Timed
    public ResponseEntity<Valeurs> createValeurs(@Valid @RequestBody Valeurs valeurs) throws URISyntaxException {
        log.debug("REST request to save Valeurs : {}", valeurs);
        if (valeurs.getId() != null) {
            throw new BadRequestAlertException("A new valeurs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Valeurs result = valeursRepository.save(valeurs);
        return ResponseEntity.created(new URI("/api/valeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valeurs : Updates an existing valeurs.
     *
     * @param valeurs the valeurs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valeurs,
     * or with status 400 (Bad Request) if the valeurs is not valid,
     * or with status 500 (Internal Server Error) if the valeurs couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valeurs")
    @Timed
    public ResponseEntity<Valeurs> updateValeurs(@Valid @RequestBody Valeurs valeurs) throws URISyntaxException {
        log.debug("REST request to update Valeurs : {}", valeurs);
        if (valeurs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Valeurs result = valeursRepository.save(valeurs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valeurs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valeurs : get all the valeurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valeurs in body
     */
    @GetMapping("/valeurs")
    @Timed
    public List<Valeurs> getAllValeurs() {
        log.debug("REST request to get all Valeurs");
        return valeursRepository.findAll();
    }

    /**
     * GET  /valeurs/:id : get the "id" valeurs.
     *
     * @param id the id of the valeurs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valeurs, or with status 404 (Not Found)
     */
    @GetMapping("/valeurs/{id}")
    @Timed
    public ResponseEntity<Valeurs> getValeurs(@PathVariable Long id) {
        log.debug("REST request to get Valeurs : {}", id);
        Optional<Valeurs> valeurs = valeursRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(valeurs);
    }

    /**
     * DELETE  /valeurs/:id : delete the "id" valeurs.
     *
     * @param id the id of the valeurs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valeurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteValeurs(@PathVariable Long id) {
        log.debug("REST request to delete Valeurs : {}", id);

        valeursRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
