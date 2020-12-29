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

import com.hiraparl.hekimmaster.domain.Day;
import com.hiraparl.hekimmaster.domain.*; // for static metamodels
import com.hiraparl.hekimmaster.repository.DayRepository;
import com.hiraparl.hekimmaster.service.dto.DayCriteria;
import com.hiraparl.hekimmaster.service.dto.DayDTO;
import com.hiraparl.hekimmaster.service.mapper.DayMapper;

/**
 * Service for executing complex queries for {@link Day} entities in the database.
 * The main input is a {@link DayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DayDTO} or a {@link Page} of {@link DayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DayQueryService extends QueryService<Day> {

    private final Logger log = LoggerFactory.getLogger(DayQueryService.class);

    private final DayRepository dayRepository;

    private final DayMapper dayMapper;

    public DayQueryService(DayRepository dayRepository, DayMapper dayMapper) {
        this.dayRepository = dayRepository;
        this.dayMapper = dayMapper;
    }

    /**
     * Return a {@link List} of {@link DayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DayDTO> findByCriteria(DayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Day> specification = createSpecification(criteria);
        return dayMapper.toDto(dayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DayDTO> findByCriteria(DayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Day> specification = createSpecification(criteria);
        return dayRepository.findAll(specification, page)
            .map(dayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Day> specification = createSpecification(criteria);
        return dayRepository.count(specification);
    }

    /**
     * Function to convert {@link DayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Day> createSpecification(DayCriteria criteria) {
        Specification<Day> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Day_.id));
            }
            if (criteria.getRandevuDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRandevuDate(), Day_.randevuDate));
            }
        }
        return specification;
    }
}
