package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ThamsotlAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Thamsotl;
import com.mycompany.myapp.repository.ThamsotlRepository;
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
 * Integration tests for the {@link ThamsotlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThamsotlResourceIT {

    private static final String DEFAULT_THANGNAM = "AAAAAAAAAA";
    private static final String UPDATED_THANGNAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_NCCHUAN = 1;
    private static final Integer UPDATED_NCCHUAN = 2;

    private static final Integer DEFAULT_GIOCCHUAN = 1;
    private static final Integer UPDATED_GIOCCHUAN = 2;

    private static final Float DEFAULT_HSGIOTH = 1F;
    private static final Float UPDATED_HSGIOTH = 2F;

    private static final Float DEFAULT_HSGIOLE = 1F;
    private static final Float UPDATED_HSGIOLE = 2F;

    private static final Integer DEFAULT_PCAN = 1;
    private static final Integer UPDATED_PCAN = 2;

    private static final Float DEFAULT_TLBHXH = 1F;
    private static final Float UPDATED_TLBHXH = 2F;

    private static final Float DEFAULT_TLBHYT = 1F;
    private static final Float UPDATED_TLBHYT = 2F;

    private static final String DEFAULT_TLBHTN = "AAAAAAAAAA";
    private static final String UPDATED_TLBHTN = "BBBBBBBBBB";

    private static final Float DEFAULT_TLKPCD = 1F;
    private static final Float UPDATED_TLKPCD = 2F;

    private static final String ENTITY_API_URL = "/api/thamsotls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThamsotlRepository thamsotlRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThamsotlMockMvc;

    private Thamsotl thamsotl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thamsotl createEntity(EntityManager em) {
        Thamsotl thamsotl = new Thamsotl()
            .thangnam(DEFAULT_THANGNAM)
            .ncchuan(DEFAULT_NCCHUAN)
            .giocchuan(DEFAULT_GIOCCHUAN)
            .hsgioth(DEFAULT_HSGIOTH)
            .hsgiole(DEFAULT_HSGIOLE)
            .pcan(DEFAULT_PCAN)
            .tlbhxh(DEFAULT_TLBHXH)
            .tlbhyt(DEFAULT_TLBHYT)
            .tlbhtn(DEFAULT_TLBHTN)
            .tlkpcd(DEFAULT_TLKPCD);
        return thamsotl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thamsotl createUpdatedEntity(EntityManager em) {
        Thamsotl thamsotl = new Thamsotl()
            .thangnam(UPDATED_THANGNAM)
            .ncchuan(UPDATED_NCCHUAN)
            .giocchuan(UPDATED_GIOCCHUAN)
            .hsgioth(UPDATED_HSGIOTH)
            .hsgiole(UPDATED_HSGIOLE)
            .pcan(UPDATED_PCAN)
            .tlbhxh(UPDATED_TLBHXH)
            .tlbhyt(UPDATED_TLBHYT)
            .tlbhtn(UPDATED_TLBHTN)
            .tlkpcd(UPDATED_TLKPCD);
        return thamsotl;
    }

    @BeforeEach
    public void initTest() {
        thamsotl = createEntity(em);
    }

    @Test
    @Transactional
    void createThamsotl() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Thamsotl
        var returnedThamsotl = om.readValue(
            restThamsotlMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thamsotl)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Thamsotl.class
        );

        // Validate the Thamsotl in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThamsotlUpdatableFieldsEquals(returnedThamsotl, getPersistedThamsotl(returnedThamsotl));
    }

    @Test
    @Transactional
    void createThamsotlWithExistingId() throws Exception {
        // Create the Thamsotl with an existing ID
        thamsotl.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThamsotlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thamsotl)))
            .andExpect(status().isBadRequest());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThamsotls() throws Exception {
        // Initialize the database
        thamsotlRepository.saveAndFlush(thamsotl);

        // Get all the thamsotlList
        restThamsotlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thamsotl.getId().intValue())))
            .andExpect(jsonPath("$.[*].thangnam").value(hasItem(DEFAULT_THANGNAM)))
            .andExpect(jsonPath("$.[*].ncchuan").value(hasItem(DEFAULT_NCCHUAN)))
            .andExpect(jsonPath("$.[*].giocchuan").value(hasItem(DEFAULT_GIOCCHUAN)))
            .andExpect(jsonPath("$.[*].hsgioth").value(hasItem(DEFAULT_HSGIOTH.doubleValue())))
            .andExpect(jsonPath("$.[*].hsgiole").value(hasItem(DEFAULT_HSGIOLE.doubleValue())))
            .andExpect(jsonPath("$.[*].pcan").value(hasItem(DEFAULT_PCAN)))
            .andExpect(jsonPath("$.[*].tlbhxh").value(hasItem(DEFAULT_TLBHXH.doubleValue())))
            .andExpect(jsonPath("$.[*].tlbhyt").value(hasItem(DEFAULT_TLBHYT.doubleValue())))
            .andExpect(jsonPath("$.[*].tlbhtn").value(hasItem(DEFAULT_TLBHTN)))
            .andExpect(jsonPath("$.[*].tlkpcd").value(hasItem(DEFAULT_TLKPCD.doubleValue())));
    }

    @Test
    @Transactional
    void getThamsotl() throws Exception {
        // Initialize the database
        thamsotlRepository.saveAndFlush(thamsotl);

        // Get the thamsotl
        restThamsotlMockMvc
            .perform(get(ENTITY_API_URL_ID, thamsotl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thamsotl.getId().intValue()))
            .andExpect(jsonPath("$.thangnam").value(DEFAULT_THANGNAM))
            .andExpect(jsonPath("$.ncchuan").value(DEFAULT_NCCHUAN))
            .andExpect(jsonPath("$.giocchuan").value(DEFAULT_GIOCCHUAN))
            .andExpect(jsonPath("$.hsgioth").value(DEFAULT_HSGIOTH.doubleValue()))
            .andExpect(jsonPath("$.hsgiole").value(DEFAULT_HSGIOLE.doubleValue()))
            .andExpect(jsonPath("$.pcan").value(DEFAULT_PCAN))
            .andExpect(jsonPath("$.tlbhxh").value(DEFAULT_TLBHXH.doubleValue()))
            .andExpect(jsonPath("$.tlbhyt").value(DEFAULT_TLBHYT.doubleValue()))
            .andExpect(jsonPath("$.tlbhtn").value(DEFAULT_TLBHTN))
            .andExpect(jsonPath("$.tlkpcd").value(DEFAULT_TLKPCD.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingThamsotl() throws Exception {
        // Get the thamsotl
        restThamsotlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThamsotl() throws Exception {
        // Initialize the database
        thamsotlRepository.saveAndFlush(thamsotl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thamsotl
        Thamsotl updatedThamsotl = thamsotlRepository.findById(thamsotl.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedThamsotl are not directly saved in db
        em.detach(updatedThamsotl);
        updatedThamsotl
            .thangnam(UPDATED_THANGNAM)
            .ncchuan(UPDATED_NCCHUAN)
            .giocchuan(UPDATED_GIOCCHUAN)
            .hsgioth(UPDATED_HSGIOTH)
            .hsgiole(UPDATED_HSGIOLE)
            .pcan(UPDATED_PCAN)
            .tlbhxh(UPDATED_TLBHXH)
            .tlbhyt(UPDATED_TLBHYT)
            .tlbhtn(UPDATED_TLBHTN)
            .tlkpcd(UPDATED_TLKPCD);

        restThamsotlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThamsotl.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedThamsotl))
            )
            .andExpect(status().isOk());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThamsotlToMatchAllProperties(updatedThamsotl);
    }

    @Test
    @Transactional
    void putNonExistingThamsotl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thamsotl.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThamsotlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thamsotl.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thamsotl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThamsotl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thamsotl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThamsotlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thamsotl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThamsotl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thamsotl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThamsotlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thamsotl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThamsotlWithPatch() throws Exception {
        // Initialize the database
        thamsotlRepository.saveAndFlush(thamsotl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thamsotl using partial update
        Thamsotl partialUpdatedThamsotl = new Thamsotl();
        partialUpdatedThamsotl.setId(thamsotl.getId());

        partialUpdatedThamsotl
            .ncchuan(UPDATED_NCCHUAN)
            .hsgiole(UPDATED_HSGIOLE)
            .tlbhxh(UPDATED_TLBHXH)
            .tlbhyt(UPDATED_TLBHYT)
            .tlbhtn(UPDATED_TLBHTN)
            .tlkpcd(UPDATED_TLKPCD);

        restThamsotlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThamsotl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThamsotl))
            )
            .andExpect(status().isOk());

        // Validate the Thamsotl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThamsotlUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedThamsotl, thamsotl), getPersistedThamsotl(thamsotl));
    }

    @Test
    @Transactional
    void fullUpdateThamsotlWithPatch() throws Exception {
        // Initialize the database
        thamsotlRepository.saveAndFlush(thamsotl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thamsotl using partial update
        Thamsotl partialUpdatedThamsotl = new Thamsotl();
        partialUpdatedThamsotl.setId(thamsotl.getId());

        partialUpdatedThamsotl
            .thangnam(UPDATED_THANGNAM)
            .ncchuan(UPDATED_NCCHUAN)
            .giocchuan(UPDATED_GIOCCHUAN)
            .hsgioth(UPDATED_HSGIOTH)
            .hsgiole(UPDATED_HSGIOLE)
            .pcan(UPDATED_PCAN)
            .tlbhxh(UPDATED_TLBHXH)
            .tlbhyt(UPDATED_TLBHYT)
            .tlbhtn(UPDATED_TLBHTN)
            .tlkpcd(UPDATED_TLKPCD);

        restThamsotlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThamsotl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThamsotl))
            )
            .andExpect(status().isOk());

        // Validate the Thamsotl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThamsotlUpdatableFieldsEquals(partialUpdatedThamsotl, getPersistedThamsotl(partialUpdatedThamsotl));
    }

    @Test
    @Transactional
    void patchNonExistingThamsotl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thamsotl.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThamsotlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thamsotl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thamsotl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThamsotl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thamsotl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThamsotlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thamsotl))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThamsotl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thamsotl.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThamsotlMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thamsotl)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thamsotl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThamsotl() throws Exception {
        // Initialize the database
        thamsotlRepository.saveAndFlush(thamsotl);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thamsotl
        restThamsotlMockMvc
            .perform(delete(ENTITY_API_URL_ID, thamsotl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thamsotlRepository.count();
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

    protected Thamsotl getPersistedThamsotl(Thamsotl thamsotl) {
        return thamsotlRepository.findById(thamsotl.getId()).orElseThrow();
    }

    protected void assertPersistedThamsotlToMatchAllProperties(Thamsotl expectedThamsotl) {
        assertThamsotlAllPropertiesEquals(expectedThamsotl, getPersistedThamsotl(expectedThamsotl));
    }

    protected void assertPersistedThamsotlToMatchUpdatableProperties(Thamsotl expectedThamsotl) {
        assertThamsotlAllUpdatablePropertiesEquals(expectedThamsotl, getPersistedThamsotl(expectedThamsotl));
    }
}
