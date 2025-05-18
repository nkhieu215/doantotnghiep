package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Bangchamcong;
import com.mycompany.myapp.repository.BangchamcongRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Bangchamcong}.
 */
@RestController
@RequestMapping("/api/bangchamcongs")
@Transactional
public class BangchamcongResource {

    private final Logger log = LoggerFactory.getLogger(BangchamcongResource.class);

    private static final String ENTITY_NAME = "bangchamcong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BangchamcongRepository bangchamcongRepository;

    public BangchamcongResource(BangchamcongRepository bangchamcongRepository) {
        this.bangchamcongRepository = bangchamcongRepository;
    }

    /**
     * {@code POST  /bangchamcongs} : Create a new bangchamcong.
     *
     * @param bangchamcong the bangchamcong to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bangchamcong, or with status {@code 400 (Bad Request)} if the bangchamcong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bangchamcong> createBangchamcong(@RequestBody Bangchamcong bangchamcong) throws URISyntaxException {
        log.debug("REST request to save Bangchamcong : {}", bangchamcong);
        if (bangchamcong.getId() != null) {
            throw new BadRequestAlertException("A new bangchamcong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bangchamcong = bangchamcongRepository.save(bangchamcong);
        return ResponseEntity.created(new URI("/api/bangchamcongs/" + bangchamcong.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bangchamcong.getId().toString()))
            .body(bangchamcong);
    }

    /**
     * {@code PUT  /bangchamcongs/:id} : Updates an existing bangchamcong.
     *
     * @param id the id of the bangchamcong to save.
     * @param bangchamcong the bangchamcong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bangchamcong,
     * or with status {@code 400 (Bad Request)} if the bangchamcong is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bangchamcong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bangchamcong> updateBangchamcong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bangchamcong bangchamcong
    ) throws URISyntaxException {
        log.debug("REST request to update Bangchamcong : {}, {}", id, bangchamcong);
        if (bangchamcong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bangchamcong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bangchamcongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bangchamcong = bangchamcongRepository.save(bangchamcong);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bangchamcong.getId().toString()))
            .body(bangchamcong);
    }

    /**
     * {@code PATCH  /bangchamcongs/:id} : Partial updates given fields of an existing bangchamcong, field will ignore if it is null
     *
     * @param id the id of the bangchamcong to save.
     * @param bangchamcong the bangchamcong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bangchamcong,
     * or with status {@code 400 (Bad Request)} if the bangchamcong is not valid,
     * or with status {@code 404 (Not Found)} if the bangchamcong is not found,
     * or with status {@code 500 (Internal Server Error)} if the bangchamcong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bangchamcong> partialUpdateBangchamcong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bangchamcong bangchamcong
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bangchamcong partially : {}, {}", id, bangchamcong);
        if (bangchamcong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bangchamcong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bangchamcongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bangchamcong> result = bangchamcongRepository
            .findById(bangchamcong.getId())
            .map(existingBangchamcong -> {
                if (bangchamcong.getNcdilam() != null) {
                    existingBangchamcong.setNcdilam(bangchamcong.getNcdilam());
                }
                if (bangchamcong.getThangcc() != null) {
                    existingBangchamcong.setThangcc(bangchamcong.getThangcc());
                }
                if (bangchamcong.getNclephep() != null) {
                    existingBangchamcong.setNclephep(bangchamcong.getNclephep());
                }
                if (bangchamcong.getXeploai() != null) {
                    existingBangchamcong.setXeploai(bangchamcong.getXeploai());
                }
                if (bangchamcong.getNgayththuong() != null) {
                    existingBangchamcong.setNgayththuong(bangchamcong.getNgayththuong());
                }
                if (bangchamcong.getNgaythle() != null) {
                    existingBangchamcong.setNgaythle(bangchamcong.getNgaythle());
                }

                return existingBangchamcong;
            })
            .map(bangchamcongRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bangchamcong.getId().toString())
        );
    }

    /**
     * {@code GET  /bangchamcongs} : get all the bangchamcongs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bangchamcongs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Bangchamcong>> getAllBangchamcongs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Bangchamcongs");
        Page<Bangchamcong> page = bangchamcongRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bangchamcongs/:id} : get the "id" bangchamcong.
     *
     * @param id the id of the bangchamcong to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bangchamcong, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bangchamcong> getBangchamcong(@PathVariable("id") Long id) {
        log.debug("REST request to get Bangchamcong : {}", id);
        Optional<Bangchamcong> bangchamcong = bangchamcongRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bangchamcong);
    }

    /**
     * {@code DELETE  /bangchamcongs/:id} : delete the "id" bangchamcong.
     *
     * @param id the id of the bangchamcong to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBangchamcong(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bangchamcong : {}", id);
        bangchamcongRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
