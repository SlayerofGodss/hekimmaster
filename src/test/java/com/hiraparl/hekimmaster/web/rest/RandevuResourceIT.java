package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.HekimmasterApp;
import com.hiraparl.hekimmaster.domain.Randevu;
import com.hiraparl.hekimmaster.domain.Patient;
import com.hiraparl.hekimmaster.repository.RandevuRepository;
import com.hiraparl.hekimmaster.service.RandevuService;
import com.hiraparl.hekimmaster.service.dto.RandevuDTO;
import com.hiraparl.hekimmaster.service.mapper.RandevuMapper;
import com.hiraparl.hekimmaster.service.dto.RandevuCriteria;
import com.hiraparl.hekimmaster.service.RandevuQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RandevuResource} REST controller.
 */
@SpringBootTest(classes = HekimmasterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RandevuResourceIT {

    private static final String DEFAULT_RANDEVU = "AAAAAAAAAA";
    private static final String UPDATED_RANDEVU = "BBBBBBBBBB";

    @Autowired
    private RandevuRepository randevuRepository;

    @Autowired
    private RandevuMapper randevuMapper;

    @Autowired
    private RandevuService randevuService;

    @Autowired
    private RandevuQueryService randevuQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRandevuMockMvc;

    private Randevu randevu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Randevu createEntity(EntityManager em) {
        Randevu randevu = new Randevu()
            .randevu(DEFAULT_RANDEVU);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        randevu.setPatient(patient);
        return randevu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Randevu createUpdatedEntity(EntityManager em) {
        Randevu randevu = new Randevu()
            .randevu(UPDATED_RANDEVU);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        randevu.setPatient(patient);
        return randevu;
    }

    @BeforeEach
    public void initTest() {
        randevu = createEntity(em);
    }

    @Test
    @Transactional
    public void createRandevu() throws Exception {
        int databaseSizeBeforeCreate = randevuRepository.findAll().size();
        // Create the Randevu
        RandevuDTO randevuDTO = randevuMapper.toDto(randevu);
        restRandevuMockMvc.perform(post("/api/randevus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(randevuDTO)))
            .andExpect(status().isCreated());

        // Validate the Randevu in the database
        List<Randevu> randevuList = randevuRepository.findAll();
        assertThat(randevuList).hasSize(databaseSizeBeforeCreate + 1);
        Randevu testRandevu = randevuList.get(randevuList.size() - 1);
        assertThat(testRandevu.getRandevu()).isEqualTo(DEFAULT_RANDEVU);
    }

    @Test
    @Transactional
    public void createRandevuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = randevuRepository.findAll().size();

        // Create the Randevu with an existing ID
        randevu.setId(1L);
        RandevuDTO randevuDTO = randevuMapper.toDto(randevu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRandevuMockMvc.perform(post("/api/randevus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(randevuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Randevu in the database
        List<Randevu> randevuList = randevuRepository.findAll();
        assertThat(randevuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRandevuIsRequired() throws Exception {
        int databaseSizeBeforeTest = randevuRepository.findAll().size();
        // set the field null
        randevu.setRandevu(null);

        // Create the Randevu, which fails.
        RandevuDTO randevuDTO = randevuMapper.toDto(randevu);


        restRandevuMockMvc.perform(post("/api/randevus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(randevuDTO)))
            .andExpect(status().isBadRequest());

        List<Randevu> randevuList = randevuRepository.findAll();
        assertThat(randevuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRandevus() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList
        restRandevuMockMvc.perform(get("/api/randevus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(randevu.getId().intValue())))
            .andExpect(jsonPath("$.[*].randevu").value(hasItem(DEFAULT_RANDEVU)));
    }
    
    @Test
    @Transactional
    public void getRandevu() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get the randevu
        restRandevuMockMvc.perform(get("/api/randevus/{id}", randevu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(randevu.getId().intValue()))
            .andExpect(jsonPath("$.randevu").value(DEFAULT_RANDEVU));
    }


    @Test
    @Transactional
    public void getRandevusByIdFiltering() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        Long id = randevu.getId();

        defaultRandevuShouldBeFound("id.equals=" + id);
        defaultRandevuShouldNotBeFound("id.notEquals=" + id);

        defaultRandevuShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRandevuShouldNotBeFound("id.greaterThan=" + id);

        defaultRandevuShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRandevuShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRandevusByRandevuIsEqualToSomething() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList where randevu equals to DEFAULT_RANDEVU
        defaultRandevuShouldBeFound("randevu.equals=" + DEFAULT_RANDEVU);

        // Get all the randevuList where randevu equals to UPDATED_RANDEVU
        defaultRandevuShouldNotBeFound("randevu.equals=" + UPDATED_RANDEVU);
    }

    @Test
    @Transactional
    public void getAllRandevusByRandevuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList where randevu not equals to DEFAULT_RANDEVU
        defaultRandevuShouldNotBeFound("randevu.notEquals=" + DEFAULT_RANDEVU);

        // Get all the randevuList where randevu not equals to UPDATED_RANDEVU
        defaultRandevuShouldBeFound("randevu.notEquals=" + UPDATED_RANDEVU);
    }

    @Test
    @Transactional
    public void getAllRandevusByRandevuIsInShouldWork() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList where randevu in DEFAULT_RANDEVU or UPDATED_RANDEVU
        defaultRandevuShouldBeFound("randevu.in=" + DEFAULT_RANDEVU + "," + UPDATED_RANDEVU);

        // Get all the randevuList where randevu equals to UPDATED_RANDEVU
        defaultRandevuShouldNotBeFound("randevu.in=" + UPDATED_RANDEVU);
    }

    @Test
    @Transactional
    public void getAllRandevusByRandevuIsNullOrNotNull() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList where randevu is not null
        defaultRandevuShouldBeFound("randevu.specified=true");

        // Get all the randevuList where randevu is null
        defaultRandevuShouldNotBeFound("randevu.specified=false");
    }
                @Test
    @Transactional
    public void getAllRandevusByRandevuContainsSomething() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList where randevu contains DEFAULT_RANDEVU
        defaultRandevuShouldBeFound("randevu.contains=" + DEFAULT_RANDEVU);

        // Get all the randevuList where randevu contains UPDATED_RANDEVU
        defaultRandevuShouldNotBeFound("randevu.contains=" + UPDATED_RANDEVU);
    }

    @Test
    @Transactional
    public void getAllRandevusByRandevuNotContainsSomething() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        // Get all the randevuList where randevu does not contain DEFAULT_RANDEVU
        defaultRandevuShouldNotBeFound("randevu.doesNotContain=" + DEFAULT_RANDEVU);

        // Get all the randevuList where randevu does not contain UPDATED_RANDEVU
        defaultRandevuShouldBeFound("randevu.doesNotContain=" + UPDATED_RANDEVU);
    }


    @Test
    @Transactional
    public void getAllRandevusByPatientIsEqualToSomething() throws Exception {
        // Get already existing entity
        Patient patient = randevu.getPatient();
        randevuRepository.saveAndFlush(randevu);
        Long patientId = patient.getId();

        // Get all the randevuList where patient equals to patientId
        defaultRandevuShouldBeFound("patientId.equals=" + patientId);

        // Get all the randevuList where patient equals to patientId + 1
        defaultRandevuShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRandevuShouldBeFound(String filter) throws Exception {
        restRandevuMockMvc.perform(get("/api/randevus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(randevu.getId().intValue())))
            .andExpect(jsonPath("$.[*].randevu").value(hasItem(DEFAULT_RANDEVU)));

        // Check, that the count call also returns 1
        restRandevuMockMvc.perform(get("/api/randevus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRandevuShouldNotBeFound(String filter) throws Exception {
        restRandevuMockMvc.perform(get("/api/randevus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRandevuMockMvc.perform(get("/api/randevus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRandevu() throws Exception {
        // Get the randevu
        restRandevuMockMvc.perform(get("/api/randevus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRandevu() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        int databaseSizeBeforeUpdate = randevuRepository.findAll().size();

        // Update the randevu
        Randevu updatedRandevu = randevuRepository.findById(randevu.getId()).get();
        // Disconnect from session so that the updates on updatedRandevu are not directly saved in db
        em.detach(updatedRandevu);
        updatedRandevu
            .randevu(UPDATED_RANDEVU);
        RandevuDTO randevuDTO = randevuMapper.toDto(updatedRandevu);

        restRandevuMockMvc.perform(put("/api/randevus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(randevuDTO)))
            .andExpect(status().isOk());

        // Validate the Randevu in the database
        List<Randevu> randevuList = randevuRepository.findAll();
        assertThat(randevuList).hasSize(databaseSizeBeforeUpdate);
        Randevu testRandevu = randevuList.get(randevuList.size() - 1);
        assertThat(testRandevu.getRandevu()).isEqualTo(UPDATED_RANDEVU);
    }

    @Test
    @Transactional
    public void updateNonExistingRandevu() throws Exception {
        int databaseSizeBeforeUpdate = randevuRepository.findAll().size();

        // Create the Randevu
        RandevuDTO randevuDTO = randevuMapper.toDto(randevu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRandevuMockMvc.perform(put("/api/randevus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(randevuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Randevu in the database
        List<Randevu> randevuList = randevuRepository.findAll();
        assertThat(randevuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRandevu() throws Exception {
        // Initialize the database
        randevuRepository.saveAndFlush(randevu);

        int databaseSizeBeforeDelete = randevuRepository.findAll().size();

        // Delete the randevu
        restRandevuMockMvc.perform(delete("/api/randevus/{id}", randevu.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Randevu> randevuList = randevuRepository.findAll();
        assertThat(randevuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
