package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Chucvu;
import com.mycompany.myapp.repository.ChucvuRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Chucvu}.
 */
@RestController
@RequestMapping("/api/chucvus")
@Transactional
public class ChucvuResource {

    private final Logger log = LoggerFactory.getLogger(ChucvuResource.class);

    private static final String ENTITY_NAME = "chucvu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChucvuRepository chucvuRepository;

    public ChucvuResource(ChucvuRepository chucvuRepository) {
        this.chucvuRepository = chucvuRepository;
    }

    /**
     * {@code POST  /chucvus} : Create a new chucvu.
     *
     * @param chucvu the chucvu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chucvu, or with status {@code 400 (Bad Request)} if the chucvu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Chucvu> createChucvu(@RequestBody Chucvu chucvu) throws URISyntaxException {
        log.debug("REST request to save Chucvu : {}", chucvu);
        if (chucvu.getId() != null) {
            throw new BadRequestAlertException("A new chucvu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        chucvu = chucvuRepository.save(chucvu);
        return ResponseEntity.created(new URI("/api/chucvus/" + chucvu.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, chucvu.getId().toString()))
            .body(chucvu);
    }

    /**
     * {@code PUT  /chucvus/:id} : Updates an existing chucvu.
     *
     * @param id the id of the chucvu to save.
     * @param chucvu the chucvu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chucvu,
     * or with status {@code 400 (Bad Request)} if the chucvu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chucvu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Chucvu> updateChucvu(@PathVariable(value = "id", required = false) final Long id, @RequestBody Chucvu chucvu)
        throws URISyntaxException {
        log.debug("REST request to update Chucvu : {}, {}", id, chucvu);
        if (chucvu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chucvu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chucvuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        chucvu = chucvuRepository.save(chucvu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chucvu.getId().toString()))
            .body(chucvu);
    }

    /**
     * {@code PATCH  /chucvus/:id} : Partial updates given fields of an existing chucvu, field will ignore if it is null
     *
     * @param id the id of the chucvu to save.
     * @param chucvu the chucvu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chucvu,
     * or with status {@code 400 (Bad Request)} if the chucvu is not valid,
     * or with status {@code 404 (Not Found)} if the chucvu is not found,
     * or with status {@code 500 (Internal Server Error)} if the chucvu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Chucvu> partialUpdateChucvu(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Chucvu chucvu
    ) throws URISyntaxException {
        log.debug("REST request to partial update Chucvu partially : {}, {}", id, chucvu);
        if (chucvu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chucvu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chucvuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Chucvu> result = chucvuRepository
            .findById(chucvu.getId())
            .map(existingChucvu -> {
                if (chucvu.getMacv() != null) {
                    existingChucvu.setMacv(chucvu.getMacv());
                }
                if (chucvu.getTencv() != null) {
                    existingChucvu.setTencv(chucvu.getTencv());
                }
                if (chucvu.getHcpccv() != null) {
                    existingChucvu.setHcpccv(chucvu.getHcpccv());
                }

                return existingChucvu;
            })
            .map(chucvuRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chucvu.getId().toString())
        );
    }

    /**
     * {@code GET  /chucvus} : get all the chucvus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chucvus in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Chucvu>> getAllChucvus(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Chucvus");
        Page<Chucvu> page = chucvuRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chucvus/:id} : get the "id" chucvu.
     *
     * @param id the id of the chucvu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chucvu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Chucvu> getChucvu(@PathVariable("id") Long id) {
        log.debug("REST request to get Chucvu : {}", id);
        Optional<Chucvu> chucvu = chucvuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chucvu);
    }

    /**
     * {@code DELETE  /chucvus/:id} : delete the "id" chucvu.
     *
     * @param id the id of the chucvu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChucvu(@PathVariable("id") Long id) {
        log.debug("REST request to delete Chucvu : {}", id);
        chucvuRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
