package com.hiraparl.hekimmaster.web.rest;

import com.hiraparl.hekimmaster.HekimmasterApp;
import com.hiraparl.hekimmaster.domain.Day;
import com.hiraparl.hekimmaster.repository.DayRepository;
import com.hiraparl.hekimmaster.service.DayService;
import com.hiraparl.hekimmaster.service.dto.DayDTO;
import com.hiraparl.hekimmaster.service.mapper.DayMapper;
import com.hiraparl.hekimmaster.service.dto.DayCriteria;
import com.hiraparl.hekimmaster.service.DayQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hiraparl.hekimmaster.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DayResource} REST controller.
 */
@SpringBootTest(classes = HekimmasterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DayResourceIT {

    private static final ZonedDateTime DEFAULT_RANDEVU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RANDEVU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RANDEVU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private DayMapper dayMapper;

    @Autowired
    private DayService dayService;

    @Autowired
    private DayQueryService dayQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDayMockMvc;

    private Day day;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Day createEntity(EntityManager em) {
        Day day = new Day()
            .randevuDate(DEFAULT_RANDEVU_DATE);
        return day;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Day createUpdatedEntity(EntityManager em) {
        Day day = new Day()
            .randevuDate(UPDATED_RANDEVU_DATE);
        return day;
    }

    @BeforeEach
    public void initTest() {
        day = createEntity(em);
    }

    @Test
    @Transactional
    public void createDay() throws Exception {
        int databaseSizeBeforeCreate = dayRepository.findAll().size();
        // Create the Day
        DayDTO dayDTO = dayMapper.toDto(day);
        restDayMockMvc.perform(post("/api/days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isCreated());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeCreate + 1);
        Day testDay = dayList.get(dayList.size() - 1);
        assertThat(testDay.getRandevuDate()).isEqualTo(DEFAULT_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void createDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayRepository.findAll().size();

        // Create the Day with an existing ID
        day.setId(1L);
        DayDTO dayDTO = dayMapper.toDto(day);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayMockMvc.perform(post("/api/days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRandevuDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayRepository.findAll().size();
        // set the field null
        day.setRandevuDate(null);

        // Create the Day, which fails.
        DayDTO dayDTO = dayMapper.toDto(day);


        restDayMockMvc.perform(post("/api/days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isBadRequest());

        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDays() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList
        restDayMockMvc.perform(get("/api/days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(day.getId().intValue())))
            .andExpect(jsonPath("$.[*].randevuDate").value(hasItem(sameInstant(DEFAULT_RANDEVU_DATE))));
    }
    
    @Test
    @Transactional
    public void getDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get the day
        restDayMockMvc.perform(get("/api/days/{id}", day.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(day.getId().intValue()))
            .andExpect(jsonPath("$.randevuDate").value(sameInstant(DEFAULT_RANDEVU_DATE)));
    }


    @Test
    @Transactional
    public void getDaysByIdFiltering() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        Long id = day.getId();

        defaultDayShouldBeFound("id.equals=" + id);
        defaultDayShouldNotBeFound("id.notEquals=" + id);

        defaultDayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDayShouldNotBeFound("id.greaterThan=" + id);

        defaultDayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDayShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate equals to DEFAULT_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.equals=" + DEFAULT_RANDEVU_DATE);

        // Get all the dayList where randevuDate equals to UPDATED_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.equals=" + UPDATED_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate not equals to DEFAULT_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.notEquals=" + DEFAULT_RANDEVU_DATE);

        // Get all the dayList where randevuDate not equals to UPDATED_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.notEquals=" + UPDATED_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsInShouldWork() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate in DEFAULT_RANDEVU_DATE or UPDATED_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.in=" + DEFAULT_RANDEVU_DATE + "," + UPDATED_RANDEVU_DATE);

        // Get all the dayList where randevuDate equals to UPDATED_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.in=" + UPDATED_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate is not null
        defaultDayShouldBeFound("randevuDate.specified=true");

        // Get all the dayList where randevuDate is null
        defaultDayShouldNotBeFound("randevuDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate is greater than or equal to DEFAULT_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.greaterThanOrEqual=" + DEFAULT_RANDEVU_DATE);

        // Get all the dayList where randevuDate is greater than or equal to UPDATED_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.greaterThanOrEqual=" + UPDATED_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate is less than or equal to DEFAULT_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.lessThanOrEqual=" + DEFAULT_RANDEVU_DATE);

        // Get all the dayList where randevuDate is less than or equal to SMALLER_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.lessThanOrEqual=" + SMALLER_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate is less than DEFAULT_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.lessThan=" + DEFAULT_RANDEVU_DATE);

        // Get all the dayList where randevuDate is less than UPDATED_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.lessThan=" + UPDATED_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void getAllDaysByRandevuDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList where randevuDate is greater than DEFAULT_RANDEVU_DATE
        defaultDayShouldNotBeFound("randevuDate.greaterThan=" + DEFAULT_RANDEVU_DATE);

        // Get all the dayList where randevuDate is greater than SMALLER_RANDEVU_DATE
        defaultDayShouldBeFound("randevuDate.greaterThan=" + SMALLER_RANDEVU_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDayShouldBeFound(String filter) throws Exception {
        restDayMockMvc.perform(get("/api/days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(day.getId().intValue())))
            .andExpect(jsonPath("$.[*].randevuDate").value(hasItem(sameInstant(DEFAULT_RANDEVU_DATE))));

        // Check, that the count call also returns 1
        restDayMockMvc.perform(get("/api/days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDayShouldNotBeFound(String filter) throws Exception {
        restDayMockMvc.perform(get("/api/days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDayMockMvc.perform(get("/api/days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDay() throws Exception {
        // Get the day
        restDayMockMvc.perform(get("/api/days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        int databaseSizeBeforeUpdate = dayRepository.findAll().size();

        // Update the day
        Day updatedDay = dayRepository.findById(day.getId()).get();
        // Disconnect from session so that the updates on updatedDay are not directly saved in db
        em.detach(updatedDay);
        updatedDay
            .randevuDate(UPDATED_RANDEVU_DATE);
        DayDTO dayDTO = dayMapper.toDto(updatedDay);

        restDayMockMvc.perform(put("/api/days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isOk());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeUpdate);
        Day testDay = dayList.get(dayList.size() - 1);
        assertThat(testDay.getRandevuDate()).isEqualTo(UPDATED_RANDEVU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDay() throws Exception {
        int databaseSizeBeforeUpdate = dayRepository.findAll().size();

        // Create the Day
        DayDTO dayDTO = dayMapper.toDto(day);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayMockMvc.perform(put("/api/days")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        int databaseSizeBeforeDelete = dayRepository.findAll().size();

        // Delete the day
        restDayMockMvc.perform(delete("/api/days/{id}", day.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
