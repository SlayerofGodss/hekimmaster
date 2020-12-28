package com.hiraparl.hekimmaster.service;

import com.hiraparl.hekimmaster.domain.Randevu;
import com.hiraparl.hekimmaster.repository.RandevuRepository;
import com.hiraparl.hekimmaster.service.dto.RandevuDTO;
import com.hiraparl.hekimmaster.service.mapper.RandevuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Randevu}.
 */
@Service
@Transactional
public class RandevuService {

    private final Logger log = LoggerFactory.getLogger(RandevuService.class);

    private final RandevuRepository randevuRepository;

    private final RandevuMapper randevuMapper;

    public RandevuService(RandevuRepository randevuRepository, RandevuMapper randevuMapper) {
        this.randevuRepository = randevuRepository;
        this.randevuMapper = randevuMapper;
    }

    /**
     * Save a randevu.
     *
     * @param randevuDTO the entity to save.
     * @return the persisted entity.
     */
    public RandevuDTO save(RandevuDTO randevuDTO) {
        log.debug("Request to save Randevu : {}", randevuDTO);
        Randevu randevu = randevuMapper.toEntity(randevuDTO);
        randevu = randevuRepository.save(randevu);
        return randevuMapper.toDto(randevu);
    }

    /**
     * Get all the randevus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RandevuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Randevus");
        return randevuRepository.findAll(pageable)
            .map(randevuMapper::toDto);
    }


    /**
     * Get one randevu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RandevuDTO> findOne(Long id) {
        log.debug("Request to get Randevu : {}", id);
        return randevuRepository.findById(id)
            .map(randevuMapper::toDto);
    }

    /**
     * Delete the randevu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Randevu : {}", id);
        randevuRepository.deleteById(id);
    }
}
