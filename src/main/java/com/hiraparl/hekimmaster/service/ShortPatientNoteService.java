package com.hiraparl.hekimmaster.service;

import com.hiraparl.hekimmaster.domain.ShortPatientNote;
import com.hiraparl.hekimmaster.repository.ShortPatientNoteRepository;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteDTO;
import com.hiraparl.hekimmaster.service.mapper.ShortPatientNoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ShortPatientNote}.
 */
@Service
@Transactional
public class ShortPatientNoteService {

    private final Logger log = LoggerFactory.getLogger(ShortPatientNoteService.class);

    private final ShortPatientNoteRepository shortPatientNoteRepository;

    private final ShortPatientNoteMapper shortPatientNoteMapper;

    public ShortPatientNoteService(ShortPatientNoteRepository shortPatientNoteRepository, ShortPatientNoteMapper shortPatientNoteMapper) {
        this.shortPatientNoteRepository = shortPatientNoteRepository;
        this.shortPatientNoteMapper = shortPatientNoteMapper;
    }

    /**
     * Save a shortPatientNote.
     *
     * @param shortPatientNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public ShortPatientNoteDTO save(ShortPatientNoteDTO shortPatientNoteDTO) {
        log.debug("Request to save ShortPatientNote : {}", shortPatientNoteDTO);
        ShortPatientNote shortPatientNote = shortPatientNoteMapper.toEntity(shortPatientNoteDTO);
        shortPatientNote = shortPatientNoteRepository.save(shortPatientNote);
        return shortPatientNoteMapper.toDto(shortPatientNote);
    }

    /**
     * Get all the shortPatientNotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ShortPatientNoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShortPatientNotes");
        return shortPatientNoteRepository.findAll(pageable)
            .map(shortPatientNoteMapper::toDto);
    }


    /**
     * Get one shortPatientNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShortPatientNoteDTO> findOne(Long id) {
        log.debug("Request to get ShortPatientNote : {}", id);
        return shortPatientNoteRepository.findById(id)
            .map(shortPatientNoteMapper::toDto);
    }

    /**
     * Delete the shortPatientNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ShortPatientNote : {}", id);
        shortPatientNoteRepository.deleteById(id);
    }
}
