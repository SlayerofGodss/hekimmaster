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

import com.hiraparl.hekimmaster.domain.PatientNote;
import com.hiraparl.hekimmaster.domain.*; // for static metamodels
import com.hiraparl.hekimmaster.repository.PatientNoteRepository;
import com.hiraparl.hekimmaster.service.dto.PatientNoteCriteria;
import com.hiraparl.hekimmaster.service.dto.PatientNoteDTO;
import com.hiraparl.hekimmaster.service.mapper.PatientNoteMapper;

/**
 * Service for executing complex queries for {@link PatientNote} entities in the database.
 * The main input is a {@link PatientNoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatientNoteDTO} or a {@link Page} of {@link PatientNoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientNoteQueryService extends QueryService<PatientNote> {

    private final Logger log = LoggerFactory.getLogger(PatientNoteQueryService.class);

    private final PatientNoteRepository patientNoteRepository;

    private final PatientNoteMapper patientNoteMapper;

    public PatientNoteQueryService(PatientNoteRepository patientNoteRepository, PatientNoteMapper patientNoteMapper) {
        this.patientNoteRepository = patientNoteRepository;
        this.patientNoteMapper = patientNoteMapper;
    }

    /**
     * Return a {@link List} of {@link PatientNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatientNoteDTO> findByCriteria(PatientNoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatientNote> specification = createSpecification(criteria);
        return patientNoteMapper.toDto(patientNoteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PatientNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientNoteDTO> findByCriteria(PatientNoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatientNote> specification = createSpecification(criteria);
        return patientNoteRepository.findAll(specification, page)
            .map(patientNoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientNoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatientNote> specification = createSpecification(criteria);
        return patientNoteRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientNoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatientNote> createSpecification(PatientNoteCriteria criteria) {
        Specification<PatientNote> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatientNote_.id));
            }
            if (criteria.getPatientNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientNote(), PatientNote_.patientNote));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(PatientNote_.patient, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
