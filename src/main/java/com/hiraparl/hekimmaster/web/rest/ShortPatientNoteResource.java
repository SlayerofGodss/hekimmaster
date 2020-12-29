package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.service.ShortPatientNoteService;
import com.hiraparl.hekimmaster.web.rest.errors.BadRequestAlertException;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteDTO;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteCriteria;
import com.hiraparl.hekimmaster.service.ShortPatientNoteQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hiraparl.hekimmaster.domain.ShortPatientNote}.
 */
@RestController
@RequestMapping("/api")
public class ShortPatientNoteResource {

    private final Logger log = LoggerFactory.getLogger(ShortPatientNoteResource.class);

    private static final String ENTITY_NAME = "shortPatientNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShortPatientNoteService shortPatientNoteService;

    private final ShortPatientNoteQueryService shortPatientNoteQueryService;

    public ShortPatientNoteResource(ShortPatientNoteService shortPatientNoteService, ShortPatientNoteQueryService shortPatientNoteQueryService) {
        this.shortPatientNoteService = shortPatientNoteService;
        this.shortPatientNoteQueryService = shortPatientNoteQueryService;
    }

    /**
     * {@code POST  /short-patient-notes} : Create a new shortPatientNote.
     *
     * @param shortPatientNoteDTO the shortPatientNoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shortPatientNoteDTO, or with status {@code 400 (Bad Request)} if the shortPatientNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/short-patient-notes")
    public ResponseEntity<ShortPatientNoteDTO> createShortPatientNote(@Valid @RequestBody ShortPatientNoteDTO shortPatientNoteDTO) throws URISyntaxException {
        log.debug("REST request to save ShortPatientNote : {}", shortPatientNoteDTO);
        if (shortPatientNoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new shortPatientNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShortPatientNoteDTO result = shortPatientNoteService.save(shortPatientNoteDTO);
        return ResponseEntity.created(new URI("/api/short-patient-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /short-patient-notes} : Updates an existing shortPatientNote.
     *
     * @param shortPatientNoteDTO the shortPatientNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shortPatientNoteDTO,
     * or with status {@code 400 (Bad Request)} if the shortPatientNoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shortPatientNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/short-patient-notes")
    public ResponseEntity<ShortPatientNoteDTO> updateShortPatientNote(@Valid @RequestBody ShortPatientNoteDTO shortPatientNoteDTO) throws URISyntaxException {
        log.debug("REST request to update ShortPatientNote : {}", shortPatientNoteDTO);
        if (shortPatientNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShortPatientNoteDTO result = shortPatientNoteService.save(shortPatientNoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shortPatientNoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /short-patient-notes} : get all the shortPatientNotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shortPatientNotes in body.
     */
    @GetMapping("/short-patient-notes")
    public ResponseEntity<List<ShortPatientNoteDTO>> getAllShortPatientNotes(ShortPatientNoteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ShortPatientNotes by criteria: {}", criteria);
        Page<ShortPatientNoteDTO> page = shortPatientNoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /short-patient-notes/count} : count all the shortPatientNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/short-patient-notes/count")
    public ResponseEntity<Long> countShortPatientNotes(ShortPatientNoteCriteria criteria) {
        log.debug("REST request to count ShortPatientNotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(shortPatientNoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /short-patient-notes/:id} : get the "id" shortPatientNote.
     *
     * @param id the id of the shortPatientNoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shortPatientNoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/short-patient-notes/{id}")
    public ResponseEntity<ShortPatientNoteDTO> getShortPatientNote(@PathVariable Long id) {
        log.debug("REST request to get ShortPatientNote : {}", id);
        Optional<ShortPatientNoteDTO> shortPatientNoteDTO = shortPatientNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shortPatientNoteDTO);
    }

    /**
     * {@code DELETE  /short-patient-notes/:id} : delete the "id" shortPatientNote.
     *
     * @param id the id of the shortPatientNoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/short-patient-notes/{id}")
    public ResponseEntity<Void> deleteShortPatientNote(@PathVariable Long id) {
        log.debug("REST request to delete ShortPatientNote : {}", id);
        shortPatientNoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
