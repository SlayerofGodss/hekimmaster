package com.hiraparl.hekimmaster.service;

import com.hiraparl.hekimmaster.domain.PatientNote;
import com.hiraparl.hekimmaster.repository.PatientNoteRepository;
import com.hiraparl.hekimmaster.service.dto.PatientNoteDTO;
import com.hiraparl.hekimmaster.service.mapper.PatientNoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PatientNote}.
 */
@Service
@Transactional
public class PatientNoteService {

    private final Logger log = LoggerFactory.getLogger(PatientNoteService.class);

    private final PatientNoteRepository patientNoteRepository;

    private final PatientNoteMapper patientNoteMapper;

    public PatientNoteService(PatientNoteRepository patientNoteRepository, PatientNoteMapper patientNoteMapper) {
        this.patientNoteRepository = patientNoteRepository;
        this.patientNoteMapper = patientNoteMapper;
    }

    /**
     * Save a patientNote.
     *
     * @param patientNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public PatientNoteDTO save(PatientNoteDTO patientNoteDTO) {
        log.debug("Request to save PatientNote : {}", patientNoteDTO);
        PatientNote patientNote = patientNoteMapper.toEntity(patientNoteDTO);
        patientNote = patientNoteRepository.save(patientNote);
        return patientNoteMapper.toDto(patientNote);
    }

    /**
     * Get all the patientNotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientNoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PatientNotes");
        return patientNoteRepository.findAll(pageable)
            .map(patientNoteMapper::toDto);
    }


    /**
     * Get one patientNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PatientNoteDTO> findOne(Long id) {
        log.debug("Request to get PatientNote : {}", id);
        return patientNoteRepository.findById(id)
            .map(patientNoteMapper::toDto);
    }

    /**
     * Delete the patientNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientNote : {}", id);
        patientNoteRepository.deleteById(id);
    }
}
