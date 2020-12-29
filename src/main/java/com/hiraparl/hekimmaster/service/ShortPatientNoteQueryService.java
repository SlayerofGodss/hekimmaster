package com.hiraparl.hekimmaster.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.hiraparl.hekimmaster.domain.ShortPatientNote;
import com.hiraparl.hekimmaster.domain.*; // for static metamodels
import com.hiraparl.hekimmaster.repository.ShortPatientNoteRepository;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteCriteria;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteDTO;
import com.hiraparl.hekimmaster.service.mapper.ShortPatientNoteMapper;

/**
 * Service for executing complex queries for {@link ShortPatientNote} entities in the database.
 * The main input is a {@link ShortPatientNoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShortPatientNoteDTO} or a {@link Page} of {@link ShortPatientNoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShortPatientNoteQueryService extends QueryService<ShortPatientNote> {

    private final Logger log = LoggerFactory.getLogger(ShortPatientNoteQueryService.class);

    private final ShortPatientNoteRepository shortPatientNoteRepository;

    private final ShortPatientNoteMapper shortPatientNoteMapper;

    public ShortPatientNoteQueryService(ShortPatientNoteRepository shortPatientNoteRepository, ShortPatientNoteMapper shortPatientNoteMapper) {
        this.shortPatientNoteRepository = shortPatientNoteRepository;
        this.shortPatientNoteMapper = shortPatientNoteMapper;
    }

    /**
     * Return a {@link List} of {@link ShortPatientNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShortPatientNoteDTO> findByCriteria(ShortPatientNoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShortPatientNote> specification = createSpecification(criteria);
        return shortPatientNoteMapper.toDto(shortPatientNoteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShortPatientNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShortPatientNoteDTO> findByCriteria(ShortPatientNoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShortPatientNote> specification = createSpecification(criteria);
        return shortPatientNoteRepository.findAll(specification, page)
            .map(shortPatientNoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShortPatientNoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShortPatientNote> specification = createSpecification(criteria);
        return shortPatientNoteRepository.count(specification);
    }

    /**
     * Function to convert {@link ShortPatientNoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShortPatientNote> createSpecification(ShortPatientNoteCriteria criteria) {
        Specification<ShortPatientNote> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShortPatientNote_.id));
            }
            if (criteria.getShortPatientNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortPatientNote(), ShortPatientNote_.shortPatientNote));
            }
        }
        return specification;
    }
}
