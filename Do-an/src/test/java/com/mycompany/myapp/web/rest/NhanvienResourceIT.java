package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.NhanvienAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Nhanvien;
import com.mycompany.myapp.repository.NhanvienRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link NhanvienResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhanvienResourceIT {

    private static final String DEFAULT_MANV = "AAAAAAAAAA";
    private static final String UPDATED_MANV = "BBBBBBBBBB";

    private static final String DEFAULT_HOTEN = "AAAAAAAAAA";
    private static final String UPDATED_HOTEN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_NGAYSINH = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NGAYSINH = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_GIOITINH = "AAAAAAAAAA";
    private static final String UPDATED_GIOITINH = "BBBBBBBBBB";

    private static final String DEFAULT_QUEQUAN = "AAAAAAAAAA";
    private static final String UPDATED_QUEQUAN = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final Float DEFAULT_HSLUONG = 1F;
    private static final Float UPDATED_HSLUONG = 2F;

    private static final Integer DEFAULT_MSTHUE = 1;
    private static final Integer UPDATED_MSTHUE = 2;

    private static final String ENTITY_API_URL = "/api/nhanviens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NhanvienRepository nhanvienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhanvienMockMvc;

    private Nhanvien nhanvien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nhanvien createEntity(EntityManager em) {
        Nhanvien nhanvien = new Nhanvien()
            .manv(DEFAULT_MANV)
            .hoten(DEFAULT_HOTEN)
            .ngaysinh(DEFAULT_NGAYSINH)
            .gioitinh(DEFAULT_GIOITINH)
            .quequan(DEFAULT_QUEQUAN)
            .diachi(DEFAULT_DIACHI)
            .hsluong(DEFAULT_HSLUONG)
            .msthue(DEFAULT_MSTHUE);
        return nhanvien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nhanvien createUpdatedEntity(EntityManager em) {
        Nhanvien nhanvien = new Nhanvien()
            .manv(UPDATED_MANV)
            .hoten(UPDATED_HOTEN)
            .ngaysinh(UPDATED_NGAYSINH)
            .gioitinh(UPDATED_GIOITINH)
            .quequan(UPDATED_QUEQUAN)
            .diachi(UPDATED_DIACHI)
            .hsluong(UPDATED_HSLUONG)
            .msthue(UPDATED_MSTHUE);
        return nhanvien;
    }

    @BeforeEach
    public void initTest() {
        nhanvien = createEntity(em);
    }

    @Test
    @Transactional
    void createNhanvien() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nhanvien
        var returnedNhanvien = om.readValue(
            restNhanvienMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nhanvien)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nhanvien.class
        );

        // Validate the Nhanvien in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNhanvienUpdatableFieldsEquals(returnedNhanvien, getPersistedNhanvien(returnedNhanvien));
    }

    @Test
    @Transactional
    void createNhanvienWithExistingId() throws Exception {
        // Create the Nhanvien with an existing ID
        nhanvien.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhanvienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nhanvien)))
            .andExpect(status().isBadRequest());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNhanviens() throws Exception {
        // Initialize the database
        nhanvienRepository.saveAndFlush(nhanvien);

        // Get all the nhanvienList
        restNhanvienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanvien.getId().intValue())))
            .andExpect(jsonPath("$.[*].manv").value(hasItem(DEFAULT_MANV)))
            .andExpect(jsonPath("$.[*].hoten").value(hasItem(DEFAULT_HOTEN)))
            .andExpect(jsonPath("$.[*].ngaysinh").value(hasItem(sameInstant(DEFAULT_NGAYSINH))))
            .andExpect(jsonPath("$.[*].gioitinh").value(hasItem(DEFAULT_GIOITINH)))
            .andExpect(jsonPath("$.[*].quequan").value(hasItem(DEFAULT_QUEQUAN)))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI)))
            .andExpect(jsonPath("$.[*].hsluong").value(hasItem(DEFAULT_HSLUONG.doubleValue())))
            .andExpect(jsonPath("$.[*].msthue").value(hasItem(DEFAULT_MSTHUE)));
    }

    @Test
    @Transactional
    void getNhanvien() throws Exception {
        // Initialize the database
        nhanvienRepository.saveAndFlush(nhanvien);

        // Get the nhanvien
        restNhanvienMockMvc
            .perform(get(ENTITY_API_URL_ID, nhanvien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhanvien.getId().intValue()))
            .andExpect(jsonPath("$.manv").value(DEFAULT_MANV))
            .andExpect(jsonPath("$.hoten").value(DEFAULT_HOTEN))
            .andExpect(jsonPath("$.ngaysinh").value(sameInstant(DEFAULT_NGAYSINH)))
            .andExpect(jsonPath("$.gioitinh").value(DEFAULT_GIOITINH))
            .andExpect(jsonPath("$.quequan").value(DEFAULT_QUEQUAN))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI))
            .andExpect(jsonPath("$.hsluong").value(DEFAULT_HSLUONG.doubleValue()))
            .andExpect(jsonPath("$.msthue").value(DEFAULT_MSTHUE));
    }

    @Test
    @Transactional
    void getNonExistingNhanvien() throws Exception {
        // Get the nhanvien
        restNhanvienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNhanvien() throws Exception {
        // Initialize the database
        nhanvienRepository.saveAndFlush(nhanvien);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nhanvien
        Nhanvien updatedNhanvien = nhanvienRepository.findById(nhanvien.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNhanvien are not directly saved in db
        em.detach(updatedNhanvien);
        updatedNhanvien
            .manv(UPDATED_MANV)
            .hoten(UPDATED_HOTEN)
            .ngaysinh(UPDATED_NGAYSINH)
            .gioitinh(UPDATED_GIOITINH)
            .quequan(UPDATED_QUEQUAN)
            .diachi(UPDATED_DIACHI)
            .hsluong(UPDATED_HSLUONG)
            .msthue(UPDATED_MSTHUE);

        restNhanvienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNhanvien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNhanvien))
            )
            .andExpect(status().isOk());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNhanvienToMatchAllProperties(updatedNhanvien);
    }

    @Test
    @Transactional
    void putNonExistingNhanvien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nhanvien.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanvienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhanvien.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nhanvien))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhanvien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nhanvien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanvienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nhanvien))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhanvien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nhanvien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanvienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nhanvien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhanvienWithPatch() throws Exception {
        // Initialize the database
        nhanvienRepository.saveAndFlush(nhanvien);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nhanvien using partial update
        Nhanvien partialUpdatedNhanvien = new Nhanvien();
        partialUpdatedNhanvien.setId(nhanvien.getId());

        partialUpdatedNhanvien.diachi(UPDATED_DIACHI).hsluong(UPDATED_HSLUONG).msthue(UPDATED_MSTHUE);

        restNhanvienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanvien.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNhanvien))
            )
            .andExpect(status().isOk());

        // Validate the Nhanvien in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNhanvienUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNhanvien, nhanvien), getPersistedNhanvien(nhanvien));
    }

    @Test
    @Transactional
    void fullUpdateNhanvienWithPatch() throws Exception {
        // Initialize the database
        nhanvienRepository.saveAndFlush(nhanvien);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nhanvien using partial update
        Nhanvien partialUpdatedNhanvien = new Nhanvien();
        partialUpdatedNhanvien.setId(nhanvien.getId());

        partialUpdatedNhanvien
            .manv(UPDATED_MANV)
            .hoten(UPDATED_HOTEN)
            .ngaysinh(UPDATED_NGAYSINH)
            .gioitinh(UPDATED_GIOITINH)
            .quequan(UPDATED_QUEQUAN)
            .diachi(UPDATED_DIACHI)
            .hsluong(UPDATED_HSLUONG)
            .msthue(UPDATED_MSTHUE);

        restNhanvienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanvien.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNhanvien))
            )
            .andExpect(status().isOk());

        // Validate the Nhanvien in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNhanvienUpdatableFieldsEquals(partialUpdatedNhanvien, getPersistedNhanvien(partialUpdatedNhanvien));
    }

    @Test
    @Transactional
    void patchNonExistingNhanvien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nhanvien.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanvienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhanvien.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nhanvien))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhanvien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nhanvien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanvienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nhanvien))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhanvien() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nhanvien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanvienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nhanvien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nhanvien in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhanvien() throws Exception {
        // Initialize the database
        nhanvienRepository.saveAndFlush(nhanvien);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nhanvien
        restNhanvienMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhanvien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nhanvienRepository.count();
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

    protected Nhanvien getPersistedNhanvien(Nhanvien nhanvien) {
        return nhanvienRepository.findById(nhanvien.getId()).orElseThrow();
    }

    protected void assertPersistedNhanvienToMatchAllProperties(Nhanvien expectedNhanvien) {
        assertNhanvienAllPropertiesEquals(expectedNhanvien, getPersistedNhanvien(expectedNhanvien));
    }

    protected void assertPersistedNhanvienToMatchUpdatableProperties(Nhanvien expectedNhanvien) {
        assertNhanvienAllUpdatablePropertiesEquals(expectedNhanvien, getPersistedNhanvien(expectedNhanvien));
    }
}
