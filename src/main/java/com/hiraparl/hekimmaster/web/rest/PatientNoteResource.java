package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.service.PatientNoteService;
import com.hiraparl.hekimmaster.web.rest.errors.BadRequestAlertException;
import com.hiraparl.hekimmaster.service.dto.PatientNoteDTO;
import com.hiraparl.hekimmaster.service.dto.PatientNoteCriteria;
import com.hiraparl.hekimmaster.service.PatientNoteQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hiraparl.hekimmaster.domain.PatientNote}.
 */
@RestController
@RequestMapping("/api")
public class PatientNoteResource {

    private final Logger log = LoggerFactory.getLogger(PatientNoteResource.class);

    private static final String ENTITY_NAME = "patientNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientNoteService patientNoteService;

    private final PatientNoteQueryService patientNoteQueryService;

    public PatientNoteResource(PatientNoteService patientNoteService, PatientNoteQueryService patientNoteQueryService) {
        this.patientNoteService = patientNoteService;
        this.patientNoteQueryService = patientNoteQueryService;
    }

    /**
     * {@code POST  /patient-notes} : Create a new patientNote.
     *
     * @param patientNoteDTO the patientNoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientNoteDTO, or with status {@code 400 (Bad Request)} if the patientNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-notes")
    public ResponseEntity<PatientNoteDTO> createPatientNote(@RequestBody PatientNoteDTO patientNoteDTO) throws URISyntaxException {
        log.debug("REST request to save PatientNote : {}", patientNoteDTO);
        if (patientNoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientNoteDTO result = patientNoteService.save(patientNoteDTO);
        return ResponseEntity.created(new URI("/api/patient-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-notes} : Updates an existing patientNote.
     *
     * @param patientNoteDTO the patientNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientNoteDTO,
     * or with status {@code 400 (Bad Request)} if the patientNoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-notes")
    public ResponseEntity<PatientNoteDTO> updatePatientNote(@RequestBody PatientNoteDTO patientNoteDTO) throws URISyntaxException {
        log.debug("REST request to update PatientNote : {}", patientNoteDTO);
        if (patientNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientNoteDTO result = patientNoteService.save(patientNoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientNoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-notes} : get all the patientNotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientNotes in body.
     */
    @GetMapping("/patient-notes")
    public ResponseEntity<List<PatientNoteDTO>> getAllPatientNotes(PatientNoteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PatientNotes by criteria: {}", criteria);
        Page<PatientNoteDTO> page = patientNoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patient-notes/count} : count all the patientNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/patient-notes/count")
    public ResponseEntity<Long> countPatientNotes(PatientNoteCriteria criteria) {
        log.debug("REST request to count PatientNotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(patientNoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /patient-notes/:id} : get the "id" patientNote.
     *
     * @param id the id of the patientNoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientNoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-notes/{id}")
    public ResponseEntity<PatientNoteDTO> getPatientNote(@PathVariable Long id) {
        log.debug("REST request to get PatientNote : {}", id);
        Optional<PatientNoteDTO> patientNoteDTO = patientNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientNoteDTO);
    }

    /**
     * {@code DELETE  /patient-notes/:id} : delete the "id" patientNote.
     *
     * @param id the id of the patientNoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-notes/{id}")
    public ResponseEntity<Void> deletePatientNote(@PathVariable Long id) {
        log.debug("REST request to delete PatientNote : {}", id);
        patientNoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
