package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.BangchamcongAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Bangchamcong;
import com.mycompany.myapp.repository.BangchamcongRepository;
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
 * Integration tests for the {@link BangchamcongResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BangchamcongResourceIT {

    private static final Integer DEFAULT_NCDILAM = 1;
    private static final Integer UPDATED_NCDILAM = 2;

    private static final String DEFAULT_THANGCC = "AAAAAAAAAA";
    private static final String UPDATED_THANGCC = "BBBBBBBBBB";

    private static final Integer DEFAULT_NCLEPHEP = 1;
    private static final Integer UPDATED_NCLEPHEP = 2;

    private static final String DEFAULT_XEPLOAI = "AAAAAAAAAA";
    private static final String UPDATED_XEPLOAI = "BBBBBBBBBB";

    private static final Integer DEFAULT_NGAYTHTHUONG = 1;
    private static final Integer UPDATED_NGAYTHTHUONG = 2;

    private static final Integer DEFAULT_NGAYTHLE = 1;
    private static final Integer UPDATED_NGAYTHLE = 2;

    private static final String ENTITY_API_URL = "/api/bangchamcongs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BangchamcongRepository bangchamcongRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBangchamcongMockMvc;

    private Bangchamcong bangchamcong;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bangchamcong createEntity(EntityManager em) {
        Bangchamcong bangchamcong = new Bangchamcong()
            .ncdilam(DEFAULT_NCDILAM)
            .thangcc(DEFAULT_THANGCC)
            .nclephep(DEFAULT_NCLEPHEP)
            .xeploai(DEFAULT_XEPLOAI)
            .ngayththuong(DEFAULT_NGAYTHTHUONG)
            .ngaythle(DEFAULT_NGAYTHLE);
        return bangchamcong;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bangchamcong createUpdatedEntity(EntityManager em) {
        Bangchamcong bangchamcong = new Bangchamcong()
            .ncdilam(UPDATED_NCDILAM)
            .thangcc(UPDATED_THANGCC)
            .nclephep(UPDATED_NCLEPHEP)
            .xeploai(UPDATED_XEPLOAI)
            .ngayththuong(UPDATED_NGAYTHTHUONG)
            .ngaythle(UPDATED_NGAYTHLE);
        return bangchamcong;
    }

    @BeforeEach
    public void initTest() {
        bangchamcong = createEntity(em);
    }

    @Test
    @Transactional
    void createBangchamcong() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bangchamcong
        var returnedBangchamcong = om.readValue(
            restBangchamcongMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bangchamcong)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Bangchamcong.class
        );

        // Validate the Bangchamcong in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBangchamcongUpdatableFieldsEquals(returnedBangchamcong, getPersistedBangchamcong(returnedBangchamcong));
    }

    @Test
    @Transactional
    void createBangchamcongWithExistingId() throws Exception {
        // Create the Bangchamcong with an existing ID
        bangchamcong.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBangchamcongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bangchamcong)))
            .andExpect(status().isBadRequest());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBangchamcongs() throws Exception {
        // Initialize the database
        bangchamcongRepository.saveAndFlush(bangchamcong);

        // Get all the bangchamcongList
        restBangchamcongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bangchamcong.getId().intValue())))
            .andExpect(jsonPath("$.[*].ncdilam").value(hasItem(DEFAULT_NCDILAM)))
            .andExpect(jsonPath("$.[*].thangcc").value(hasItem(DEFAULT_THANGCC)))
            .andExpect(jsonPath("$.[*].nclephep").value(hasItem(DEFAULT_NCLEPHEP)))
            .andExpect(jsonPath("$.[*].xeploai").value(hasItem(DEFAULT_XEPLOAI)))
            .andExpect(jsonPath("$.[*].ngayththuong").value(hasItem(DEFAULT_NGAYTHTHUONG)))
            .andExpect(jsonPath("$.[*].ngaythle").value(hasItem(DEFAULT_NGAYTHLE)));
    }

    @Test
    @Transactional
    void getBangchamcong() throws Exception {
        // Initialize the database
        bangchamcongRepository.saveAndFlush(bangchamcong);

        // Get the bangchamcong
        restBangchamcongMockMvc
            .perform(get(ENTITY_API_URL_ID, bangchamcong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bangchamcong.getId().intValue()))
            .andExpect(jsonPath("$.ncdilam").value(DEFAULT_NCDILAM))
            .andExpect(jsonPath("$.thangcc").value(DEFAULT_THANGCC))
            .andExpect(jsonPath("$.nclephep").value(DEFAULT_NCLEPHEP))
            .andExpect(jsonPath("$.xeploai").value(DEFAULT_XEPLOAI))
            .andExpect(jsonPath("$.ngayththuong").value(DEFAULT_NGAYTHTHUONG))
            .andExpect(jsonPath("$.ngaythle").value(DEFAULT_NGAYTHLE));
    }

    @Test
    @Transactional
    void getNonExistingBangchamcong() throws Exception {
        // Get the bangchamcong
        restBangchamcongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBangchamcong() throws Exception {
        // Initialize the database
        bangchamcongRepository.saveAndFlush(bangchamcong);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bangchamcong
        Bangchamcong updatedBangchamcong = bangchamcongRepository.findById(bangchamcong.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBangchamcong are not directly saved in db
        em.detach(updatedBangchamcong);
        updatedBangchamcong
            .ncdilam(UPDATED_NCDILAM)
            .thangcc(UPDATED_THANGCC)
            .nclephep(UPDATED_NCLEPHEP)
            .xeploai(UPDATED_XEPLOAI)
            .ngayththuong(UPDATED_NGAYTHTHUONG)
            .ngaythle(UPDATED_NGAYTHLE);

        restBangchamcongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBangchamcong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBangchamcong))
            )
            .andExpect(status().isOk());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBangchamcongToMatchAllProperties(updatedBangchamcong);
    }

    @Test
    @Transactional
    void putNonExistingBangchamcong() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bangchamcong.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBangchamcongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bangchamcong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bangchamcong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBangchamcong() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bangchamcong.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangchamcongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bangchamcong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBangchamcong() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bangchamcong.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangchamcongMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bangchamcong)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBangchamcongWithPatch() throws Exception {
        // Initialize the database
        bangchamcongRepository.saveAndFlush(bangchamcong);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bangchamcong using partial update
        Bangchamcong partialUpdatedBangchamcong = new Bangchamcong();
        partialUpdatedBangchamcong.setId(bangchamcong.getId());

        partialUpdatedBangchamcong.xeploai(UPDATED_XEPLOAI);

        restBangchamcongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBangchamcong.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBangchamcong))
            )
            .andExpect(status().isOk());

        // Validate the Bangchamcong in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBangchamcongUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBangchamcong, bangchamcong),
            getPersistedBangchamcong(bangchamcong)
        );
    }

    @Test
    @Transactional
    void fullUpdateBangchamcongWithPatch() throws Exception {
        // Initialize the database
        bangchamcongRepository.saveAndFlush(bangchamcong);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bangchamcong using partial update
        Bangchamcong partialUpdatedBangchamcong = new Bangchamcong();
        partialUpdatedBangchamcong.setId(bangchamcong.getId());

        partialUpdatedBangchamcong
            .ncdilam(UPDATED_NCDILAM)
            .thangcc(UPDATED_THANGCC)
            .nclephep(UPDATED_NCLEPHEP)
            .xeploai(UPDATED_XEPLOAI)
            .ngayththuong(UPDATED_NGAYTHTHUONG)
            .ngaythle(UPDATED_NGAYTHLE);

        restBangchamcongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBangchamcong.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBangchamcong))
            )
            .andExpect(status().isOk());

        // Validate the Bangchamcong in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBangchamcongUpdatableFieldsEquals(partialUpdatedBangchamcong, getPersistedBangchamcong(partialUpdatedBangchamcong));
    }

    @Test
    @Transactional
    void patchNonExistingBangchamcong() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bangchamcong.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBangchamcongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bangchamcong.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bangchamcong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBangchamcong() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bangchamcong.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangchamcongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bangchamcong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBangchamcong() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bangchamcong.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangchamcongMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bangchamcong)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bangchamcong in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBangchamcong() throws Exception {
        // Initialize the database
        bangchamcongRepository.saveAndFlush(bangchamcong);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bangchamcong
        restBangchamcongMockMvc
            .perform(delete(ENTITY_API_URL_ID, bangchamcong.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bangchamcongRepository.count();
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

    protected Bangchamcong getPersistedBangchamcong(Bangchamcong bangchamcong) {
        return bangchamcongRepository.findById(bangchamcong.getId()).orElseThrow();
    }

    protected void assertPersistedBangchamcongToMatchAllProperties(Bangchamcong expectedBangchamcong) {
        assertBangchamcongAllPropertiesEquals(expectedBangchamcong, getPersistedBangchamcong(expectedBangchamcong));
    }

    protected void assertPersistedBangchamcongToMatchUpdatableProperties(Bangchamcong expectedBangchamcong) {
        assertBangchamcongAllUpdatablePropertiesEquals(expectedBangchamcong, getPersistedBangchamcong(expectedBangchamcong));
    }
}
