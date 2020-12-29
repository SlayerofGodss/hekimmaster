package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.service.DayService;
import com.hiraparl.hekimmaster.web.rest.errors.BadRequestAlertException;
import com.hiraparl.hekimmaster.service.dto.DayDTO;
import com.hiraparl.hekimmaster.service.dto.DayCriteria;
import com.hiraparl.hekimmaster.service.DayQueryService;

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
 * REST controller for managing {@link com.hiraparl.hekimmaster.domain.Day}.
 */
@RestController
@RequestMapping("/api")
public class DayResource {

    private final Logger log = LoggerFactory.getLogger(DayResource.class);

    private static final String ENTITY_NAME = "day";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DayService dayService;

    private final DayQueryService dayQueryService;

    public DayResource(DayService dayService, DayQueryService dayQueryService) {
        this.dayService = dayService;
        this.dayQueryService = dayQueryService;
    }

    /**
     * {@code POST  /days} : Create a new day.
     *
     * @param dayDTO the dayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dayDTO, or with status {@code 400 (Bad Request)} if the day has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/days")
    public ResponseEntity<DayDTO> createDay(@Valid @RequestBody DayDTO dayDTO) throws URISyntaxException {
        log.debug("REST request to save Day : {}", dayDTO);
        if (dayDTO.getId() != null) {
            throw new BadRequestAlertException("A new day cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DayDTO result = dayService.save(dayDTO);
        return ResponseEntity.created(new URI("/api/days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /days} : Updates an existing day.
     *
     * @param dayDTO the dayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dayDTO,
     * or with status {@code 400 (Bad Request)} if the dayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/days")
    public ResponseEntity<DayDTO> updateDay(@Valid @RequestBody DayDTO dayDTO) throws URISyntaxException {
        log.debug("REST request to update Day : {}", dayDTO);
        if (dayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DayDTO result = dayService.save(dayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /days} : get all the days.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of days in body.
     */
    @GetMapping("/days")
    public ResponseEntity<List<DayDTO>> getAllDays(DayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Days by criteria: {}", criteria);
        Page<DayDTO> page = dayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /days/count} : count all the days.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/days/count")
    public ResponseEntity<Long> countDays(DayCriteria criteria) {
        log.debug("REST request to count Days by criteria: {}", criteria);
        return ResponseEntity.ok().body(dayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /days/:id} : get the "id" day.
     *
     * @param id the id of the dayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/days/{id}")
    public ResponseEntity<DayDTO> getDay(@PathVariable Long id) {
        log.debug("REST request to get Day : {}", id);
        Optional<DayDTO> dayDTO = dayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dayDTO);
    }

    /**
     * {@code DELETE  /days/:id} : delete the "id" day.
     *
     * @param id the id of the dayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/days/{id}")
    public ResponseEntity<Void> deleteDay(@PathVariable Long id) {
        log.debug("REST request to delete Day : {}", id);
        dayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
