package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Tanggiamtl;
import com.mycompany.myapp.repository.TanggiamtlRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Tanggiamtl}.
 */
@RestController
@RequestMapping("/api/tanggiamtls")
@Transactional
public class TanggiamtlResource {

    private final Logger log = LoggerFactory.getLogger(TanggiamtlResource.class);

    private static final String ENTITY_NAME = "tanggiamtl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TanggiamtlRepository tanggiamtlRepository;

    public TanggiamtlResource(TanggiamtlRepository tanggiamtlRepository) {
        this.tanggiamtlRepository = tanggiamtlRepository;
    }

    /**
     * {@code POST  /tanggiamtls} : Create a new tanggiamtl.
     *
     * @param tanggiamtl the tanggiamtl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tanggiamtl, or with status {@code 400 (Bad Request)} if the tanggiamtl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tanggiamtl> createTanggiamtl(@RequestBody Tanggiamtl tanggiamtl) throws URISyntaxException {
        log.debug("REST request to save Tanggiamtl : {}", tanggiamtl);
        if (tanggiamtl.getId() != null) {
            throw new BadRequestAlertException("A new tanggiamtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tanggiamtl = tanggiamtlRepository.save(tanggiamtl);
        return ResponseEntity.created(new URI("/api/tanggiamtls/" + tanggiamtl.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tanggiamtl.getId().toString()))
            .body(tanggiamtl);
    }

    /**
     * {@code PUT  /tanggiamtls/:id} : Updates an existing tanggiamtl.
     *
     * @param id the id of the tanggiamtl to save.
     * @param tanggiamtl the tanggiamtl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tanggiamtl,
     * or with status {@code 400 (Bad Request)} if the tanggiamtl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tanggiamtl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tanggiamtl> updateTanggiamtl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tanggiamtl tanggiamtl
    ) throws URISyntaxException {
        log.debug("REST request to update Tanggiamtl : {}, {}", id, tanggiamtl);
        if (tanggiamtl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tanggiamtl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tanggiamtlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tanggiamtl = tanggiamtlRepository.save(tanggiamtl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tanggiamtl.getId().toString()))
            .body(tanggiamtl);
    }

    /**
     * {@code PATCH  /tanggiamtls/:id} : Partial updates given fields of an existing tanggiamtl, field will ignore if it is null
     *
     * @param id the id of the tanggiamtl to save.
     * @param tanggiamtl the tanggiamtl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tanggiamtl,
     * or with status {@code 400 (Bad Request)} if the tanggiamtl is not valid,
     * or with status {@code 404 (Not Found)} if the tanggiamtl is not found,
     * or with status {@code 500 (Internal Server Error)} if the tanggiamtl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tanggiamtl> partialUpdateTanggiamtl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tanggiamtl tanggiamtl
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tanggiamtl partially : {}, {}", id, tanggiamtl);
        if (tanggiamtl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tanggiamtl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tanggiamtlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tanggiamtl> result = tanggiamtlRepository
            .findById(tanggiamtl.getId())
            .map(existingTanggiamtl -> {
                if (tanggiamtl.getNgaythang() != null) {
                    existingTanggiamtl.setNgaythang(tanggiamtl.getNgaythang());
                }
                if (tanggiamtl.getTkn() != null) {
                    existingTanggiamtl.setTkn(tanggiamtl.getTkn());
                }
                if (tanggiamtl.getTkc() != null) {
                    existingTanggiamtl.setTkc(tanggiamtl.getTkc());
                }
                if (tanggiamtl.getSotien() != null) {
                    existingTanggiamtl.setSotien(tanggiamtl.getSotien());
                }
                if (tanggiamtl.getDiengiai() != null) {
                    existingTanggiamtl.setDiengiai(tanggiamtl.getDiengiai());
                }

                return existingTanggiamtl;
            })
            .map(tanggiamtlRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tanggiamtl.getId().toString())
        );
    }

    /**
     * {@code GET  /tanggiamtls} : get all the tanggiamtls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tanggiamtls in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Tanggiamtl>> getAllTanggiamtls(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Tanggiamtls");
        Page<Tanggiamtl> page = tanggiamtlRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tanggiamtls/:id} : get the "id" tanggiamtl.
     *
     * @param id the id of the tanggiamtl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tanggiamtl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tanggiamtl> getTanggiamtl(@PathVariable("id") Long id) {
        log.debug("REST request to get Tanggiamtl : {}", id);
        Optional<Tanggiamtl> tanggiamtl = tanggiamtlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tanggiamtl);
    }

    /**
     * {@code DELETE  /tanggiamtls/:id} : delete the "id" tanggiamtl.
     *
     * @param id the id of the tanggiamtl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTanggiamtl(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tanggiamtl : {}", id);
        tanggiamtlRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
