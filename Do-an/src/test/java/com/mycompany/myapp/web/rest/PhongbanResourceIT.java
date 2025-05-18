package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PhongbanAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Phongban;
import com.mycompany.myapp.repository.PhongbanRepository;
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
 * Integration tests for the {@link PhongbanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhongbanResourceIT {

    private static final String DEFAULT_MAPB = "AAAAAAAAAA";
    private static final String UPDATED_MAPB = "BBBBBBBBBB";

    private static final String DEFAULT_TENPB = "AAAAAAAAAA";
    private static final String UPDATED_TENPB = "BBBBBBBBBB";

    private static final Integer DEFAULT_SDT = 1;
    private static final Integer UPDATED_SDT = 2;

    private static final String ENTITY_API_URL = "/api/phongbans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PhongbanRepository phongbanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhongbanMockMvc;

    private Phongban phongban;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phongban createEntity(EntityManager em) {
        Phongban phongban = new Phongban().mapb(DEFAULT_MAPB).tenpb(DEFAULT_TENPB).sdt(DEFAULT_SDT);
        return phongban;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phongban createUpdatedEntity(EntityManager em) {
        Phongban phongban = new Phongban().mapb(UPDATED_MAPB).tenpb(UPDATED_TENPB).sdt(UPDATED_SDT);
        return phongban;
    }

    @BeforeEach
    public void initTest() {
        phongban = createEntity(em);
    }

    @Test
    @Transactional
    void createPhongban() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Phongban
        var returnedPhongban = om.readValue(
            restPhongbanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phongban)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Phongban.class
        );

        // Validate the Phongban in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPhongbanUpdatableFieldsEquals(returnedPhongban, getPersistedPhongban(returnedPhongban));
    }

    @Test
    @Transactional
    void createPhongbanWithExistingId() throws Exception {
        // Create the Phongban with an existing ID
        phongban.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongbanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phongban)))
            .andExpect(status().isBadRequest());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhongbans() throws Exception {
        // Initialize the database
        phongbanRepository.saveAndFlush(phongban);

        // Get all the phongbanList
        restPhongbanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongban.getId().intValue())))
            .andExpect(jsonPath("$.[*].mapb").value(hasItem(DEFAULT_MAPB)))
            .andExpect(jsonPath("$.[*].tenpb").value(hasItem(DEFAULT_TENPB)))
            .andExpect(jsonPath("$.[*].sdt").value(hasItem(DEFAULT_SDT)));
    }

    @Test
    @Transactional
    void getPhongban() throws Exception {
        // Initialize the database
        phongbanRepository.saveAndFlush(phongban);

        // Get the phongban
        restPhongbanMockMvc
            .perform(get(ENTITY_API_URL_ID, phongban.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongban.getId().intValue()))
            .andExpect(jsonPath("$.mapb").value(DEFAULT_MAPB))
            .andExpect(jsonPath("$.tenpb").value(DEFAULT_TENPB))
            .andExpect(jsonPath("$.sdt").value(DEFAULT_SDT));
    }

    @Test
    @Transactional
    void getNonExistingPhongban() throws Exception {
        // Get the phongban
        restPhongbanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPhongban() throws Exception {
        // Initialize the database
        phongbanRepository.saveAndFlush(phongban);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the phongban
        Phongban updatedPhongban = phongbanRepository.findById(phongban.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPhongban are not directly saved in db
        em.detach(updatedPhongban);
        updatedPhongban.mapb(UPDATED_MAPB).tenpb(UPDATED_TENPB).sdt(UPDATED_SDT);

        restPhongbanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongban.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPhongban))
            )
            .andExpect(status().isOk());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPhongbanToMatchAllProperties(updatedPhongban);
    }

    @Test
    @Transactional
    void putNonExistingPhongban() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phongban.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongbanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongban.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phongban))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhongban() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phongban.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongbanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(phongban))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhongban() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phongban.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongbanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(phongban)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhongbanWithPatch() throws Exception {
        // Initialize the database
        phongbanRepository.saveAndFlush(phongban);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the phongban using partial update
        Phongban partialUpdatedPhongban = new Phongban();
        partialUpdatedPhongban.setId(phongban.getId());

        partialUpdatedPhongban.tenpb(UPDATED_TENPB).sdt(UPDATED_SDT);

        restPhongbanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongban.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPhongban))
            )
            .andExpect(status().isOk());

        // Validate the Phongban in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPhongbanUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPhongban, phongban), getPersistedPhongban(phongban));
    }

    @Test
    @Transactional
    void fullUpdatePhongbanWithPatch() throws Exception {
        // Initialize the database
        phongbanRepository.saveAndFlush(phongban);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the phongban using partial update
        Phongban partialUpdatedPhongban = new Phongban();
        partialUpdatedPhongban.setId(phongban.getId());

        partialUpdatedPhongban.mapb(UPDATED_MAPB).tenpb(UPDATED_TENPB).sdt(UPDATED_SDT);

        restPhongbanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongban.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPhongban))
            )
            .andExpect(status().isOk());

        // Validate the Phongban in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPhongbanUpdatableFieldsEquals(partialUpdatedPhongban, getPersistedPhongban(partialUpdatedPhongban));
    }

    @Test
    @Transactional
    void patchNonExistingPhongban() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phongban.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongbanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongban.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(phongban))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhongban() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phongban.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongbanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(phongban))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhongban() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        phongban.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongbanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(phongban)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phongban in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhongban() throws Exception {
        // Initialize the database
        phongbanRepository.saveAndFlush(phongban);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the phongban
        restPhongbanMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongban.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return phongbanRepository.count();
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

    protected Phongban getPersistedPhongban(Phongban phongban) {
        return phongbanRepository.findById(phongban.getId()).orElseThrow();
    }

    protected void assertPersistedPhongbanToMatchAllProperties(Phongban expectedPhongban) {
        assertPhongbanAllPropertiesEquals(expectedPhongban, getPersistedPhongban(expectedPhongban));
    }

    protected void assertPersistedPhongbanToMatchUpdatableProperties(Phongban expectedPhongban) {
        assertPhongbanAllUpdatablePropertiesEquals(expectedPhongban, getPersistedPhongban(expectedPhongban));
    }
}
