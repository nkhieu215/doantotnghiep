package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ChucvuAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Chucvu;
import com.mycompany.myapp.repository.ChucvuRepository;
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
 * Integration tests for the {@link ChucvuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChucvuResourceIT {

    private static final String DEFAULT_MACV = "AAAAAAAAAA";
    private static final String UPDATED_MACV = "BBBBBBBBBB";

    private static final String DEFAULT_TENCV = "AAAAAAAAAA";
    private static final String UPDATED_TENCV = "BBBBBBBBBB";

    private static final Integer DEFAULT_HCPCCV = 1;
    private static final Integer UPDATED_HCPCCV = 2;

    private static final String ENTITY_API_URL = "/api/chucvus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChucvuRepository chucvuRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChucvuMockMvc;

    private Chucvu chucvu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chucvu createEntity(EntityManager em) {
        Chucvu chucvu = new Chucvu().macv(DEFAULT_MACV).tencv(DEFAULT_TENCV).hcpccv(DEFAULT_HCPCCV);
        return chucvu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chucvu createUpdatedEntity(EntityManager em) {
        Chucvu chucvu = new Chucvu().macv(UPDATED_MACV).tencv(UPDATED_TENCV).hcpccv(UPDATED_HCPCCV);
        return chucvu;
    }

    @BeforeEach
    public void initTest() {
        chucvu = createEntity(em);
    }

    @Test
    @Transactional
    void createChucvu() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Chucvu
        var returnedChucvu = om.readValue(
            restChucvuMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chucvu)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Chucvu.class
        );

        // Validate the Chucvu in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertChucvuUpdatableFieldsEquals(returnedChucvu, getPersistedChucvu(returnedChucvu));
    }

    @Test
    @Transactional
    void createChucvuWithExistingId() throws Exception {
        // Create the Chucvu with an existing ID
        chucvu.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChucvuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chucvu)))
            .andExpect(status().isBadRequest());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChucvus() throws Exception {
        // Initialize the database
        chucvuRepository.saveAndFlush(chucvu);

        // Get all the chucvuList
        restChucvuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chucvu.getId().intValue())))
            .andExpect(jsonPath("$.[*].macv").value(hasItem(DEFAULT_MACV)))
            .andExpect(jsonPath("$.[*].tencv").value(hasItem(DEFAULT_TENCV)))
            .andExpect(jsonPath("$.[*].hcpccv").value(hasItem(DEFAULT_HCPCCV)));
    }

    @Test
    @Transactional
    void getChucvu() throws Exception {
        // Initialize the database
        chucvuRepository.saveAndFlush(chucvu);

        // Get the chucvu
        restChucvuMockMvc
            .perform(get(ENTITY_API_URL_ID, chucvu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chucvu.getId().intValue()))
            .andExpect(jsonPath("$.macv").value(DEFAULT_MACV))
            .andExpect(jsonPath("$.tencv").value(DEFAULT_TENCV))
            .andExpect(jsonPath("$.hcpccv").value(DEFAULT_HCPCCV));
    }

    @Test
    @Transactional
    void getNonExistingChucvu() throws Exception {
        // Get the chucvu
        restChucvuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChucvu() throws Exception {
        // Initialize the database
        chucvuRepository.saveAndFlush(chucvu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chucvu
        Chucvu updatedChucvu = chucvuRepository.findById(chucvu.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedChucvu are not directly saved in db
        em.detach(updatedChucvu);
        updatedChucvu.macv(UPDATED_MACV).tencv(UPDATED_TENCV).hcpccv(UPDATED_HCPCCV);

        restChucvuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChucvu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedChucvu))
            )
            .andExpect(status().isOk());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChucvuToMatchAllProperties(updatedChucvu);
    }

    @Test
    @Transactional
    void putNonExistingChucvu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chucvu.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChucvuMockMvc
            .perform(put(ENTITY_API_URL_ID, chucvu.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chucvu)))
            .andExpect(status().isBadRequest());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChucvu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chucvu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucvuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(chucvu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChucvu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chucvu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucvuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chucvu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChucvuWithPatch() throws Exception {
        // Initialize the database
        chucvuRepository.saveAndFlush(chucvu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chucvu using partial update
        Chucvu partialUpdatedChucvu = new Chucvu();
        partialUpdatedChucvu.setId(chucvu.getId());

        partialUpdatedChucvu.tencv(UPDATED_TENCV);

        restChucvuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChucvu.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChucvu))
            )
            .andExpect(status().isOk());

        // Validate the Chucvu in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChucvuUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedChucvu, chucvu), getPersistedChucvu(chucvu));
    }

    @Test
    @Transactional
    void fullUpdateChucvuWithPatch() throws Exception {
        // Initialize the database
        chucvuRepository.saveAndFlush(chucvu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the chucvu using partial update
        Chucvu partialUpdatedChucvu = new Chucvu();
        partialUpdatedChucvu.setId(chucvu.getId());

        partialUpdatedChucvu.macv(UPDATED_MACV).tencv(UPDATED_TENCV).hcpccv(UPDATED_HCPCCV);

        restChucvuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChucvu.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChucvu))
            )
            .andExpect(status().isOk());

        // Validate the Chucvu in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChucvuUpdatableFieldsEquals(partialUpdatedChucvu, getPersistedChucvu(partialUpdatedChucvu));
    }

    @Test
    @Transactional
    void patchNonExistingChucvu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chucvu.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChucvuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chucvu.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(chucvu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChucvu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chucvu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucvuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(chucvu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChucvu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        chucvu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChucvuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(chucvu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chucvu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChucvu() throws Exception {
        // Initialize the database
        chucvuRepository.saveAndFlush(chucvu);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the chucvu
        restChucvuMockMvc
            .perform(delete(ENTITY_API_URL_ID, chucvu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return chucvuRepository.count();
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

    protected Chucvu getPersistedChucvu(Chucvu chucvu) {
        return chucvuRepository.findById(chucvu.getId()).orElseThrow();
    }

    protected void assertPersistedChucvuToMatchAllProperties(Chucvu expectedChucvu) {
        assertChucvuAllPropertiesEquals(expectedChucvu, getPersistedChucvu(expectedChucvu));
    }

    protected void assertPersistedChucvuToMatchUpdatableProperties(Chucvu expectedChucvu) {
        assertChucvuAllUpdatablePropertiesEquals(expectedChucvu, getPersistedChucvu(expectedChucvu));
    }
}
