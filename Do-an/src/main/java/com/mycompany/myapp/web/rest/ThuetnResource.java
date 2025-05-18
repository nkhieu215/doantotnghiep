package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Thuetn;
import com.mycompany.myapp.repository.ThuetnRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Thuetn}.
 */
@RestController
@RequestMapping("/api/thuetns")
@Transactional
public class ThuetnResource {

    private final Logger log = LoggerFactory.getLogger(ThuetnResource.class);

    private static final String ENTITY_NAME = "thuetn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThuetnRepository thuetnRepository;

    public ThuetnResource(ThuetnRepository thuetnRepository) {
        this.thuetnRepository = thuetnRepository;
    }

    /**
     * {@code POST  /thuetns} : Create a new thuetn.
     *
     * @param thuetn the thuetn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thuetn, or with status {@code 400 (Bad Request)} if the thuetn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Thuetn> createThuetn(@RequestBody Thuetn thuetn) throws URISyntaxException {
        log.debug("REST request to save Thuetn : {}", thuetn);
        if (thuetn.getId() != null) {
            throw new BadRequestAlertException("A new thuetn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thuetn = thuetnRepository.save(thuetn);
        return ResponseEntity.created(new URI("/api/thuetns/" + thuetn.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, thuetn.getId().toString()))
            .body(thuetn);
    }

    /**
     * {@code PUT  /thuetns/:id} : Updates an existing thuetn.
     *
     * @param id the id of the thuetn to save.
     * @param thuetn the thuetn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thuetn,
     * or with status {@code 400 (Bad Request)} if the thuetn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thuetn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Thuetn> updateThuetn(@PathVariable(value = "id", required = false) final Long id, @RequestBody Thuetn thuetn)
        throws URISyntaxException {
        log.debug("REST request to update Thuetn : {}, {}", id, thuetn);
        if (thuetn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thuetn.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thuetnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thuetn = thuetnRepository.save(thuetn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thuetn.getId().toString()))
            .body(thuetn);
    }

    /**
     * {@code PATCH  /thuetns/:id} : Partial updates given fields of an existing thuetn, field will ignore if it is null
     *
     * @param id the id of the thuetn to save.
     * @param thuetn the thuetn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thuetn,
     * or with status {@code 400 (Bad Request)} if the thuetn is not valid,
     * or with status {@code 404 (Not Found)} if the thuetn is not found,
     * or with status {@code 500 (Internal Server Error)} if the thuetn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Thuetn> partialUpdateThuetn(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Thuetn thuetn
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thuetn partially : {}, {}", id, thuetn);
        if (thuetn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thuetn.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thuetnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thuetn> result = thuetnRepository
            .findById(thuetn.getId())
            .map(existingThuetn -> {
                if (thuetn.getBacthue() != null) {
                    existingThuetn.setBacthue(thuetn.getBacthue());
                }
                if (thuetn.getTu() != null) {
                    existingThuetn.setTu(thuetn.getTu());
                }
                if (thuetn.getDen() != null) {
                    existingThuetn.setDen(thuetn.getDen());
                }
                if (thuetn.getThuesuat() != null) {
                    existingThuetn.setThuesuat(thuetn.getThuesuat());
                }

                return existingThuetn;
            })
            .map(thuetnRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thuetn.getId().toString())
        );
    }

    /**
     * {@code GET  /thuetns} : get all the thuetns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thuetns in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Thuetn>> getAllThuetns(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Thuetns");
        Page<Thuetn> page = thuetnRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thuetns/:id} : get the "id" thuetn.
     *
     * @param id the id of the thuetn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thuetn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Thuetn> getThuetn(@PathVariable("id") Long id) {
        log.debug("REST request to get Thuetn : {}", id);
        Optional<Thuetn> thuetn = thuetnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thuetn);
    }

    /**
     * {@code DELETE  /thuetns/:id} : delete the "id" thuetn.
     *
     * @param id the id of the thuetn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuetn(@PathVariable("id") Long id) {
        log.debug("REST request to delete Thuetn : {}", id);
        thuetnRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
