package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.TanggiamtlAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Tanggiamtl;
import com.mycompany.myapp.repository.TanggiamtlRepository;
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
 * Integration tests for the {@link TanggiamtlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TanggiamtlResourceIT {

    private static final String DEFAULT_NGAYTHANG = "AAAAAAAAAA";
    private static final String UPDATED_NGAYTHANG = "BBBBBBBBBB";

    private static final Integer DEFAULT_TKN = 1;
    private static final Integer UPDATED_TKN = 2;

    private static final Integer DEFAULT_TKC = 1;
    private static final Integer UPDATED_TKC = 2;

    private static final Float DEFAULT_SOTIEN = 1F;
    private static final Float UPDATED_SOTIEN = 2F;

    private static final String DEFAULT_DIENGIAI = "AAAAAAAAAA";
    private static final String UPDATED_DIENGIAI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tanggiamtls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TanggiamtlRepository tanggiamtlRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTanggiamtlMockMvc;

    private Tanggiamtl tanggiamtl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tanggiamtl createEntity(EntityManager em) {
        Tanggiamtl tanggiamtl = new Tanggiamtl()
            .ngaythang(DEFAULT_NGAYTHANG)
            .tkn(DEFAULT_TKN)
            .tkc(DEFAULT_TKC)
            .sotien(DEFAULT_SOTIEN)
            .diengiai(DEFAULT_DIENGIAI);
        return tanggiamtl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tanggiamtl createUpdatedEntity(EntityManager em) {
        Tanggiamtl tanggiamtl = new Tanggiamtl()
            .ngaythang(UPDATED_NGAYTHANG)
            .tkn(UPDATED_TKN)
            .tkc(UPDATED_TKC)
            .sotien(UPDATED_SOTIEN)
            .diengiai(UPDATED_DIENGIAI);
        return tanggiamtl;
    }

    @BeforeEach
    public void initTest() {
        tanggiamtl = createEntity(em);
    }

    @Test
    @Transactional
    void createTanggiamtl() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tanggiamtl
        var returnedTanggiamtl = om.readValue(
            restTanggiamtlMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tanggiamtl)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tanggiamtl.class
        );

        // Validate the Tanggiamtl in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTanggiamtlUpdatableFieldsEquals(returnedTanggiamtl, getPersistedTanggiamtl(returnedTanggiamtl));
    }

    @Test
    @Transactional
    void createTanggiamtlWithExistingId() throws Exception {
        // Create the Tanggiamtl with an existing ID
        tanggiamtl.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTanggiamtlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tanggiamtl)))
            .andExpect(status().isBadRequest());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTanggiamtls() throws Exception {
        // Initialize the database
        tanggiamtlRepository.saveAndFlush(tanggiamtl);

        // Get all the tanggiamtlList
        restTanggiamtlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tanggiamtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngaythang").value(hasItem(DEFAULT_NGAYTHANG)))
            .andExpect(jsonPath("$.[*].tkn").value(hasItem(DEFAULT_TKN)))
            .andExpect(jsonPath("$.[*].tkc").value(hasItem(DEFAULT_TKC)))
            .andExpect(jsonPath("$.[*].sotien").value(hasItem(DEFAULT_SOTIEN.doubleValue())))
            .andExpect(jsonPath("$.[*].diengiai").value(hasItem(DEFAULT_DIENGIAI)));
    }

    @Test
    @Transactional
    void getTanggiamtl() throws Exception {
        // Initialize the database
        tanggiamtlRepository.saveAndFlush(tanggiamtl);

        // Get the tanggiamtl
        restTanggiamtlMockMvc
            .perform(get(ENTITY_API_URL_ID, tanggiamtl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tanggiamtl.getId().intValue()))
            .andExpect(jsonPath("$.ngaythang").value(DEFAULT_NGAYTHANG))
            .andExpect(jsonPath("$.tkn").value(DEFAULT_TKN))
            .andExpect(jsonPath("$.tkc").value(DEFAULT_TKC))
            .andExpect(jsonPath("$.sotien").value(DEFAULT_SOTIEN.doubleValue()))
            .andExpect(jsonPath("$.diengiai").value(DEFAULT_DIENGIAI));
    }

    @Test
    @Transactional
    void getNonExistingTanggiamtl() throws Exception {
        // Get the tanggiamtl
        restTanggiamtlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTanggiamtl() throws Exception {
        // Initialize the database
        tanggiamtlRepository.saveAndFlush(tanggiamtl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tanggiamtl
        Tanggiamtl updatedTanggiamtl = tanggiamtlRepository.findById(tanggiamtl.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTanggiamtl are not directly saved in db
        em.detach(updatedTanggiamtl);
        updatedTanggiamtl.ngaythang(UPDATED_NGAYTHANG).tkn(UPDATED_TKN).tkc(UPDATED_TKC).sotien(UPDATED_SOTIEN).diengiai(UPDATED_DIENGIAI);

        restTanggiamtlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTanggiamtl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTanggiamtl))
            )
            .andExpect(status().isOk());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTanggiamtlToMatchAllProperties(updatedTanggiamtl);
    }

    @Test
    @Transactional
    void putNonExistingTanggiamtl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tanggiamtl.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTanggiamtlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tanggiamtl.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tanggiamtl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTanggiamtl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tanggiamtl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTanggiamtlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tanggiamtl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTanggiamtl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tanggiamtl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTanggiamtlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tanggiamtl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTanggiamtlWithPatch() throws Exception {
        // Initialize the database
        tanggiamtlRepository.saveAndFlush(tanggiamtl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tanggiamtl using partial update
        Tanggiamtl partialUpdatedTanggiamtl = new Tanggiamtl();
        partialUpdatedTanggiamtl.setId(tanggiamtl.getId());

        partialUpdatedTanggiamtl.tkc(UPDATED_TKC).sotien(UPDATED_SOTIEN);

        restTanggiamtlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTanggiamtl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTanggiamtl))
            )
            .andExpect(status().isOk());

        // Validate the Tanggiamtl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTanggiamtlUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTanggiamtl, tanggiamtl),
            getPersistedTanggiamtl(tanggiamtl)
        );
    }

    @Test
    @Transactional
    void fullUpdateTanggiamtlWithPatch() throws Exception {
        // Initialize the database
        tanggiamtlRepository.saveAndFlush(tanggiamtl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tanggiamtl using partial update
        Tanggiamtl partialUpdatedTanggiamtl = new Tanggiamtl();
        partialUpdatedTanggiamtl.setId(tanggiamtl.getId());

        partialUpdatedTanggiamtl
            .ngaythang(UPDATED_NGAYTHANG)
            .tkn(UPDATED_TKN)
            .tkc(UPDATED_TKC)
            .sotien(UPDATED_SOTIEN)
            .diengiai(UPDATED_DIENGIAI);

        restTanggiamtlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTanggiamtl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTanggiamtl))
            )
            .andExpect(status().isOk());

        // Validate the Tanggiamtl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTanggiamtlUpdatableFieldsEquals(partialUpdatedTanggiamtl, getPersistedTanggiamtl(partialUpdatedTanggiamtl));
    }

    @Test
    @Transactional
    void patchNonExistingTanggiamtl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tanggiamtl.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTanggiamtlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tanggiamtl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tanggiamtl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTanggiamtl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tanggiamtl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTanggiamtlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tanggiamtl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTanggiamtl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tanggiamtl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTanggiamtlMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tanggiamtl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tanggiamtl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTanggiamtl() throws Exception {
        // Initialize the database
        tanggiamtlRepository.saveAndFlush(tanggiamtl);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tanggiamtl
        restTanggiamtlMockMvc
            .perform(delete(ENTITY_API_URL_ID, tanggiamtl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tanggiamtlRepository.count();
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

    protected Tanggiamtl getPersistedTanggiamtl(Tanggiamtl tanggiamtl) {
        return tanggiamtlRepository.findById(tanggiamtl.getId()).orElseThrow();
    }

    protected void assertPersistedTanggiamtlToMatchAllProperties(Tanggiamtl expectedTanggiamtl) {
        assertTanggiamtlAllPropertiesEquals(expectedTanggiamtl, getPersistedTanggiamtl(expectedTanggiamtl));
    }

    protected void assertPersistedTanggiamtlToMatchUpdatableProperties(Tanggiamtl expectedTanggiamtl) {
        assertTanggiamtlAllUpdatablePropertiesEquals(expectedTanggiamtl, getPersistedTanggiamtl(expectedTanggiamtl));
    }
}
