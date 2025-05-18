package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Phongban;
import com.mycompany.myapp.repository.PhongbanRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Phongban}.
 */
@RestController
@RequestMapping("/api/phongbans")
@Transactional
public class PhongbanResource {

    private final Logger log = LoggerFactory.getLogger(PhongbanResource.class);

    private static final String ENTITY_NAME = "phongban";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhongbanRepository phongbanRepository;

    public PhongbanResource(PhongbanRepository phongbanRepository) {
        this.phongbanRepository = phongbanRepository;
    }

    /**
     * {@code POST  /phongbans} : Create a new phongban.
     *
     * @param phongban the phongban to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phongban, or with status {@code 400 (Bad Request)} if the phongban has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Phongban> createPhongban(@RequestBody Phongban phongban) throws URISyntaxException {
        log.debug("REST request to save Phongban : {}", phongban);
        if (phongban.getId() != null) {
            throw new BadRequestAlertException("A new phongban cannot already have an ID", ENTITY_NAME, "idexists");
        }
        phongban = phongbanRepository.save(phongban);
        return ResponseEntity.created(new URI("/api/phongbans/" + phongban.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, phongban.getId().toString()))
            .body(phongban);
    }

    /**
     * {@code PUT  /phongbans/:id} : Updates an existing phongban.
     *
     * @param id the id of the phongban to save.
     * @param phongban the phongban to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongban,
     * or with status {@code 400 (Bad Request)} if the phongban is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phongban couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Phongban> updatePhongban(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Phongban phongban
    ) throws URISyntaxException {
        log.debug("REST request to update Phongban : {}, {}", id, phongban);
        if (phongban.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongban.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongbanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        phongban = phongbanRepository.save(phongban);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongban.getId().toString()))
            .body(phongban);
    }

    /**
     * {@code PATCH  /phongbans/:id} : Partial updates given fields of an existing phongban, field will ignore if it is null
     *
     * @param id the id of the phongban to save.
     * @param phongban the phongban to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongban,
     * or with status {@code 400 (Bad Request)} if the phongban is not valid,
     * or with status {@code 404 (Not Found)} if the phongban is not found,
     * or with status {@code 500 (Internal Server Error)} if the phongban couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Phongban> partialUpdatePhongban(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Phongban phongban
    ) throws URISyntaxException {
        log.debug("REST request to partial update Phongban partially : {}, {}", id, phongban);
        if (phongban.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongban.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongbanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Phongban> result = phongbanRepository
            .findById(phongban.getId())
            .map(existingPhongban -> {
                if (phongban.getMapb() != null) {
                    existingPhongban.setMapb(phongban.getMapb());
                }
                if (phongban.getTenpb() != null) {
                    existingPhongban.setTenpb(phongban.getTenpb());
                }
                if (phongban.getSdt() != null) {
                    existingPhongban.setSdt(phongban.getSdt());
                }

                return existingPhongban;
            })
            .map(phongbanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongban.getId().toString())
        );
    }

    /**
     * {@code GET  /phongbans} : get all the phongbans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phongbans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Phongban>> getAllPhongbans(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Phongbans");
        Page<Phongban> page = phongbanRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phongbans/:id} : get the "id" phongban.
     *
     * @param id the id of the phongban to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phongban, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Phongban> getPhongban(@PathVariable("id") Long id) {
        log.debug("REST request to get Phongban : {}", id);
        Optional<Phongban> phongban = phongbanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(phongban);
    }

    /**
     * {@code DELETE  /phongbans/:id} : delete the "id" phongban.
     *
     * @param id the id of the phongban to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhongban(@PathVariable("id") Long id) {
        log.debug("REST request to delete Phongban : {}", id);
        phongbanRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
