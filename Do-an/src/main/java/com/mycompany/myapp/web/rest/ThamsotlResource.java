package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Thamsotl;
import com.mycompany.myapp.repository.ThamsotlRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Thamsotl}.
 */
@RestController
@RequestMapping("/api/thamsotls")
@Transactional
public class ThamsotlResource {

    private final Logger log = LoggerFactory.getLogger(ThamsotlResource.class);

    private static final String ENTITY_NAME = "thamsotl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThamsotlRepository thamsotlRepository;

    public ThamsotlResource(ThamsotlRepository thamsotlRepository) {
        this.thamsotlRepository = thamsotlRepository;
    }

    /**
     * {@code POST  /thamsotls} : Create a new thamsotl.
     *
     * @param thamsotl the thamsotl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thamsotl, or with status {@code 400 (Bad Request)} if the thamsotl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Thamsotl> createThamsotl(@RequestBody Thamsotl thamsotl) throws URISyntaxException {
        log.debug("REST request to save Thamsotl : {}", thamsotl);
        if (thamsotl.getId() != null) {
            throw new BadRequestAlertException("A new thamsotl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thamsotl = thamsotlRepository.save(thamsotl);
        return ResponseEntity.created(new URI("/api/thamsotls/" + thamsotl.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, thamsotl.getId().toString()))
            .body(thamsotl);
    }

    /**
     * {@code PUT  /thamsotls/:id} : Updates an existing thamsotl.
     *
     * @param id the id of the thamsotl to save.
     * @param thamsotl the thamsotl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thamsotl,
     * or with status {@code 400 (Bad Request)} if the thamsotl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thamsotl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Thamsotl> updateThamsotl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Thamsotl thamsotl
    ) throws URISyntaxException {
        log.debug("REST request to update Thamsotl : {}, {}", id, thamsotl);
        if (thamsotl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thamsotl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thamsotlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thamsotl = thamsotlRepository.save(thamsotl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thamsotl.getId().toString()))
            .body(thamsotl);
    }

    /**
     * {@code PATCH  /thamsotls/:id} : Partial updates given fields of an existing thamsotl, field will ignore if it is null
     *
     * @param id the id of the thamsotl to save.
     * @param thamsotl the thamsotl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thamsotl,
     * or with status {@code 400 (Bad Request)} if the thamsotl is not valid,
     * or with status {@code 404 (Not Found)} if the thamsotl is not found,
     * or with status {@code 500 (Internal Server Error)} if the thamsotl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Thamsotl> partialUpdateThamsotl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Thamsotl thamsotl
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thamsotl partially : {}, {}", id, thamsotl);
        if (thamsotl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thamsotl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thamsotlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thamsotl> result = thamsotlRepository
            .findById(thamsotl.getId())
            .map(existingThamsotl -> {
                if (thamsotl.getThangnam() != null) {
                    existingThamsotl.setThangnam(thamsotl.getThangnam());
                }
                if (thamsotl.getNcchuan() != null) {
                    existingThamsotl.setNcchuan(thamsotl.getNcchuan());
                }
                if (thamsotl.getGiocchuan() != null) {
                    existingThamsotl.setGiocchuan(thamsotl.getGiocchuan());
                }
                if (thamsotl.getHsgioth() != null) {
                    existingThamsotl.setHsgioth(thamsotl.getHsgioth());
                }
                if (thamsotl.getHsgiole() != null) {
                    existingThamsotl.setHsgiole(thamsotl.getHsgiole());
                }
                if (thamsotl.getPcan() != null) {
                    existingThamsotl.setPcan(thamsotl.getPcan());
                }
                if (thamsotl.getTlbhxh() != null) {
                    existingThamsotl.setTlbhxh(thamsotl.getTlbhxh());
                }
                if (thamsotl.getTlbhyt() != null) {
                    existingThamsotl.setTlbhyt(thamsotl.getTlbhyt());
                }
                if (thamsotl.getTlbhtn() != null) {
                    existingThamsotl.setTlbhtn(thamsotl.getTlbhtn());
                }
                if (thamsotl.getTlkpcd() != null) {
                    existingThamsotl.setTlkpcd(thamsotl.getTlkpcd());
                }

                return existingThamsotl;
            })
            .map(thamsotlRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thamsotl.getId().toString())
        );
    }

    /**
     * {@code GET  /thamsotls} : get all the thamsotls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thamsotls in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Thamsotl>> getAllThamsotls(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Thamsotls");
        Page<Thamsotl> page = thamsotlRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thamsotls/:id} : get the "id" thamsotl.
     *
     * @param id the id of the thamsotl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thamsotl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Thamsotl> getThamsotl(@PathVariable("id") Long id) {
        log.debug("REST request to get Thamsotl : {}", id);
        Optional<Thamsotl> thamsotl = thamsotlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thamsotl);
    }

    /**
     * {@code DELETE  /thamsotls/:id} : delete the "id" thamsotl.
     *
     * @param id the id of the thamsotl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThamsotl(@PathVariable("id") Long id) {
        log.debug("REST request to delete Thamsotl : {}", id);
        thamsotlRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
