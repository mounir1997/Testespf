package com.rct.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rct.myapp.domain.Attribut;
import com.rct.myapp.repository.AttributRepository;
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
 * REST controller for managing Attribut.
 */
@RestController
@RequestMapping("/api")
public class AttributResource {

    private final Logger log = LoggerFactory.getLogger(AttributResource.class);

    private static final String ENTITY_NAME = "attribut";

    private final AttributRepository attributRepository;

    public AttributResource(AttributRepository attributRepository) {
        this.attributRepository = attributRepository;
    }

    /**
     * POST  /attributs : Create a new attribut.
     *
     * @param attribut the attribut to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attribut, or with status 400 (Bad Request) if the attribut has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attributs")
    @Timed
    public ResponseEntity<Attribut> createAttribut(@Valid @RequestBody Attribut attribut) throws URISyntaxException {
        log.debug("REST request to save Attribut : {}", attribut);
        if (attribut.getId() != null) {
            throw new BadRequestAlertException("A new attribut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attribut result = attributRepository.save(attribut);
        return ResponseEntity.created(new URI("/api/attributs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attributs : Updates an existing attribut.
     *
     * @param attribut the attribut to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attribut,
     * or with status 400 (Bad Request) if the attribut is not valid,
     * or with status 500 (Internal Server Error) if the attribut couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attributs")
    @Timed
    public ResponseEntity<Attribut> updateAttribut(@Valid @RequestBody Attribut attribut) throws URISyntaxException {
        log.debug("REST request to update Attribut : {}", attribut);
        if (attribut.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Attribut result = attributRepository.save(attribut);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attribut.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attributs : get all the attributs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of attributs in body
     */
    @GetMapping("/attributs")
    @Timed
    public List<Attribut> getAllAttributs() {
        log.debug("REST request to get all Attributs");
        return attributRepository.findAll();
    }

    /**
     * GET  /attributs/:id : get the "id" attribut.
     *
     * @param id the id of the attribut to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attribut, or with status 404 (Not Found)
     */
    @GetMapping("/attributs/{id}")
    @Timed
    public ResponseEntity<Attribut> getAttribut(@PathVariable Long id) {
        log.debug("REST request to get Attribut : {}", id);
        Optional<Attribut> attribut = attributRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(attribut);
    }

    /**
     * DELETE  /attributs/:id : delete the "id" attribut.
     *
     * @param id the id of the attribut to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attributs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttribut(@PathVariable Long id) {
        log.debug("REST request to delete Attribut : {}", id);

        attributRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
