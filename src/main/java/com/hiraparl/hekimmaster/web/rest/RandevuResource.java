package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.service.RandevuService;
import com.hiraparl.hekimmaster.web.rest.errors.BadRequestAlertException;
import com.hiraparl.hekimmaster.service.dto.RandevuDTO;
import com.hiraparl.hekimmaster.service.dto.RandevuCriteria;
import com.hiraparl.hekimmaster.service.RandevuQueryService;

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
 * REST controller for managing {@link com.hiraparl.hekimmaster.domain.Randevu}.
 */
@RestController
@RequestMapping("/api")
public class RandevuResource {

    private final Logger log = LoggerFactory.getLogger(RandevuResource.class);

    private static final String ENTITY_NAME = "randevu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RandevuService randevuService;

    private final RandevuQueryService randevuQueryService;

    public RandevuResource(RandevuService randevuService, RandevuQueryService randevuQueryService) {
        this.randevuService = randevuService;
        this.randevuQueryService = randevuQueryService;
    }

    /**
     * {@code POST  /randevus} : Create a new randevu.
     *
     * @param randevuDTO the randevuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new randevuDTO, or with status {@code 400 (Bad Request)} if the randevu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/randevus")
    public ResponseEntity<RandevuDTO> createRandevu(@Valid @RequestBody RandevuDTO randevuDTO) throws URISyntaxException {
        log.debug("REST request to save Randevu : {}", randevuDTO);
        if (randevuDTO.getId() != null) {
            throw new BadRequestAlertException("A new randevu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RandevuDTO result = randevuService.save(randevuDTO);
        return ResponseEntity.created(new URI("/api/randevus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /randevus} : Updates an existing randevu.
     *
     * @param randevuDTO the randevuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated randevuDTO,
     * or with status {@code 400 (Bad Request)} if the randevuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the randevuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/randevus")
    public ResponseEntity<RandevuDTO> updateRandevu(@Valid @RequestBody RandevuDTO randevuDTO) throws URISyntaxException {
        log.debug("REST request to update Randevu : {}", randevuDTO);
        if (randevuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RandevuDTO result = randevuService.save(randevuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, randevuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /randevus} : get all the randevus.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of randevus in body.
     */
    @GetMapping("/randevus")
    public ResponseEntity<List<RandevuDTO>> getAllRandevus(RandevuCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Randevus by criteria: {}", criteria);
        Page<RandevuDTO> page = randevuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /randevus/count} : count all the randevus.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/randevus/count")
    public ResponseEntity<Long> countRandevus(RandevuCriteria criteria) {
        log.debug("REST request to count Randevus by criteria: {}", criteria);
        return ResponseEntity.ok().body(randevuQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /randevus/:id} : get the "id" randevu.
     *
     * @param id the id of the randevuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the randevuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/randevus/{id}")
    public ResponseEntity<RandevuDTO> getRandevu(@PathVariable Long id) {
        log.debug("REST request to get Randevu : {}", id);
        Optional<RandevuDTO> randevuDTO = randevuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(randevuDTO);
    }

    /**
     * {@code DELETE  /randevus/:id} : delete the "id" randevu.
     *
     * @param id the id of the randevuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/randevus/{id}")
    public ResponseEntity<Void> deleteRandevu(@PathVariable Long id) {
        log.debug("REST request to delete Randevu : {}", id);
        randevuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
