package com.hiraparl.hekimmaster.service;

import com.hiraparl.hekimmaster.domain.Day;
import com.hiraparl.hekimmaster.repository.DayRepository;
import com.hiraparl.hekimmaster.service.dto.DayDTO;
import com.hiraparl.hekimmaster.service.mapper.DayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Day}.
 */
@Service
@Transactional
public class DayService {

    private final Logger log = LoggerFactory.getLogger(DayService.class);

    private final DayRepository dayRepository;

    private final DayMapper dayMapper;

    public DayService(DayRepository dayRepository, DayMapper dayMapper) {
        this.dayRepository = dayRepository;
        this.dayMapper = dayMapper;
    }

    /**
     * Save a day.
     *
     * @param dayDTO the entity to save.
     * @return the persisted entity.
     */
    public DayDTO save(DayDTO dayDTO) {
        log.debug("Request to save Day : {}", dayDTO);
        Day day = dayMapper.toEntity(dayDTO);
        day = dayRepository.save(day);
        return dayMapper.toDto(day);
    }

    /**
     * Get all the days.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Days");
        return dayRepository.findAll(pageable)
            .map(dayMapper::toDto);
    }


    /**
     * Get one day by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DayDTO> findOne(Long id) {
        log.debug("Request to get Day : {}", id);
        return dayRepository.findById(id)
            .map(dayMapper::toDto);
    }

    /**
     * Delete the day by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Day : {}", id);
        dayRepository.deleteById(id);
    }
}
