package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.HekimmasterApp;
import com.hiraparl.hekimmaster.domain.ShortPatientNote;
import com.hiraparl.hekimmaster.repository.ShortPatientNoteRepository;
import com.hiraparl.hekimmaster.service.ShortPatientNoteService;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteDTO;
import com.hiraparl.hekimmaster.service.mapper.ShortPatientNoteMapper;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteCriteria;
import com.hiraparl.hekimmaster.service.ShortPatientNoteQueryService;

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
 * Integration tests for the {@link ShortPatientNoteResource} REST controller.
 */
@SpringBootTest(classes = HekimmasterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShortPatientNoteResourceIT {

    private static final String DEFAULT_SHORT_PATIENT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_PATIENT_NOTE = "BBBBBBBBBB";

    @Autowired
    private ShortPatientNoteRepository shortPatientNoteRepository;

    @Autowired
    private ShortPatientNoteMapper shortPatientNoteMapper;

    @Autowired
    private ShortPatientNoteService shortPatientNoteService;

    @Autowired
    private ShortPatientNoteQueryService shortPatientNoteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShortPatientNoteMockMvc;

    private ShortPatientNote shortPatientNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShortPatientNote createEntity(EntityManager em) {
        ShortPatientNote shortPatientNote = new ShortPatientNote()
            .shortPatientNote(DEFAULT_SHORT_PATIENT_NOTE);
        return shortPatientNote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShortPatientNote createUpdatedEntity(EntityManager em) {
        ShortPatientNote shortPatientNote = new ShortPatientNote()
            .shortPatientNote(UPDATED_SHORT_PATIENT_NOTE);
        return shortPatientNote;
    }

    @BeforeEach
    public void initTest() {
        shortPatientNote = createEntity(em);
    }

    @Test
    @Transactional
    public void createShortPatientNote() throws Exception {
        int databaseSizeBeforeCreate = shortPatientNoteRepository.findAll().size();
        // Create the ShortPatientNote
        ShortPatientNoteDTO shortPatientNoteDTO = shortPatientNoteMapper.toDto(shortPatientNote);
        restShortPatientNoteMockMvc.perform(post("/api/short-patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shortPatientNoteDTO)))
            .andExpect(status().isCreated());

        // Validate the ShortPatientNote in the database
        List<ShortPatientNote> shortPatientNoteList = shortPatientNoteRepository.findAll();
        assertThat(shortPatientNoteList).hasSize(databaseSizeBeforeCreate + 1);
        ShortPatientNote testShortPatientNote = shortPatientNoteList.get(shortPatientNoteList.size() - 1);
        assertThat(testShortPatientNote.getShortPatientNote()).isEqualTo(DEFAULT_SHORT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void createShortPatientNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shortPatientNoteRepository.findAll().size();

        // Create the ShortPatientNote with an existing ID
        shortPatientNote.setId(1L);
        ShortPatientNoteDTO shortPatientNoteDTO = shortPatientNoteMapper.toDto(shortPatientNote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShortPatientNoteMockMvc.perform(post("/api/short-patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shortPatientNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShortPatientNote in the database
        List<ShortPatientNote> shortPatientNoteList = shortPatientNoteRepository.findAll();
        assertThat(shortPatientNoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkShortPatientNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = shortPatientNoteRepository.findAll().size();
        // set the field null
        shortPatientNote.setShortPatientNote(null);

        // Create the ShortPatientNote, which fails.
        ShortPatientNoteDTO shortPatientNoteDTO = shortPatientNoteMapper.toDto(shortPatientNote);


        restShortPatientNoteMockMvc.perform(post("/api/short-patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shortPatientNoteDTO)))
            .andExpect(status().isBadRequest());

        List<ShortPatientNote> shortPatientNoteList = shortPatientNoteRepository.findAll();
        assertThat(shortPatientNoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShortPatientNotes() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shortPatientNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortPatientNote").value(hasItem(DEFAULT_SHORT_PATIENT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getShortPatientNote() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get the shortPatientNote
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes/{id}", shortPatientNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shortPatientNote.getId().intValue()))
            .andExpect(jsonPath("$.shortPatientNote").value(DEFAULT_SHORT_PATIENT_NOTE));
    }


    @Test
    @Transactional
    public void getShortPatientNotesByIdFiltering() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        Long id = shortPatientNote.getId();

        defaultShortPatientNoteShouldBeFound("id.equals=" + id);
        defaultShortPatientNoteShouldNotBeFound("id.notEquals=" + id);

        defaultShortPatientNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShortPatientNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultShortPatientNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShortPatientNoteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShortPatientNotesByShortPatientNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList where shortPatientNote equals to DEFAULT_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldBeFound("shortPatientNote.equals=" + DEFAULT_SHORT_PATIENT_NOTE);

        // Get all the shortPatientNoteList where shortPatientNote equals to UPDATED_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldNotBeFound("shortPatientNote.equals=" + UPDATED_SHORT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllShortPatientNotesByShortPatientNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList where shortPatientNote not equals to DEFAULT_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldNotBeFound("shortPatientNote.notEquals=" + DEFAULT_SHORT_PATIENT_NOTE);

        // Get all the shortPatientNoteList where shortPatientNote not equals to UPDATED_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldBeFound("shortPatientNote.notEquals=" + UPDATED_SHORT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllShortPatientNotesByShortPatientNoteIsInShouldWork() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList where shortPatientNote in DEFAULT_SHORT_PATIENT_NOTE or UPDATED_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldBeFound("shortPatientNote.in=" + DEFAULT_SHORT_PATIENT_NOTE + "," + UPDATED_SHORT_PATIENT_NOTE);

        // Get all the shortPatientNoteList where shortPatientNote equals to UPDATED_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldNotBeFound("shortPatientNote.in=" + UPDATED_SHORT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllShortPatientNotesByShortPatientNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList where shortPatientNote is not null
        defaultShortPatientNoteShouldBeFound("shortPatientNote.specified=true");

        // Get all the shortPatientNoteList where shortPatientNote is null
        defaultShortPatientNoteShouldNotBeFound("shortPatientNote.specified=false");
    }
                @Test
    @Transactional
    public void getAllShortPatientNotesByShortPatientNoteContainsSomething() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList where shortPatientNote contains DEFAULT_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldBeFound("shortPatientNote.contains=" + DEFAULT_SHORT_PATIENT_NOTE);

        // Get all the shortPatientNoteList where shortPatientNote contains UPDATED_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldNotBeFound("shortPatientNote.contains=" + UPDATED_SHORT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllShortPatientNotesByShortPatientNoteNotContainsSomething() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        // Get all the shortPatientNoteList where shortPatientNote does not contain DEFAULT_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldNotBeFound("shortPatientNote.doesNotContain=" + DEFAULT_SHORT_PATIENT_NOTE);

        // Get all the shortPatientNoteList where shortPatientNote does not contain UPDATED_SHORT_PATIENT_NOTE
        defaultShortPatientNoteShouldBeFound("shortPatientNote.doesNotContain=" + UPDATED_SHORT_PATIENT_NOTE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShortPatientNoteShouldBeFound(String filter) throws Exception {
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shortPatientNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortPatientNote").value(hasItem(DEFAULT_SHORT_PATIENT_NOTE)));

        // Check, that the count call also returns 1
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShortPatientNoteShouldNotBeFound(String filter) throws Exception {
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingShortPatientNote() throws Exception {
        // Get the shortPatientNote
        restShortPatientNoteMockMvc.perform(get("/api/short-patient-notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShortPatientNote() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        int databaseSizeBeforeUpdate = shortPatientNoteRepository.findAll().size();

        // Update the shortPatientNote
        ShortPatientNote updatedShortPatientNote = shortPatientNoteRepository.findById(shortPatientNote.getId()).get();
        // Disconnect from session so that the updates on updatedShortPatientNote are not directly saved in db
        em.detach(updatedShortPatientNote);
        updatedShortPatientNote
            .shortPatientNote(UPDATED_SHORT_PATIENT_NOTE);
        ShortPatientNoteDTO shortPatientNoteDTO = shortPatientNoteMapper.toDto(updatedShortPatientNote);

        restShortPatientNoteMockMvc.perform(put("/api/short-patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shortPatientNoteDTO)))
            .andExpect(status().isOk());

        // Validate the ShortPatientNote in the database
        List<ShortPatientNote> shortPatientNoteList = shortPatientNoteRepository.findAll();
        assertThat(shortPatientNoteList).hasSize(databaseSizeBeforeUpdate);
        ShortPatientNote testShortPatientNote = shortPatientNoteList.get(shortPatientNoteList.size() - 1);
        assertThat(testShortPatientNote.getShortPatientNote()).isEqualTo(UPDATED_SHORT_PATIENT_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingShortPatientNote() throws Exception {
        int databaseSizeBeforeUpdate = shortPatientNoteRepository.findAll().size();

        // Create the ShortPatientNote
        ShortPatientNoteDTO shortPatientNoteDTO = shortPatientNoteMapper.toDto(shortPatientNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShortPatientNoteMockMvc.perform(put("/api/short-patient-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shortPatientNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShortPatientNote in the database
        List<ShortPatientNote> shortPatientNoteList = shortPatientNoteRepository.findAll();
        assertThat(shortPatientNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShortPatientNote() throws Exception {
        // Initialize the database
        shortPatientNoteRepository.saveAndFlush(shortPatientNote);

        int databaseSizeBeforeDelete = shortPatientNoteRepository.findAll().size();

        // Delete the shortPatientNote
        restShortPatientNoteMockMvc.perform(delete("/api/short-patient-notes/{id}", shortPatientNote.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShortPatientNote> shortPatientNoteList = shortPatientNoteRepository.findAll();
        assertThat(shortPatientNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
