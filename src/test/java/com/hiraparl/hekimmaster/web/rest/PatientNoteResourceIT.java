package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.HekimmasterApp;
import com.hiraparl.hekimmaster.domain.PatientNote;
import com.hiraparl.hekimmaster.domain.Patient;
import com.hiraparl.hekimmaster.repository.PatientNoteRepository;
import com.hiraparl.hekimmaster.service.PatientNoteService;
import com.hiraparl.hekimmaster.service.dto.PatientNoteDTO;
import com.hiraparl.hekimmaster.service.mapper.PatientNoteMapper;
import com.hiraparl.hekimmaster.service.dto.PatientNoteCriteria;
import com.hiraparl.hekimmaster.service.PatientNoteQueryService;

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
 * Integration tests for the {@link PatientNoteResource} REST controller.
 */
@SpringBootTest(classes = HekimmasterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PatientNoteResourceIT {

    private static final String DEFAULT_PATIENT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_NOTE = "BBBBBBBBBB";

    @Autowired
    private PatientNoteRepository patientNoteRepository;

    @Autowired
    private PatientNoteMapper patientNoteMapper;

    @Autowired
    private PatientNoteService patientNoteService;

    @Autowired
    private PatientNoteQueryService patientNoteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientNoteMockMvc;

    private PatientNote patientNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientNote createEntity(EntityManager em) {
        PatientNote patientNote = new PatientNote()
            .patientNote(DEFAULT_PATIENT_NOTE);
        return patientNote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientNote createUpdatedEntity(EntityManager em) {
        PatientNote patientNote = new PatientNote()
            .patientNote(UPDATED_PATIENT_NOTE);
        return patientNote;
    }

    @BeforeEach
    public void initTest() {
        patientNote = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientNote() throws Exception {
        int databaseSizeBeforeCreate = patientNoteRepository.findAll().size();
        // Create the PatientNote
        PatientNoteDTO patientNoteDTO = patientNoteMapper.toDto(patientNote);
        restPatientNoteMockMvc.perform(post("/api/patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientNoteDTO)))
            .andExpect(status().isCreated());

        // Validate the PatientNote in the database
        List<PatientNote> patientNoteList = patientNoteRepository.findAll();
        assertThat(patientNoteList).hasSize(databaseSizeBeforeCreate + 1);
        PatientNote testPatientNote = patientNoteList.get(patientNoteList.size() - 1);
        assertThat(testPatientNote.getPatientNote()).isEqualTo(DEFAULT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void createPatientNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientNoteRepository.findAll().size();

        // Create the PatientNote with an existing ID
        patientNote.setId(1L);
        PatientNoteDTO patientNoteDTO = patientNoteMapper.toDto(patientNote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientNoteMockMvc.perform(post("/api/patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PatientNote in the database
        List<PatientNote> patientNoteList = patientNoteRepository.findAll();
        assertThat(patientNoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPatientNotes() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList
        restPatientNoteMockMvc.perform(get("/api/patient-notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientNote").value(hasItem(DEFAULT_PATIENT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getPatientNote() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get the patientNote
        restPatientNoteMockMvc.perform(get("/api/patient-notes/{id}", patientNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientNote.getId().intValue()))
            .andExpect(jsonPath("$.patientNote").value(DEFAULT_PATIENT_NOTE));
    }


    @Test
    @Transactional
    public void getPatientNotesByIdFiltering() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        Long id = patientNote.getId();

        defaultPatientNoteShouldBeFound("id.equals=" + id);
        defaultPatientNoteShouldNotBeFound("id.notEquals=" + id);

        defaultPatientNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientNoteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPatientNotesByPatientNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList where patientNote equals to DEFAULT_PATIENT_NOTE
        defaultPatientNoteShouldBeFound("patientNote.equals=" + DEFAULT_PATIENT_NOTE);

        // Get all the patientNoteList where patientNote equals to UPDATED_PATIENT_NOTE
        defaultPatientNoteShouldNotBeFound("patientNote.equals=" + UPDATED_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllPatientNotesByPatientNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList where patientNote not equals to DEFAULT_PATIENT_NOTE
        defaultPatientNoteShouldNotBeFound("patientNote.notEquals=" + DEFAULT_PATIENT_NOTE);

        // Get all the patientNoteList where patientNote not equals to UPDATED_PATIENT_NOTE
        defaultPatientNoteShouldBeFound("patientNote.notEquals=" + UPDATED_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllPatientNotesByPatientNoteIsInShouldWork() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList where patientNote in DEFAULT_PATIENT_NOTE or UPDATED_PATIENT_NOTE
        defaultPatientNoteShouldBeFound("patientNote.in=" + DEFAULT_PATIENT_NOTE + "," + UPDATED_PATIENT_NOTE);

        // Get all the patientNoteList where patientNote equals to UPDATED_PATIENT_NOTE
        defaultPatientNoteShouldNotBeFound("patientNote.in=" + UPDATED_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllPatientNotesByPatientNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList where patientNote is not null
        defaultPatientNoteShouldBeFound("patientNote.specified=true");

        // Get all the patientNoteList where patientNote is null
        defaultPatientNoteShouldNotBeFound("patientNote.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatientNotesByPatientNoteContainsSomething() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList where patientNote contains DEFAULT_PATIENT_NOTE
        defaultPatientNoteShouldBeFound("patientNote.contains=" + DEFAULT_PATIENT_NOTE);

        // Get all the patientNoteList where patientNote contains UPDATED_PATIENT_NOTE
        defaultPatientNoteShouldNotBeFound("patientNote.contains=" + UPDATED_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllPatientNotesByPatientNoteNotContainsSomething() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        // Get all the patientNoteList where patientNote does not contain DEFAULT_PATIENT_NOTE
        defaultPatientNoteShouldNotBeFound("patientNote.doesNotContain=" + DEFAULT_PATIENT_NOTE);

        // Get all the patientNoteList where patientNote does not contain UPDATED_PATIENT_NOTE
        defaultPatientNoteShouldBeFound("patientNote.doesNotContain=" + UPDATED_PATIENT_NOTE);
    }


    @Test
    @Transactional
    public void getAllPatientNotesByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);
        Patient patient = PatientResourceIT.createEntity(em);
        em.persist(patient);
        em.flush();
        patientNote.setPatient(patient);
        patientNoteRepository.saveAndFlush(patientNote);
        Long patientId = patient.getId();

        // Get all the patientNoteList where patient equals to patientId
        defaultPatientNoteShouldBeFound("patientId.equals=" + patientId);

        // Get all the patientNoteList where patient equals to patientId + 1
        defaultPatientNoteShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientNoteShouldBeFound(String filter) throws Exception {
        restPatientNoteMockMvc.perform(get("/api/patient-notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientNote").value(hasItem(DEFAULT_PATIENT_NOTE)));

        // Check, that the count call also returns 1
        restPatientNoteMockMvc.perform(get("/api/patient-notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientNoteShouldNotBeFound(String filter) throws Exception {
        restPatientNoteMockMvc.perform(get("/api/patient-notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientNoteMockMvc.perform(get("/api/patient-notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPatientNote() throws Exception {
        // Get the patientNote
        restPatientNoteMockMvc.perform(get("/api/patient-notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientNote() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        int databaseSizeBeforeUpdate = patientNoteRepository.findAll().size();

        // Update the patientNote
        PatientNote updatedPatientNote = patientNoteRepository.findById(patientNote.getId()).get();
        // Disconnect from session so that the updates on updatedPatientNote are not directly saved in db
        em.detach(updatedPatientNote);
        updatedPatientNote
            .patientNote(UPDATED_PATIENT_NOTE);
        PatientNoteDTO patientNoteDTO = patientNoteMapper.toDto(updatedPatientNote);

        restPatientNoteMockMvc.perform(put("/api/patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientNoteDTO)))
            .andExpect(status().isOk());

        // Validate the PatientNote in the database
        List<PatientNote> patientNoteList = patientNoteRepository.findAll();
        assertThat(patientNoteList).hasSize(databaseSizeBeforeUpdate);
        PatientNote testPatientNote = patientNoteList.get(patientNoteList.size() - 1);
        assertThat(testPatientNote.getPatientNote()).isEqualTo(UPDATED_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientNote() throws Exception {
        int databaseSizeBeforeUpdate = patientNoteRepository.findAll().size();

        // Create the PatientNote
        PatientNoteDTO patientNoteDTO = patientNoteMapper.toDto(patientNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientNoteMockMvc.perform(put("/api/patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PatientNote in the database
        List<PatientNote> patientNoteList = patientNoteRepository.findAll();
        assertThat(patientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatientNote() throws Exception {
        // Initialize the database
        patientNoteRepository.saveAndFlush(patientNote);

        int databaseSizeBeforeDelete = patientNoteRepository.findAll().size();

        // Delete the patientNote
        restPatientNoteMockMvc.perform(delete("/api/patient-notes/{id}", patientNote.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientNote> patientNoteList = patientNoteRepository.findAll();
        assertThat(patientNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
