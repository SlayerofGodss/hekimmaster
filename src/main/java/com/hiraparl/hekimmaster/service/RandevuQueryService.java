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

import com.hiraparl.hekimmaster.domain.Randevu;
import com.hiraparl.hekimmaster.domain.*; // for static metamodels
import com.hiraparl.hekimmaster.repository.RandevuRepository;
import com.hiraparl.hekimmaster.service.dto.RandevuCriteria;
import com.hiraparl.hekimmaster.service.dto.RandevuDTO;
import com.hiraparl.hekimmaster.service.mapper.RandevuMapper;

/**
 * Service for executing complex queries for {@link Randevu} entities in the database.
 * The main input is a {@link RandevuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RandevuDTO} or a {@link Page} of {@link RandevuDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RandevuQueryService extends QueryService<Randevu> {

    private final Logger log = LoggerFactory.getLogger(RandevuQueryService.class);

    private final RandevuRepository randevuRepository;

    private final RandevuMapper randevuMapper;

    public RandevuQueryService(RandevuRepository randevuRepository, RandevuMapper randevuMapper) {
        this.randevuRepository = randevuRepository;
        this.randevuMapper = randevuMapper;
    }

    /**
     * Return a {@link List} of {@link RandevuDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RandevuDTO> findByCriteria(RandevuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Randevu> specification = createSpecification(criteria);
        return randevuMapper.toDto(randevuRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RandevuDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RandevuDTO> findByCriteria(RandevuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Randevu> specification = createSpecification(criteria);
        return randevuRepository.findAll(specification, page)
            .map(randevuMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RandevuCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Randevu> specification = createSpecification(criteria);
        return randevuRepository.count(specification);
    }

    /**
     * Function to convert {@link RandevuCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Randevu> createSpecification(RandevuCriteria criteria) {
        Specification<Randevu> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Randevu_.id));
            }
            if (criteria.getRandevu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRandevu(), Randevu_.randevu));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Randevu_.patient, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
