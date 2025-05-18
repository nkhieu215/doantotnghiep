package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ThuetnAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Thuetn;
import com.mycompany.myapp.repository.ThuetnRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ThuetnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThuetnResourceIT {

    private static final String DEFAULT_BACTHUE = "AAAAAAAAAA";
    private static final String UPDATED_BACTHUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TU = 1;
    private static final Integer UPDATED_TU = 2;

    private static final Integer DEFAULT_DEN = 1;
    private static final Integer UPDATED_DEN = 2;

    private static final Float DEFAULT_THUESUAT = 1F;
    private static final Float UPDATED_THUESUAT = 2F;

    private static final String ENTITY_API_URL = "/api/thuetns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThuetnRepository thuetnRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThuetnMockMvc;

    private Thuetn thuetn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thuetn createEntity(EntityManager em) {
        Thuetn thuetn = new Thuetn().bacthue(DEFAULT_BACTHUE).tu(DEFAULT_TU).den(DEFAULT_DEN).thuesuat(DEFAULT_THUESUAT);
        return thuetn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thuetn createUpdatedEntity(EntityManager em) {
        Thuetn thuetn = new Thuetn().bacthue(UPDATED_BACTHUE).tu(UPDATED_TU).den(UPDATED_DEN).thuesuat(UPDATED_THUESUAT);
        return thuetn;
    }

    @BeforeEach
    public void initTest() {
        thuetn = createEntity(em);
    }

    @Test
    @Transactional
    void createThuetn() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Thuetn
        var returnedThuetn = om.readValue(
            restThuetnMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thuetn)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Thuetn.class
        );

        // Validate the Thuetn in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThuetnUpdatableFieldsEquals(returnedThuetn, getPersistedThuetn(returnedThuetn));
    }

    @Test
    @Transactional
    void createThuetnWithExistingId() throws Exception {
        // Create the Thuetn with an existing ID
        thuetn.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThuetnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thuetn)))
            .andExpect(status().isBadRequest());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThuetns() throws Exception {
        // Initialize the database
        thuetnRepository.saveAndFlush(thuetn);

        // Get all the thuetnList
        restThuetnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thuetn.getId().intValue())))
            .andExpect(jsonPath("$.[*].bacthue").value(hasItem(DEFAULT_BACTHUE)))
            .andExpect(jsonPath("$.[*].tu").value(hasItem(DEFAULT_TU)))
            .andExpect(jsonPath("$.[*].den").value(hasItem(DEFAULT_DEN)))
            .andExpect(jsonPath("$.[*].thuesuat").value(hasItem(DEFAULT_THUESUAT.doubleValue())));
    }

    @Test
    @Transactional
    void getThuetn() throws Exception {
        // Initialize the database
        thuetnRepository.saveAndFlush(thuetn);

        // Get the thuetn
        restThuetnMockMvc
            .perform(get(ENTITY_API_URL_ID, thuetn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thuetn.getId().intValue()))
            .andExpect(jsonPath("$.bacthue").value(DEFAULT_BACTHUE))
            .andExpect(jsonPath("$.tu").value(DEFAULT_TU))
            .andExpect(jsonPath("$.den").value(DEFAULT_DEN))
            .andExpect(jsonPath("$.thuesuat").value(DEFAULT_THUESUAT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingThuetn() throws Exception {
        // Get the thuetn
        restThuetnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThuetn() throws Exception {
        // Initialize the database
        thuetnRepository.saveAndFlush(thuetn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thuetn
        Thuetn updatedThuetn = thuetnRepository.findById(thuetn.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedThuetn are not directly saved in db
        em.detach(updatedThuetn);
        updatedThuetn.bacthue(UPDATED_BACTHUE).tu(UPDATED_TU).den(UPDATED_DEN).thuesuat(UPDATED_THUESUAT);

        restThuetnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThuetn.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedThuetn))
            )
            .andExpect(status().isOk());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThuetnToMatchAllProperties(updatedThuetn);
    }

    @Test
    @Transactional
    void putNonExistingThuetn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thuetn.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThuetnMockMvc
            .perform(put(ENTITY_API_URL_ID, thuetn.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thuetn)))
            .andExpect(status().isBadRequest());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThuetn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thuetn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuetnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thuetn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThuetn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thuetn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuetnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thuetn)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThuetnWithPatch() throws Exception {
        // Initialize the database
        thuetnRepository.saveAndFlush(thuetn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thuetn using partial update
        Thuetn partialUpdatedThuetn = new Thuetn();
        partialUpdatedThuetn.setId(thuetn.getId());

        partialUpdatedThuetn.bacthue(UPDATED_BACTHUE).tu(UPDATED_TU).thuesuat(UPDATED_THUESUAT);

        restThuetnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThuetn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThuetn))
            )
            .andExpect(status().isOk());

        // Validate the Thuetn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThuetnUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedThuetn, thuetn), getPersistedThuetn(thuetn));
    }

    @Test
    @Transactional
    void fullUpdateThuetnWithPatch() throws Exception {
        // Initialize the database
        thuetnRepository.saveAndFlush(thuetn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thuetn using partial update
        Thuetn partialUpdatedThuetn = new Thuetn();
        partialUpdatedThuetn.setId(thuetn.getId());

        partialUpdatedThuetn.bacthue(UPDATED_BACTHUE).tu(UPDATED_TU).den(UPDATED_DEN).thuesuat(UPDATED_THUESUAT);

        restThuetnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThuetn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThuetn))
            )
            .andExpect(status().isOk());

        // Validate the Thuetn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThuetnUpdatableFieldsEquals(partialUpdatedThuetn, getPersistedThuetn(partialUpdatedThuetn));
    }

    @Test
    @Transactional
    void patchNonExistingThuetn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thuetn.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThuetnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thuetn.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thuetn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThuetn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thuetn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuetnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thuetn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThuetn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thuetn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuetnMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thuetn)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thuetn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThuetn() throws Exception {
        // Initialize the database
        thuetnRepository.saveAndFlush(thuetn);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thuetn
        restThuetnMockMvc
            .perform(delete(ENTITY_API_URL_ID, thuetn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thuetnRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Thuetn getPersistedThuetn(Thuetn thuetn) {
        return thuetnRepository.findById(thuetn.getId()).orElseThrow();
    }

    protected void assertPersistedThuetnToMatchAllProperties(Thuetn expectedThuetn) {
        assertThuetnAllPropertiesEquals(expectedThuetn, getPersistedThuetn(expectedThuetn));
    }

    protected void assertPersistedThuetnToMatchUpdatableProperties(Thuetn expectedThuetn) {
        assertThuetnAllUpdatablePropertiesEquals(expectedThuetn, getPersistedThuetn(expectedThuetn));
    }
}
